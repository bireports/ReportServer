/*
 *  ReportServer
 *  Copyright (c) 2016 datenwerke Jan Albrecht
 *  http://reportserver.datenwerke.net
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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.jobs.report;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.ReportServerJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.exceptions.JobExecutionException;
import net.datenwerke.scheduler.service.scheduler.jobs.BaseJob__;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
@Entity
@Table(name="SCHED_EXECUTE_REPORT_JOB")
@Inheritance(strategy=InheritanceType.JOINED)
@AssociationOverride(name=BaseJob__.baseProperties, 
                     joinTable=@JoinTable(name="SCHED_REP_EXEC_JOB_2_PROP"))
public class ReportExecuteJob extends ReportServerJob {

	private final static String EXPORT_CONFIG_KEY = "exportConfigurations";
	
	@Inject @Transient private ReportExecutorService reportExecutor;
	
	@Inject @Transient private Provider<AuthenticatorService> authenticatorServiceProvider;
	
	@ManyToOne
	private Report report;
	
	private String outputFormat;
	
	@JoinTable(name="SCHED_REP_EXEC_JOB_2_RCPT", joinColumns=@JoinColumn(name="report_execute_job_id"))
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Long> rcptIDs = new HashSet<Long>();
	
	@Transient
	private CompiledReport executedReport;
	
	
	public void setRecipients(List<User> recipients){
		for(AbstractUserManagerNode recipient : recipients){
			if(recipient instanceof User)
				rcptIDs.add(recipient.getId());
		}
	}
	
	public List<Long> getRecipientsIds(){
		return new ArrayList<Long>(rcptIDs);
	}
	
	public List<User> getRecipients(){
		/* combine them */
		Set<Long> ids = new HashSet<Long>(rcptIDs);
		return new ArrayList<User>(userService.getUsers(ids, true));
	}

	/**
	 * Set the report instance to use.
	 * @param report
	 */
	public void setReport(Report report){
		this.report = report;
	}

	/**
	 * Sets the ouput format to use.
	 * @param outputFormat
	 */
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	
	public void setExportConfiguration(List<ReportExecutionConfig> exportConfiguration) {
		setComplexProperty(EXPORT_CONFIG_KEY, exportConfiguration);
	}
	
	public List<ReportExecutionConfig> getExportConfiguration() {
		List<ReportExecutionConfig> list = getComplexProperty(EXPORT_CONFIG_KEY);
		if(null == list)
			return new ArrayList<ReportExecutionConfig>();
		return list;
	}
	
	@Override
	protected void doExecute() throws JobExecutionException {
		Report report = getReport();
		if(null == report)
			throw new JobExecutionException("Could not find report with id: " + getReportId());
		
		/* execute report */
		try {
			try{
				authenticatiorServiceProvider.get().setAuthenticatedInThread(getUser().getId());
				
				executedReport = reportExecutor.execute(report, getUser(), outputFormat, getExportConfiguration().toArray(new ReportExecutionConfig[]{}));
			}finally{
				authenticatiorServiceProvider.get().logoffUserInThread();
			}
		} catch (ReportExecutorException e) {
			throw new JobExecutionException(e);
		}
	}
	
	public Report getReport(){
		return report;
	}
	
	public CompiledReport getExecutedReport(){
		return executedReport;
	}


	@Override
	public void adjustJobEntryForFailure(JobEntry jobEntry) {
		jobEntry.addHistoryProperty("reportId", ""+(null != report ? report.getId() : "null"));
	}

	public String getOutputFormat() {
		return outputFormat;
	}
	
	public Long getReportId() {
		return null != report ? report.getId() : null;
	}


}
