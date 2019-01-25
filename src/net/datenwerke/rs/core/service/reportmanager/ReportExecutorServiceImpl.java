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
 
 
package net.datenwerke.rs.core.service.reportmanager;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngine;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngines;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledReportMetadata;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECReportExecutorToken;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECSetExecutionUUID;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.events.ReportExecutedEvent;
import net.datenwerke.rs.core.service.reportmanager.events.ReportExecutionFailedEvent;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportExecutionNotificationHook;
import net.datenwerke.rs.core.service.reportmanager.locale.ReportManagerMessages;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.exception.ExceptionServices;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class ReportExecutorServiceImpl implements ReportExecutorService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final Provider<Set<Class<? extends ReportEngine>>> reportEnginesProvider;
	private final Injector injector;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final HookHandlerService hookHandler;
	private final EventBus eventBus;
	private final ExceptionServices exceptionService;
	
	private Map<UUID, ReportExecutorJob> jobMap = new HashMap<UUID, ReportExecutorJob>();
	
	@Inject
	public ReportExecutorServiceImpl(
		@ReportEngines Provider<Set<Class<? extends ReportEngine>>> reportEnginesProvider,
		Injector injector, 
		Provider<AuthenticatorService> authenticatorServiceProvider,
		HookHandlerService hookHandler,
		ExceptionServices exceptionService,
		EventBus eventBus
		){
		
		/* store objects */
		this.injector = injector;
		this.reportEnginesProvider = reportEnginesProvider;
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.hookHandler = hookHandler;
		this.exceptionService = exceptionService;
		this.eventBus = eventBus;
	}
	
	@Override
	public CompiledReport execute(Report report, String outputFormat) throws ReportExecutorException {
		return execute(report, outputFormat, ReportExecutionConfig.EMPTY_CONFIG);
	}
	
	@Override
	public CompiledReport execute(Report report, ParameterSet parameterSet, String outputFormat) throws ReportExecutorException {
		return execute(report, parameterSet, outputFormat, ReportExecutionConfig.EMPTY_CONFIG);
	}
	
	@Override
	public CompiledReport execute(Report report, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		AuthenticatorService authenticatorService = authenticatorServiceProvider.get();
		User user = authenticatorService.getCurrentUser();
		return execute(report, null, user, outputFormat, configs);
	}
	
	@Override
	public CompiledReport execute(Report report, ParameterSet parameterSet, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		AuthenticatorService authenticatorService = authenticatorServiceProvider.get();
		User user = authenticatorService.getCurrentUser();
		return execute(report, parameterSet, user, outputFormat, configs);
	}
	
	@Override
	public CompiledReport execute(Report report, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		return execute(report, null, user, outputFormat, configs);
	}
	
	@Override
	public CompiledReport execute(Report report, ParameterSet parameterSet, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		return execute(null, report, parameterSet, user, outputFormat, configs);
	}
	
	/**
	 * Loops over the list of registered report engines and executes the report with the corresponding engine.
	 * 
	 * @param report
	 * @param parameters
	 * @return
	 * @throws ReportServerException
	 */
	@Override
	public CompiledReport execute(OutputStream os, Report report, ParameterSet parameterSet, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		return execute(false, os, report, parameterSet, user, outputFormat, configs);
	}
	
	@Override
	public CompiledReport executeDry(Report report, ParameterSet parameterSet, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		return execute(true, null, report, parameterSet, user, outputFormat, configs);
	}
	
	@Override
	public boolean supportsStreaming(Report report, ParameterSet parameterSet, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		for(Class<? extends ReportEngine> componentClass : reportEnginesProvider.get()){
			ReportEngine e = injector.getInstance(componentClass);

			if(e.consumes(report))
				return e.supportsStreaming(report, parameterSet, user, outputFormat, configs);
		}
		return false;
	}
	
	
	protected CompiledReport execute(boolean dry, OutputStream os, Report report, ParameterSet parameterSet, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		/* Create uuid to trace report execution in log */
		String uuid = UUID.randomUUID().toString();
		
		String token = null;
		for(ReportExecutionConfig recc : configs){
			if(recc instanceof RECReportExecutorToken)
				token = ((RECReportExecutorToken) recc).getToken();
		}
//		System.out.println("token: " + token);
		
		if(! dry){
			eventBus.fireEvent(new ReportExecutedEvent(
				"report_id", null == report.getId() ? report.getOldTransientId() : report.getId(),
				"executing_user", user.getId(),
				"output_format", outputFormat,
				"uuid", uuid,
				"token", token
			));
		}
		
		/* sanitize configs */
		if(null == configs)
			configs = new ReportExecutionConfig[]{};
		
		List<ReportExecutionNotificationHook> notificees = hookHandler.getHookers(ReportExecutionNotificationHook.class);
		if(! dry){
			for(ReportExecutionNotificationHook notificee : notificees)
				notificee.notifyOfReportExecution(report, parameterSet, user, outputFormat, configs);
		}
		
		try{
			if(! dry){
				for(ReportExecutionNotificationHook notificee : notificees)
					notificee.doVetoReportExecution(report, parameterSet, user, outputFormat, configs);
			}
			
			for(Class<? extends ReportEngine> componentClass : reportEnginesProvider.get()){
				ReportEngine e = injector.getInstance(componentClass);

				if(e.consumes(report)) {
					CompiledReport compiledReport;
					
					if(! dry){
						ArrayList<ReportExecutionConfig> cfgList = new ArrayList<ReportExecutionConfig>();
						cfgList.addAll(Arrays.asList(configs));
						cfgList.add(new RECSetExecutionUUID(uuid));
						compiledReport = e.execute(os, report, parameterSet, user, outputFormat, cfgList.toArray(new ReportExecutionConfig[0]));
					}else
						compiledReport = e.executeDry(os, report, parameterSet, user, outputFormat, configs);
					
					if(! dry){
						for(ReportExecutionNotificationHook notificee : notificees)
							notificee.notifyOfReportsSuccessfulExecution(compiledReport, report, parameterSet, user, outputFormat, configs);
					}
					
					return compiledReport;
				}
			}
		} catch(ReportExecutorException e){
			if(! dry){
				/* fire event */
				eventBus.fireEvent(new ReportExecutionFailedEvent(
					"report_id", null == report.getId() ? report.getOldTransientId() : report.getId(),
					"executing_user", user.getId(),
					"output_format", outputFormat,
					"error_msg", e.getMessage(),
					"error_st", exceptionService.exceptionToString(e),
					"uuid", uuid
				));
				
				/* notify hookers */
				for(ReportExecutionNotificationHook notificee : notificees)
					notificee.notifyOfReportsUnsuccessfulExecution(e, report, parameterSet, user, outputFormat, configs);
			}
			
			throw e;
		} catch(Exception e){
			logger.warn( e.getMessage(), e);
			ReportExecutorException rsre = new ReportExecutorException(ReportManagerMessages.INSTANCE.exceptionReportCouldNotBeExecuted(e.getLocalizedMessage()), e);

			if(! dry){
				/* fire event */
				eventBus.fireEvent(new ReportExecutionFailedEvent(
					"report_id", null == report.getId() ? report.getOldTransientId() : report.getId(),
					"executing_user", user.getId(),
					"output_format", outputFormat,
					"error_msg", e.getMessage(),
					"error_st", exceptionService.exceptionToString(rsre),
					"uuid", uuid
				));
				
				/* notify hookers */
				for(ReportExecutionNotificationHook notificee : notificees)
					notificee.notifyOfReportsUnsuccessfulExecution(rsre, report, parameterSet, user, outputFormat, configs);
			}
			
			throw rsre;
		}
			
		throw new IllegalArgumentException("Reporttype " + report.getClass().getName() + " seems not to be supported."); //$NON-NLS-1$ //$NON-NLS-2$
	}
	

	
	@Override
	public CompiledReportMetadata exportMetadata(Report report, String outputFormat) throws ReportExecutorException {
		AuthenticatorService authenticatorService = authenticatorServiceProvider.get();
		User user = authenticatorService.getCurrentUser();
		return exportMetadata(report, user, null, outputFormat);
	}
	
	@Override
	public CompiledReportMetadata exportMetadata(Report report, User user, ParameterSet parameterSet, String outputFormat) throws ReportExecutorException {
		try{
			for(Class<? extends ReportEngine> componentClass : reportEnginesProvider.get()){
				ReportEngine e = injector.getInstance(componentClass);

				if(e.consumes(report))
					return e.exportMetadata(report, parameterSet, user, outputFormat);
			}
		} catch(Exception e){
			throw new ReportExecutorException(ReportManagerMessages.INSTANCE.exceptionReportCouldNotBeExecuted(e.getLocalizedMessage()), e);
		}
			
		throw new IllegalArgumentException("Reporttype " + report.getClass().getName() + " seems not to be supported."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void isExecutable(Report report, String outputFormat,
			ReportExecutionConfig... configs) throws ReportExecutorException {
		isExecutable(report, authenticatorServiceProvider.get().getCurrentUser(), outputFormat, configs);
	}
	
	@Override
	public void isExecutable(Report report, User user, String outputFormat,
			ReportExecutionConfig... configs) throws ReportExecutorException {
		isExecutable(report, null, user, outputFormat, configs);
	}

	@Override
	public void isExecutable(Report report, ParameterSet additionalParameters,
			User user, String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException {

		for(Class<? extends ReportEngine> componentClass : reportEnginesProvider.get()){
			ReportEngine e = injector.getInstance(componentClass);

			if(e.consumes(report))
				e.isExecutable(report, additionalParameters, user, outputFormat, configs);
		}
	}

	

}
