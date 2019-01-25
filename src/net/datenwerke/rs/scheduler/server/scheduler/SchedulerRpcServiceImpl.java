/*
 *  ReportServer
 *  Copyright (c) 2018 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.scheduler.server.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.rpc.SchedulerRpcService;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.rs.scheduler.server.scheduler.interfaces.AuthoritativeSchedulerCriteria;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.SchedulerRuntimeException;
import net.datenwerke.rs.scheduler.service.scheduler.genrights.SchedulingAdminSecurityTarget;
import net.datenwerke.rs.scheduler.service.scheduler.genrights.SchedulingBasicSecurityTarget;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.ReportServerJob;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.ReportServerJobFilter;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.scheduler.service.scheduler.locale.SchedulerMessages;
import net.datenwerke.scheduler.client.scheduler.dto.JobExecutionStatusDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DateTriggerConfigDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterConfigurationDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterCriteriaDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ExecutionLogEntryDto;
import net.datenwerke.scheduler.service.scheduler.SchedulerService;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.Outcome;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.JpaJobStore;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterConfiguration;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterCriteria;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.DateTrigger;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.DateTriggerFactory;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.DateTriggerConfig;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

/**
 * 
 *
 */
@Singleton
public class SchedulerRpcServiceImpl extends SecuredRemoteServiceServlet implements SchedulerRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7565893744135855843L;
	
	private final DtoService dtoService;
	private final HookHandlerService hookHandler;
	private final ReportService reportService;
	private final SchedulerService schedulerService;
	private final ReportExecutorService reportExecutorService;
	private final Provider<ReportExecuteJob> reportExecuteJobProvider;
	private final SecurityService securityService;
	private final Provider<AuthenticatorService> authenticatorService;
	private final UserManagerService userService;
	private final Provider<Injector> injectorProvider;

	
	private final DateTriggerFactory dateTriggerFactory;

	
	@Inject
	public SchedulerRpcServiceImpl(
		Provider<Injector> injectorProvider, 
		DtoService dtoService,
		HookHandlerService hookHandler,
		ReportService reportManager,
		SchedulerService schedulerService,
		ReportExecutorService reportExecutorService,
		Provider<ReportExecuteJob> reportExecuteJobProvider,
		SecurityService securityService, 
		Provider<AuthenticatorService> authenticatorService,
		UserManagerService userService,
		DateTriggerFactory dateTriggerFactory
		){
		
		/* store objects */
		this.injectorProvider = injectorProvider;
		this.dtoService = dtoService;
		this.hookHandler = hookHandler;
		this.reportService = reportManager;
		this.schedulerService = schedulerService;
		this.reportExecutorService = reportExecutorService;
		this.reportExecuteJobProvider = reportExecuteJobProvider;
		this.securityService = securityService;
		this.authenticatorService = authenticatorService;
		this.userService = userService;
		this.dateTriggerFactory = dateTriggerFactory;

	}
	
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Execute.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void schedule(ReportScheduleDefinition scheduleDTO) throws ServerCallFailedException{
		doSchedule(scheduleDTO, null);
	}
	
	protected ReportExecuteJob doSchedule(ReportScheduleDefinition scheduleDTO, ReportExecuteJob previous) throws ServerCallFailedException{
		/* load report */
		ReportDto reportDTO = scheduleDTO.getReport();
		Report report = reportService.getReportById(reportDTO.getId());

		/* security */
		if(! securityService.checkRights(report, Execute.class))
			throw new ViolatedSecurityExceptionDto();
		
		/* gather configuration */
		List<ReportExecutionConfig> configList = new ArrayList<ReportExecutionConfig>();
		if(null != scheduleDTO.getExportConfiguration())
			for(ReportExecutionConfigDto config : scheduleDTO.getExportConfiguration())
				configList.add((ReportExecutionConfig) dtoService.createPoso(config));
		
		/* check for empty column configuration */
		try{
			reportExecutorService.isExecutable(report, scheduleDTO.getOutputFormat(), configList.toArray(new ReportExecutionConfig[]{}));
		} catch(ReportExecutorException e){
			SchedulerRuntimeException  sre = new SchedulerRuntimeException();
			sre.initCause(e);
			ExpectedException expe = new ExpectedException(e.getMessage());
			expe.initCause(sre);
			throw expe;
		}

		/* create job */
		ReportExecuteJob job = reportExecuteJobProvider.get();
		job.setReport(report);
		job.setOutputFormat(scheduleDTO.getOutputFormat());
		job.setUser(authenticatorService.get().getCurrentUser());
		job.setExportConfiguration(configList);
		
		/* load recipient list */
		List<User> recipients = new ArrayList<User>();
		for(Long id: scheduleDTO.getRecipients()){
			try{
				AbstractUserManagerNode recipient = userService.getNodeById(id);
				if(recipient instanceof User && (null == ((User)recipient).getEmail() || "".equals(((User)recipient).getEmail())))
					throw new ExpectedException(SchedulerMessages.INSTANCE.errorNoEmail() + " " + ((User)recipient).getFirstname() + " " + ((User)recipient).getLastname());
				recipients.add((User)recipient);
			} catch(NoResultException e){
				throw new ExpectedException("Could not load user for id:" + id);
			}
		}
		job.setRecipients(recipients);
		
		/* call hookers */
		for(ScheduleConfigProviderHook configProvider : hookHandler.getHookers(ScheduleConfigProviderHook.class)){
			try{
				configProvider.adaptJob(job, scheduleDTO);
			}catch(InvalidConfigurationException e){
				throw new ExpectedException(e);
			}
		}
		
		/* schedule report */
		DateTrigger trigger = dateTriggerFactory.createTrigger((DateTriggerConfig)dtoService.createPoso(scheduleDTO.getSchedulerConfig()));
		
		if(null == previous)
			schedulerService.schedule(job, trigger);
		else 
			schedulerService.schedule(job, trigger, previous);
		
		return job;
	}
	
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Execute.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public boolean unschedule(Long jobId)
			throws ServerCallFailedException {
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportServerJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);
		
		ReportServerJob rsJob = (ReportServerJob) job;
		
		User currentUser = authenticatorService.get().getCurrentUser();
		if(rsJob.getUser().equals(currentUser) || securityService.checkRights(SchedulingAdminSecurityTarget.class, Execute.class)){
			schedulerService.unschedule(rsJob);
			return true;
		}
		
		return false;
	}


	@SecurityChecked(
			genericTargetVerification = {
					@GenericTargetVerification(
							target=SchedulingBasicSecurityTarget.class,
							verify=@RightsVerification(rights=Execute.class)
							)
			}
			)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void clearErrorStatus(Long jobId)
			throws ServerCallFailedException {
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportServerJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);

		ReportServerJob rsJob = (ReportServerJob) job;

		User currentUser = authenticatorService.get().getCurrentUser();
		if(! rsJob.getUser().equals(currentUser) && ! securityService.checkRights(SchedulingAdminSecurityTarget.class, Execute.class)){
			throw new ViolatedSecurityException();
		}
		
		schedulerService.clearErrorState(rsJob);
	}

	@SecurityChecked(
			genericTargetVerification = {
					@GenericTargetVerification(
							target=SchedulingAdminSecurityTarget.class,
							verify=@RightsVerification(rights=Execute.class)
							)
			}
			)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public boolean remove(Long jobId) throws ServerCallFailedException {
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportServerJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);

		ReportServerJob rsJob = (ReportServerJob) job;

		schedulerService.remove(rsJob);

		return true;
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public PagingLoadResult<ReportScheduleJobListInformation> getReportJobList(JobFilterConfigurationDto jobFilterConfigDto, List<JobFilterCriteriaDto> addCriterionsDto) throws ServerCallFailedException {
		/* convert filter */
		JobFilterConfiguration filter = (JobFilterConfiguration) dtoService.createPoso(jobFilterConfigDto);
		
		/* set the filters base type */
		filter.setJobType(ReportExecuteJob.class);
		
		/* load criteria */
		List<JobFilterCriteria> addCriteria = new ArrayList<JobFilterCriteria>();
		for(JobFilterCriteriaDto addCritDto : addCriterionsDto){
			JobFilterCriteria crit = (JobFilterCriteria) dtoService.createPoso(addCritDto);
			injectorProvider.get().injectMembers(crit);
			addCriteria.add(crit);
		}
		
		/* is user allowed to run the search */
		if(filter instanceof ReportServerJobFilter){
			boolean allow = false;
			if(! addCriteria.isEmpty() ){
				for(JobFilterCriteria ac : addCriteria){
					if(ac instanceof AuthoritativeSchedulerCriteria && ((AuthoritativeSchedulerCriteria)ac).isAuthoritative()){
						allow = true;
						break;
					}
				}
			}
			if(! allow && ((ReportServerJobFilter)filter).isAnyUser() && ! securityService.checkRights(SchedulingAdminSecurityTarget.class, Read.class)){
				return new PagingLoadResultBean<ReportScheduleJobListInformation>(new ArrayList<ReportScheduleJobListInformation>(),0,0);
			}
		}
		
		/* prepare jobs */
		List<ReportScheduleJobListInformation> jobInfoList = new ArrayList<ReportScheduleJobListInformation>();
		
		JpaJobStore jobStore = (JpaJobStore) schedulerService.getJobStore();
		for(AbstractJob aJob : jobStore.getJobsBy(filter, addCriteria.toArray(new JobFilterCriteria[]{}))){
			ReportExecuteJob job = (ReportExecuteJob) aJob;
			ReportScheduleJobListInformation info = new ReportScheduleJobListInformation();
			jobInfoList.add(info);
			
			info.setJobId(job.getId());
			if(null != job.getUser()){
				info.setOwnerFirstName(job.getUser().getFirstname());
				info.setOwnerLastName(job.getUser().getLastname());
			}
			
			if(null != job.getReport()){
				info.setReportId(job.getReport().getId());
				info.setReportName(job.getReport().getName());
				info.setReportDescription(job.getReport().getDescription());
				info.setNextScheduled(job.getTrigger().getNextScheduledFireTime());
				info.setLastScheduled(job.getLastExecution());
				info.setDeleted(false);
			} else {
				info.setDeleted(true);
				info.setReportId(job.getReportId());
				info.setNextScheduled(job.getTrigger().getNextScheduledFireTime());
				info.setLastScheduled(job.getLastExecution());
			}
		}
		/* get total result */
		long maxResults = jobStore.countJobs(filter);
		
		return new PagingLoadResultBean<ReportScheduleJobListInformation>(jobInfoList, (int)maxResults, jobFilterConfigDto.getOffset());
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public ReportScheduleJobInformation loadFullScheduleInformation(ReportScheduleJobListInformation selected)throws ServerCallFailedException {
		return loadScheduleInformation(selected, 5);
	}
	
	protected ReportScheduleJobInformation loadScheduleInformation(ReportScheduleJobListInformation selected, Integer maxNumberOfHistEntries) throws ServerCallFailedException {
		AbstractJob aJob = schedulerService.getJobById(selected.getJobId());
		if(! (aJob instanceof ReportExecuteJob))
			throw new ServerCallFailedException("Expected ReportExecuteJob");
		
		ReportExecuteJob job = (ReportExecuteJob) aJob;
		
		//TODO: make sure user is allowed to see info

		ReportScheduleJobInformation info = new ReportScheduleJobInformation();
		 
		/* schedule definition */
		ReportScheduleDefinition rsd = new ReportScheduleDefinition();
		rsd.setReport((ReportDto)dtoService.createListDto(job.getReport()));
		rsd.setSchedulerConfig((DateTriggerConfigDto) dtoService.createDto(((DateTrigger)job.getTrigger()).getConfig()));
		rsd.setRecipients(job.getRecipientsIds());
		rsd.setJobId(job.getId());
		rsd.setOutputFormat(job.getOutputFormat());
		
		List<ReportExecutionConfigDto> configList = new ArrayList<ReportExecutionConfigDto>();
		if(null != job.getExportConfiguration())
			for(ReportExecutionConfig config : job.getExportConfiguration())
				configList.add((ReportExecutionConfigDto) dtoService.createDto(config));
		rsd.setExportConfiguration(configList);

		/* call hookers */
		for(ScheduleConfigProviderHook configProvider : hookHandler.getHookers(ScheduleConfigProviderHook.class))
			configProvider.adaptScheduleDefinition(rsd, job);
		
		info.setScheduleDefinition(rsd);
		
		/* execution status */
		info.setExecutionStatus((JobExecutionStatusDto) dtoService.createDto(job.getExecutionStatus()));
		
		/* next entries */
		if(null != job.getTrigger()){
			List<Date> nextEntries = job.getTrigger().getNextScheduleTimes(10);
			info.setNextPlannedEntries(nextEntries);
		}
		
		/* last entries */
		ArrayList<ExecutionLogEntry> entries = new ArrayList<ExecutionLogEntry>(job.getHistory().getExecutionLogEntriesInScheduledStartOrder());
		Collections.reverse(entries);
		
		List<ExecutionLogEntryDto> lastExecutedEntrydtos = new ArrayList<ExecutionLogEntryDto>();
		for(int i = 0, l = null == maxNumberOfHistEntries ? entries.size() : Math.min(maxNumberOfHistEntries, entries.size());
			i < l; i++){
			ExecutionLogEntry entry = entries.get(i);
			ExecutionLogEntryDto dto = (ExecutionLogEntryDto) dtoService.createListDto(entry);
			if(Outcome.FAILURE == entry.getOutcome())
				dto.setBadErrorDescription(null == entry.getBadErrorDescription() ? "" : entry.getBadErrorDescription().substring(0, Math.min(100, entry.getBadErrorDescription().length())));
			lastExecutedEntrydtos.add(dto);
		}
		Collections.reverse(lastExecutedEntrydtos);
		info.setLastExecutedEntries(lastExecutedEntrydtos);
		
		info.setNrOfFailedExecutions(job.getTrigger().getNrOfFailedExecutions());
		info.setNrOfSuccessfulExecutions(job.getTrigger().getNrOfSuccessfulExecutions());
		info.setNrOfVetoedExecutions(job.getTrigger().getNrOfVetoedExecutions());
		info.setOwnerId(job.getUser().getId());
		
		return info;
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Execute.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public ReportDto getReportFor(Long jobId) throws ServerCallFailedException {
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportExecuteJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);
		
		ReportExecuteJob rsJob = (ReportExecuteJob) job;
		Report report = reportService.getReportById(rsJob.getReportId());
		
		if(! securityService.checkRights(report, Read.class, Execute.class))
			throw new ViolatedSecurityException();
		
		return (ReportDto) dtoService.createDto(report);
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Execute.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void reschedule(Long jobId, ReportScheduleDefinition scheduleDTO) throws ServerCallFailedException {
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportExecuteJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);
		
		ReportExecuteJob rsJob = (ReportExecuteJob) job;
		Report report = reportService.getReportById(rsJob.getReportId());
		
		if(! securityService.checkRights(report, Read.class))
			throw new ViolatedSecurityException();
		
		schedulerService.unschedule(rsJob);
		
		ReportExecuteJob newJob = doSchedule(scheduleDTO, rsJob);
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	public ReportScheduleJobInformation loadDetailsFor(
			ReportScheduleJobListInformation selected)
			throws ServerCallFailedException {
		return loadScheduleInformation(selected, null);
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	public ExecutionLogEntryDto loadFullDetailsFor(Long jobId, ExecutionLogEntryDto entryDto)
			throws ServerCallFailedException {
		ExecutionLogEntry entry = (ExecutionLogEntry) dtoService.loadPoso(entryDto);
		
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportExecuteJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);
		
		if(! job.getHistory().getExecutionLogEntries().contains(entry))
			throw new IllegalArgumentException("Entry does not fit job");
		
		// TODO Security
		
		 return (ExecutionLogEntryDto) dtoService.createDtoFullAccess(entry);
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=SchedulingBasicSecurityTarget.class,
				verify=@RightsVerification(rights=Execute.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public void scheduleOnce(Long jobId) throws ServerCallFailedException {
		AbstractJob job = schedulerService.getJobById(jobId);
		if(! (job instanceof ReportExecuteJob))
			throw new IllegalArgumentException("Expected " + ReportServerJob.class);
		
		// TODO Security
		
		job.getTrigger().setExecuteOnce(true);
		schedulerService.merge(job);
	}


}
