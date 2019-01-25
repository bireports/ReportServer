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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.hookers;

import java.util.List;

import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinitionSendToConfig;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.scheduler.service.scheduler.sendto.SendToReportAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionNotSupportedException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScheduleSendToActionHooker implements ScheduleConfigProviderHook {

	private final Provider<SendToReportAction> sendToActionProvider;
	
	@Inject
	public ScheduleSendToActionHooker(
		Provider<SendToReportAction> sendToActionProvider
		) {
		
		/* store objects */
		this.sendToActionProvider = sendToActionProvider;
	}

	
	@Override
	public void adaptJob(ReportExecuteJob job,
			ReportScheduleDefinition scheduleDTO)
			throws InvalidConfigurationException {
		for(ReportScheduleDefinitionSendToConfig config: scheduleDTO.getSendToConfigs()){
			/* create action */
			SendToReportAction sendToAction = sendToActionProvider.get();
			sendToAction.setSendToId(config.getId());
			sendToAction.setConfigValues(config.getValues());
			try {
				job.addAction(sendToAction);
			} catch (ActionNotSupportedException e) {
				throw new InvalidConfigurationException(e);
			}
		}
	}

	@Override
	public void adaptScheduleDefinition(ReportScheduleDefinition rsd, ReportExecuteJob job) {
		List<AbstractAction> actions = job.getActions();
		if(null == actions)
			return;
		
		for(AbstractAction action : actions){
			if(! (action instanceof SendToReportAction))
				continue;
			
			SendToReportAction sendToAction = (SendToReportAction) action;
			rsd.addSendToConfigs(new ReportScheduleDefinitionSendToConfig(sendToAction.getSendToId(), sendToAction.getValueMap()));
		}
	}

}
