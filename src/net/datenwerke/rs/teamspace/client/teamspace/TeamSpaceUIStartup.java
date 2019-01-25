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
 
 
package net.datenwerke.rs.teamspace.client.teamspace;

import net.datenwerke.gf.client.homepage.hooks.ClientMainModuleProviderHook;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.eventbus.events.SubmoduleDisplayRequest;
import net.datenwerke.gxtdto.client.eventbus.handlers.SubmoduleDisplayRequestHandler;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.decorator.TeamSpaceDtoDec;
import net.datenwerke.rs.teamspace.client.teamspace.helpers.simpleform.TeamSpaceProvider;
import net.datenwerke.rs.teamspace.client.teamspace.hookers.EditTeamSpaceBasicSettingsHooker;
import net.datenwerke.rs.teamspace.client.teamspace.hookers.EditTeamSpaceMembersHooker;
import net.datenwerke.rs.teamspace.client.teamspace.hookers.UserProfileTeamSpacePropertiesHooker;
import net.datenwerke.rs.teamspace.client.teamspace.hooks.TeamSpaceEditDialogHook;
import net.datenwerke.rs.teamspace.client.teamspace.security.TeamSpaceGenericTargetIdentifier;
import net.datenwerke.rs.teamspace.client.teamspace.security.TeamSpaceSecurityTargetDomainHooker;
import net.datenwerke.rs.teamspace.client.teamspace.ui.TeamSpaceMainComponent;
import net.datenwerke.rs.userprofile.client.userprofile.hooks.UserProfileCardProviderHook;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class TeamSpaceUIStartup {

	@Inject
	public TeamSpaceUIStartup(
		final WaitOnEventUIService waitOnEventService,
		final TeamSpaceDao rpcService,
			
		final HookHandlerService hookHandler,
		
		final Provider<TeamSpaceClientMainModule> mainModuleProvider,
		
		final Provider<EditTeamSpaceBasicSettingsHooker> editTeamSpaceBasicSettingsProvider,
		final Provider<EditTeamSpaceMembersHooker> editTeamSpaceMembersProvider,
		
		final TeamSpaceSecurityTargetDomainHooker securityTargetDomain,
		
		final UserProfileTeamSpacePropertiesHooker userProfileProvider,
		
		final SecurityUIService securityService,
		
		Provider<TeamSpaceProvider> teamSpaceProvider,
		
		final EventBus eventBus,
		final Provider<TeamSpaceMainComponent> teamSpaceMainComponentProvider,
		
		final TeamSpaceDao teamSpaceDao
		){
		
		/* create module provideR */
		final ClientMainModuleProviderHook mainModule = new ClientMainModuleProviderHook(mainModuleProvider);
		
		/* attach to load rights */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		
		/* request callback after login and check for rights */
		waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger() {
			
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(TeamSpaceGenericTargetIdentifier.class, ReadDto.class)){
					/* attach hooker */
					hookHandler.attachHooker(ClientMainModuleProviderHook.class, mainModule, HookHandlerService.PRIORITY_HIGH + 10);
					
					hookHandler.attachHooker(TeamSpaceEditDialogHook.class, editTeamSpaceBasicSettingsProvider, HookHandlerService.PRIORITY_HIGH);
					hookHandler.attachHooker(TeamSpaceEditDialogHook.class, editTeamSpaceMembersProvider);
					
					/* attach security target domains */
					hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);
					
					/* user profile */
					hookHandler.attachHooker(UserProfileCardProviderHook.class, userProfileProvider);
				} 
						
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
		
		/* simpleform */
		hookHandler.attachHooker(FormFieldProviderHook.class, teamSpaceProvider, HookHandlerService.PRIORITY_LOW);
		
		/* history */
		/* attach to eventbus */
		eventBus.addHandler(SubmoduleDisplayRequest.TYPE, new SubmoduleDisplayRequestHandler() {
			@Override
			public void onSubmoduleDisplayRequest(final SubmoduleDisplayRequest event) {
				if(TeamSpaceUIModule.TEAMSPACE_PANEL_ID.equals(event.getParentId())){
					final TeamSpaceMainComponent mainComponent = teamSpaceMainComponentProvider.get();
					eventBus.fireEvent(new SubmoduleDisplayRequest(mainComponent, null));
					
					Long id = (Long) event.getParameters().get(TeamSpaceUIModule.TEAMSPACE_ID_KEY);
					TeamSpaceDto teamSpace = new TeamSpaceDtoDec();
					teamSpace.setId(id);
					
					teamSpaceDao.reloadTeamSpace(teamSpace, new RsAsyncCallback<TeamSpaceDto>(){
						public void onSuccess(TeamSpaceDto result) {
							mainComponent.loadSpace(result);
							
							event.notifyCallback(mainComponent.getCurrentApps());						
						};
					});
				}
			}
		});
	}
}