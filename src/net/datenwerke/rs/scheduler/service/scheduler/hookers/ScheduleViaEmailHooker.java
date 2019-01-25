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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.hookers;

import net.datenwerke.rs.scheduler.client.scheduler.dto.EmailInformation;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.scheduler.service.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.service.scheduler.mail.MailReportAction;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionNotSupportedException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScheduleViaEmailHooker implements ScheduleConfigProviderHook {

	private final Provider<MailReportAction> mailReportActionProvider;
	
	@Inject
	public ScheduleViaEmailHooker(
		Provider<MailReportAction> mailReportActionProvider
		) {
		
		/* store objects */
		this.mailReportActionProvider = mailReportActionProvider;
	}

	@Override
	public void adaptJob(ReportExecuteJob job,
			ReportScheduleDefinition scheduleDTO)
			throws InvalidConfigurationException {
		EmailInformation emailInfo = scheduleDTO.getAdditionalInfo(EmailInformation.class);
		if(null == emailInfo)
			return;
		
		if(scheduleDTO.getRecipients().isEmpty())
			throw new InvalidConfigurationException(SchedulerMessages.INSTANCE.errorNoRecipients());
		if(null == emailInfo.getSubject() || "".equals(emailInfo.getSubject()))
			throw new InvalidConfigurationException(SchedulerMessages.INSTANCE.errorNoSubject());
		
		/* create mail action */
		MailReportAction mailAction = mailReportActionProvider.get();
		mailAction.setSubject(emailInfo.getSubject());
		mailAction.setMessage(emailInfo.getMessage());
		mailAction.setCompressed(emailInfo.isCompressed());
		try {
			job.addAction(mailAction);
		} catch (ActionNotSupportedException e) {
			throw new InvalidConfigurationException(e);
		}
	}



	@Override
	public void adaptScheduleDefinition(ReportScheduleDefinition rsd,
			ReportExecuteJob job) {
		MailReportAction action = job.getAction(MailReportAction.class);
		if(null == action)
			return;
		
		EmailInformation info = new EmailInformation();
		
		info.setSubject(action.getSubject());
		info.setMessage(action.getMessage());
		info.setCompressed(action.isCompressed());
		
		rsd.addAdditionalInfo(info);
	}

}
