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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker;

import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ScheduleAsFileInformation;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.action.ScheduleAsFileAction;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceRole;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskFolder;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionNotSupportedException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScheduleConfigAsFileHooker implements ScheduleConfigProviderHook {

	private final DtoService dtoService;
	private final Provider<ScheduleAsFileAction> actionProvider;
	private final TeamSpaceService teamSpaceService;
	private final TsDiskService tsService;
	
	@Inject
	public ScheduleConfigAsFileHooker(
		DtoService dtoService,
		Provider<ScheduleAsFileAction> actionProvider,
		TeamSpaceService teamSpaceService,
		TsDiskService tsService
		) {
		
		/* store objects */
		this.dtoService = dtoService;
		this.actionProvider = actionProvider;
		this.teamSpaceService = teamSpaceService;
		this.tsService = tsService;
	}



	@Override
	public void adaptJob(ReportExecuteJob job,
			ReportScheduleDefinition scheduleDTO)
			throws InvalidConfigurationException {
		ScheduleAsFileInformation info = scheduleDTO.getAdditionalInfo(ScheduleAsFileInformation.class);
		if(null == info)
			return;
		if(null == info.getFolder())
			throw new InvalidConfigurationException("No folder specified");
		
		AbstractTsDiskNode folder = (AbstractTsDiskNode) dtoService.loadPoso(info.getFolder());
		if(! (folder instanceof TsDiskRoot) && ! (folder instanceof TsDiskFolder))
			throw new InvalidConfigurationException("No folder specified");
		
		TeamSpace teamSpace = tsService.getTeamSpaceFor(folder);
		if(! teamSpaceService.hasRole(teamSpace, TeamSpaceRole.USER))
			throw new InvalidConfigurationException("Insufficient TeamSpace rights");
		
		ScheduleAsFileAction action = actionProvider.get();
		
		action.setName(info.getName());
		action.setDescription(info.getDescription());
		action.setFolderId(info.getFolder().getId());
		action.setTeamspaceId(teamSpace.getId());
		
		try {
			job.addAction(action);
		} catch (ActionNotSupportedException e) {
			throw new InvalidConfigurationException(e);
		}
	}



	@Override
	public void adaptScheduleDefinition(ReportScheduleDefinition rsd,
			ReportExecuteJob job) {
		ScheduleAsFileAction action = job.getAction(ScheduleAsFileAction.class);
		if(null == action)
			return;
		
		ScheduleAsFileInformation info = new ScheduleAsFileInformation();
		
		info.setName(action.getName());
		info.setDescription(action.getDescription());
		
		AbstractTsDiskNode node = tsService.getNodeById(action.getFolderId());
		TeamSpace teamSpace = tsService.getTeamSpaceFor(node);
		if(! teamSpaceService.hasRole(teamSpace, TeamSpaceRole.USER))
			throw new IllegalArgumentException("Insufficient TeamSpace rights");
		
		if(null != node && ! (node instanceof TsDiskFolder) && !(node instanceof TsDiskRoot))
			throw new IllegalArgumentException("Expected Folder or Root.");
		
		info.setFolder((AbstractTsDiskNodeDto) dtoService.createDto(node));
		info.setTeamSpace((TeamSpaceDto) dtoService.createListDto(teamSpace));
		
		rsd.addAdditionalInfo(info);
	}

}
