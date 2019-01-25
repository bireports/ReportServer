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
 
 
package net.datenwerke.rs.core.client.datasourcemanager;

import net.datenwerke.gf.client.administration.AdministrationUIModule;
import net.datenwerke.gf.client.administration.AdministrationUIService;
import net.datenwerke.gf.client.administration.hooks.AdminModuleProviderHook;
import net.datenwerke.gf.client.history.HistoryUiService;
import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHook;
import net.datenwerke.gf.client.managerhelper.hooks.TreeConfiguratorHook;
import net.datenwerke.gf.client.treedb.TreeDBHistoryCallback;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.simpleform.DatasourceSimpleFormProvider;
import net.datenwerke.rs.core.client.datasourcemanager.hookers.MainPanelViewProviderHooker;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceManagerAdminViewTree;
import net.datenwerke.rs.core.client.datasourcemanager.provider.treehooker.DatasourceManagerTreeConfigurationHooker;
import net.datenwerke.rs.core.client.datasourcemanager.security.DatasourceManagerGenericTargetIdentifier;
import net.datenwerke.rs.core.client.datasourcemanager.security.DatasourceManagerViewSecurityTargetDomainHooker;
import net.datenwerke.rs.core.client.datasourcemanager.ui.DatasourceManagerPanel;
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
public class DatasourceUIStartup {

	@Inject
	public DatasourceUIStartup(
		final HookHandlerService hookHandler,
		Provider<DatasourceSimpleFormProvider> datasourceSimpleFormProvider,
		
		final WaitOnEventUIService waitOnEventService,
		final DatasourceUIService datasourceService,
		
		DatasourceManagerViewSecurityTargetDomainHooker securityTargetDomain,
		
		MainPanelViewProviderHooker mainPanelViewProvider,
		final Provider<DatasourceAdminModule> adminModuleProvider,
		final SecurityUIService securityService,
		
		final DatasourceManagerTreeConfigurationHooker treeConfigurator,
		
		HistoryUiService historyService,
		@DatasourceManagerAdminViewTree Provider<UITree> datasourceManagerTree,
		EventBus eventBus,
		Provider<DatasourceManagerPanel> datasourceManagerAdminPanel
		){
		
		/* config tree */
		hookHandler.attachHooker(TreeConfiguratorHook.class, treeConfigurator);
		
		/* attach Hooks */
		hookHandler.attachHooker(FormFieldProviderHook.class, datasourceSimpleFormProvider);
		
		/* attach security target domains */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);
		
		/* attach views */
		hookHandler.attachHooker(MainPanelViewProviderHook.class, mainPanelViewProvider);
		
		/* test if user has rights to see datasource manager admin view */
		waitOnEventService.callbackOnEvent(AdministrationUIService.REPORTSERVER_EVENT_HAS_ADMIN_RIGHTS, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(DatasourceManagerGenericTargetIdentifier.class, ReadDto.class)){
					hookHandler.attachHooker(AdminModuleProviderHook.class, new AdminModuleProviderHook(adminModuleProvider),  HookHandlerService.PRIORITY_HIGH + 2);
				}
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
		
		
		/* configureHistory */
		historyService.addHistoryCallback(DatasourceUIModule.DATASOURCE_FAV_HISTORY_TOKEN,
				new TreeDBHistoryCallback(datasourceManagerTree, eventBus, datasourceManagerAdminPanel, AdministrationUIModule.ADMIN_PANEL_ID));
	}
	
}
