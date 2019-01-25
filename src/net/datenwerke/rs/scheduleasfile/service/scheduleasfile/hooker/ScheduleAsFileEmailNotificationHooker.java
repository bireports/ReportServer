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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import net.datenwerke.gf.service.localization.RemoteMessageService;
import net.datenwerke.rs.base.service.parameterreplacements.provider.ReportForJuel;
import net.datenwerke.rs.base.service.parameterreplacements.provider.UserForJuel;
import net.datenwerke.rs.core.service.mail.MailService;
import net.datenwerke.rs.core.service.mail.MailTemplate;
import net.datenwerke.rs.core.service.mail.SimpleMail;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.action.ScheduleAsFileAction;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.helper.DiskNodeForJuel;
import net.datenwerke.rs.scheduler.service.scheduler.annotations.SchedulerModuleProperties;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.teamspace.service.teamspace.helper.TeamSpaceForJuel;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.helper.VetoJobExecution;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerExecutionHook;
import net.datenwerke.security.service.usermanager.entities.User;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleAsFileEmailNotificationHooker implements SchedulerExecutionHook {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private static final String PROPERTY_TEAMSPACE = "teamspace";
	private static final String PROPERTY_FOLDER = "folder";
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_DESCRIPTION = "description";
	private static final String PROPERTY_REPORT = "report";
	private static final String PROPERTY_FILENAME = "filename";
	
	public static final String PROPERTY_EXCEPTION = "exception";
	public static final String PROPERTY_EXCEPTIONST= "trace";
	
	public static final String PROPERTY_TSNOTIFICATION_SUBJECT_SUCCESS = "scheduler.fileaction.subject";
	public static final String PROPERTY_TSNOTIFICATION_TEXT_SUCCESS = "scheduler.fileaction.text";

	private static final String PROPERTY_NOTIFICATION_DISABLED = "scheduler.fileaction[@disabled]";
	private static final String PROPERTY_NOTIFICATION_HTML = "scheduler.fileaction[@html]";


	private Configuration config;
	private MailService mailService;
	private TsDiskService tsDiskService;

	private RemoteMessageService remoteMessageService;




	@Inject
	public ScheduleAsFileEmailNotificationHooker(
			@SchedulerModuleProperties Configuration config,
			MailService mailService,
			TsDiskService tsDiskService, 
			RemoteMessageService remoteMessageService) {
		
		this.config = config;
		this.mailService = mailService;
		this.tsDiskService = tsDiskService;
		this.remoteMessageService = remoteMessageService;
	}


	@Override
	public void actionExecutionEndedSuccessfully(AbstractJob abstractJob,
			AbstractAction abstractAction, ExecutionLogEntry logEntry) {
		if(! (abstractJob instanceof ReportExecuteJob))
			return;
		
		if(! (abstractAction instanceof ScheduleAsFileAction))
			return;

		ReportExecuteJob job = (ReportExecuteJob) abstractJob;
		
		String subjectTemplate = config.getString(PROPERTY_TSNOTIFICATION_SUBJECT_SUCCESS);
		String messageTemplate = config.getString(PROPERTY_TSNOTIFICATION_TEXT_SUCCESS);
		
		try {
			sendmail((ScheduleAsFileAction) abstractAction, job, subjectTemplate, messageTemplate, null);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendmail(ScheduleAsFileAction action, ReportExecuteJob job, String subjectTemplate, String tpl, Exception e) throws MessagingException{
		if(config.getBoolean(PROPERTY_NOTIFICATION_DISABLED, false))
			return;
		
		List<User> recipients = ((ReportExecuteJob)job).getRecipients();
		if(null == recipients || recipients.isEmpty())
			return;
		
		Map<String, Object> datamap = new HashMap<String, Object>();
		
		datamap.put(PROPERTY_REPORT, new ReportForJuel(action.getReport()));
		datamap.put("user", UserForJuel.createInstance(job.getUser()));

		datamap.put(PROPERTY_TEAMSPACE, new TeamSpaceForJuel(action.getTeamspace()));
		datamap.put(PROPERTY_FOLDER, new DiskNodeForJuel(action.getFolder()));
		datamap.put(PROPERTY_NAME, action.getExecutedReportFileReference().getName());
		datamap.put(PROPERTY_FILENAME, action.getExecutedReportFileReference().getName() + "." +
				action.getExecutedReportFileReference().getCompiledReport().getCompiledReport().getFileExtension());
		datamap.put(PROPERTY_DESCRIPTION, action.getDescription());
		
		if(null != e){
			datamap.put(PROPERTY_EXCEPTION, e);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(bos);
			e.printStackTrace(pw);
			pw.close();
			datamap.put(PROPERTY_EXCEPTIONST, bos.toString());
		}
		
		String currentLanguage = LocalizationServiceImpl.getLocale().getLanguage();
		datamap.put("msgs", remoteMessageService.getMessages(currentLanguage));
		
		
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setHtml(config.getBoolean(PROPERTY_NOTIFICATION_HTML, false));
		mailTemplate.setSubjectTemplate(subjectTemplate);
		mailTemplate.setMessageTemplate(tpl);
		mailTemplate.setDataMap(datamap);
	
		SimpleMail simpleMail = mailService.newTemplateMail(mailTemplate);
		simpleMail.setRecipients(javax.mail.Message.RecipientType.BCC, getRecipientEmailList(recipients));
	
		mailService.sendMail(simpleMail);
	}
	

	private Address[] getRecipientEmailList(List<User> users) {
		ArrayList<Address> recipients = new ArrayList<Address>();
		
		for(User user : users){
			if(null != user.getEmail() && !"".equals(user.getEmail()) && !recipients.contains(user.getEmail()))
				if(! recipients.contains(user.getEmail()))
					try {
						recipients.add(new InternetAddress(user.getEmail()));
					} catch (AddressException e) {
					}
		}
		
		return recipients.toArray(new Address[]{});
	}

	private AbstractTsDiskNode getFolder(ScheduleAsFileAction action) {
		return (AbstractTsDiskNode) tsDiskService.getNodeById(action.getFolderId());
	}
	

	@Override
	public void jobExecutionAboutToStart(AbstractJob job,
			ExecutionLogEntry logEntry) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionExecutionAboutToStart(AbstractJob job,
			ExecutionLogEntry logEntry) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void executionEndedSuccessfully(AbstractJob job,
			ExecutionLogEntry logEntry) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void executionEndedAbnormally(AbstractJob job,
			ExecutionLogEntry logEntry, Exception e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public VetoJobExecution doesVetoExecution(AbstractJob job,
			ExecutionLogEntry logEntry) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void informAboutVeto(AbstractJob job, ExecutionLogEntry logEntry,
			VetoJobExecution veto) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionExecutionEndedAbnormally(AbstractJob job,
			AbstractAction action, ActionEntry actionEntry, Exception e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void jobExecutionEndedAbnormally(AbstractJob job, JobEntry jobEntry,
			Exception e) {
		// TODO Auto-generated method stub
		
	}



}
