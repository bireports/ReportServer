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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.mail.internet.InternetAddress;
import javax.persistence.Transient;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.datenwerke.rs.base.service.parameterreplacements.provider.ReportForJuel;
import net.datenwerke.rs.base.service.parameterreplacements.provider.UserForJuel;
import net.datenwerke.rs.core.service.mail.MailService;
import net.datenwerke.rs.core.service.mail.SimpleMail;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.scheduler.service.scheduler.annotations.SchedulerModuleProperties;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.security.service.usermanager.entities.User;

public class SchedulerMailHelper {
	
	private static final String PROPERTY_JOB_ID = "jobId";
	private static final String PROPERTY_HASDATA = "hasData";
	
	private static final String PROPERTY_NOTIFICATION_HTML = "scheduler.mailaction[@html]";
	
	@Transient
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private MailService mailService;
	private Provider<SimpleJuel> simpleJuelProvider;

	private ReportExecutorService reportExecutorService;
	private Provider<Configuration> config;
	

	@Inject
	public SchedulerMailHelper(
			MailService mailService, 
			Provider<SimpleJuel> simpleJuelProvider, 
			ReportExecutorService reportExecutorService,
			@SchedulerModuleProperties Provider<Configuration> config
		) {
		this.mailService = mailService;
		this.simpleJuelProvider = simpleJuelProvider;
		this.reportExecutorService = reportExecutorService;
		this.config = config;
	}

	public SimpleMail prepareSimpleMail(ReportExecuteJob job){
		/* create new object */
		SimpleMail mail = mailService.newSimpleMail();
					
		/* set from */
		InternetAddress mailFrom = getMailFrom(job);
		mail.setFrom(mailFrom, true);
		
		/* set recipients */
		mail.setToRecipients(getRecipientEmailList(job));
		
		
		return mail;
	}
	
	public boolean isHTML(){
		try{
			return config.get().getBoolean(PROPERTY_NOTIFICATION_HTML, false);
		}catch(Exception e){
			return false;
		}
	}
	
	public  InternetAddress getMailFrom(ReportExecuteJob job) {
		User sender = job.getUser();
		String mailFrom = ((User)sender).getEmail();
		if(null == mailFrom || "".equals(mailFrom)){
			IllegalArgumentException ex = new IllegalArgumentException("Sender does not have email address: " + sender.getId());
			logger.warn( ex.getMessage(), ex);
			throw ex;
		}
		try {
			return new InternetAddress(mailFrom, sender.getFirstname() + " " + sender.getLastname());
		} catch (UnsupportedEncodingException e) {
			logger.warn( e.getMessage(), e);
			throw new RuntimeException("Failed to set mail sender address ",e);
		}
	}


	
	public  List<String> getRecipientEmailList(ReportExecuteJob job) {
		List<String> recipients = new ArrayList<String>();
		
		for(User user : job.getRecipients()){
			if(null != user.getEmail() && !"".equals(user.getEmail()) && !recipients.contains(user.getEmail()))
				if(! recipients.contains(user.getEmail()))
					recipients.add(user.getEmail());
		}
		
		return recipients;
	}
	
	
	public SimpleJuel getConfiguredJuel(ReportExecuteJob job ) {
		SimpleJuel juel = simpleJuelProvider.get();
		
		juel.addReplacement(PROPERTY_JOB_ID, job.getId().toString());
		juel.addReplacement(PROPERTY_HASDATA, job.getExecutedReport().hasData());
		
		/* addReport */
		juel.addReplacement("report", new ReportForJuel(job.getReport()));
		juel.addReplacement("user", UserForJuel.createInstance(job.getUser()));
		
		/* metadata description */
		try {
			String metadata = (String) reportExecutorService.exportMetadata(job.getReport(), job.getUser(), null, ReportExecutorService.METADATA_FORMAT_PLAIN).getMetadata();
			juel.addReplacement("metadata", metadata);
		} catch (ReportExecutorException e) {
			logger.warn( e.getMessage(), e);
		}
		
		return juel;
	}
	
}
