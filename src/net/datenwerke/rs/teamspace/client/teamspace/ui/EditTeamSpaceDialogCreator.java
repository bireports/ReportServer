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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.ui;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.dialog.properties.RpcPropertiesDialog;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitCompleteCallback;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceDao;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.hooks.TeamSpaceEditDialogHook;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
public class EditTeamSpaceDialogCreator {
	
	private final HookHandlerService hookHandler;
	private final Provider<RpcPropertiesDialog> propertiesDialogProvider;
	private final TeamSpaceDao tsDao;
	
	@Inject
	public EditTeamSpaceDialogCreator(
			HookHandlerService hookHandler,
			Provider<RpcPropertiesDialog> propertiesDialogProvider,
			TeamSpaceDao tsDao
		){
		
		/* store objects */
		this.hookHandler = hookHandler;
		this.propertiesDialogProvider = propertiesDialogProvider;
		this.tsDao = tsDao;
	}

	public void displayDialog(final TeamSpaceMainComponent teamSpaceMainComponent, final TeamSpaceDto currentSpace) {
		tsDao.reloadTeamSpaceForEdit(currentSpace, new RsAsyncCallback<TeamSpaceDto>(){
			@Override
			public void onSuccess(TeamSpaceDto result) {
				final RpcPropertiesDialog dialog = propertiesDialogProvider.get();
				
				dialog.setPerformSubmitsConsecutively(true);
				dialog.continueOnFailure(true);
				dialog.setSize(640, 480);
				dialog.setHeadingText(TeamSpaceMessages.INSTANCE.editTeamSpaceHeading(currentSpace.getName() + " (" + currentSpace.getId() + ") "));
				dialog.setHeaderIcon(BaseIcon.GROUP_PROPERTIES);
				dialog.setMaskOnSubmit(TeamSpaceMessages.INSTANCE.editTeamSpaceWindowServerRequestMask());
				dialog.setModal(true);
				dialog.setSubmitCompleteCallback(new SubmitCompleteCallback() {
					
					@Override
					public void onSuccess() {
						dialog.mask(BaseMessages.INSTANCE.storingMsg());
						tsDao.reloadTeamSpace(currentSpace, new RsAsyncCallback<TeamSpaceDto>(){
							@Override
							public void onSuccess(TeamSpaceDto result) {
								dialog.hide();
								teamSpaceMainComponent.loadSpace(result);
							}
						});
					}
					@Override
					public void onFailure(Throwable t) {
					}			
				});
				
				/* load cards */
				final List<TeamSpaceEditDialogHook> cardProviders = hookHandler.getHookers(TeamSpaceEditDialogHook.class);
				for(TeamSpaceEditDialogHook cardProvider : cardProviders){
					if(! cardProvider.applies(result))
						continue;
					
					cardProvider.setCurrentSpace(result);
					
					/* get height */
					dialog.addCard(cardProvider);
				}
				
				dialog.show();
			}
		});

	}
}
