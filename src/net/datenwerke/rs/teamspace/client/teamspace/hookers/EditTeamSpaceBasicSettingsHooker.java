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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.hookers;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCReadOnly;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTextArea;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerToken;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceDao;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.decorator.TeamSpaceDtoDec;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.TeamSpaceDtoPA;
import net.datenwerke.rs.teamspace.client.teamspace.hooks.TeamSpaceEditDialogHookImpl;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * 
 *
 */
public class EditTeamSpaceBasicSettingsHooker extends TeamSpaceEditDialogHookImpl {
	
	private TeamSpaceUIService teamSpaceUIService;
	private final TeamSpaceDao tsDao;
	
	private TeamSpaceDto teamSpaceData;
	private SimpleForm form;

	
	@Inject
	public EditTeamSpaceBasicSettingsHooker(
			TeamSpaceUIService teamSpaceUIService,
			TeamSpaceDao tsDao	
		){
		
		this.teamSpaceUIService = teamSpaceUIService;
		/* store objects */
		this.tsDao = tsDao;
	}
	
	@Override
	public ImageResource getIcon() {
		return BaseIcon.EDIT.toImageResource();
	}

	@Override
	public String getName() {
		return TeamSpaceMessages.INSTANCE.editTeamSpaceBasicSettingsName();
	}

	@Override
	public Widget getCard() {
		Widget editForm = createEditComponent(teamSpace);
		
		return editForm;
	}

	private Widget createEditComponent(TeamSpaceDto teamSpace) {
		SFFCReadOnly readOnly = teamSpaceUIService.isAdmin(teamSpace)?SFFCReadOnly.FALSE:SFFCReadOnly.TRUE;
		
		form = SimpleForm.getInlineInstance();
		form.setHeight(300);
		
		form.addField(String.class, 
				TeamSpaceDtoPA.INSTANCE.name(), 
				TeamSpaceMessages.INSTANCE.newTeamSpaceNameLabel(),
				new SFFCAllowBlank() {
					@Override
					public boolean allowBlank() {
						return false;
					}
				}, readOnly);
		
		form.addField(String.class, 
				TeamSpaceDtoPA.INSTANCE.description(), 
				TeamSpaceMessages.INSTANCE.newTeamSpaceDescriptionLabel(),
				new SFFCTextArea(){
					@Override
					public int getWidth() {
						return 250;
					}

					@Override
					public int getHeight() {
						return 100;
					}
		}, readOnly);
		
		/* copy values to data */
		teamSpaceData = new TeamSpaceDtoDec();
		teamSpaceData.setName(
			null != teamSpace.getName() ?
					teamSpace.getName() : "");
		teamSpaceData.setDescription(
			null != teamSpace.getDescription() ?
				teamSpace.getDescription() : ""
		);
		
		/* create dummy teamspace and bind it to form */
		form.bind(teamSpaceData);
		
		return form;
	}

	@Override
	public int getHeight() {
		return 400;
	}


	@Override
	public void submitPressed(final SubmitTrackerToken token) {
		boolean dataChanged = teamSpaceUIService.isAdmin(teamSpace) && 
			(! (teamSpaceData.getName().equals(teamSpace.getName()) &&
			teamSpaceData.getDescription().equals(teamSpace.getDescription()) ));
			
		if(! dataChanged)
			token.setCompleted();
		else {
			teamSpace.setName(teamSpaceData.getName());
			teamSpace.setDescription(teamSpaceData.getDescription());
			tsDao.editTeamSpaceSettings(teamSpace, 
					new NotamCallback<TeamSpaceDto>(TeamSpaceMessages.INSTANCE.editTeamSpaceBasicSettingsSubmitted()){
				@Override
				public void doOnSuccess(TeamSpaceDto result) {
					token.setCompleted();
				}
			});
		} 
			
	}


	@Override
	public String isValid() {
		if(! form.isValid())
			return TeamSpaceMessages.INSTANCE.editTeamSpaceBasicSettingsErrorMsg();
		return null;
	}

}
