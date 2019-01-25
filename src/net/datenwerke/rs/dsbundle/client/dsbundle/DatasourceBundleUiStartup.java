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
 
 
package net.datenwerke.rs.dsbundle.client.dsbundle;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.dsbundle.client.dsbundle.hooker.AuthenticatorWindowExtraOptionHooker;
import net.datenwerke.rs.dsbundle.client.dsbundle.hooker.DatabaseBundleConfigProviderHooker;
import net.datenwerke.rs.enterprise.client.EnterpriseCheckUiModule;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.security.client.login.hooks.AuthenticatorWindowExtraOptionHook;

public class DatasourceBundleUiStartup {

	@Inject
	public DatasourceBundleUiStartup(
		final WaitOnEventUIService waitOnEventService,
		final HookHandlerService hookHandler,
		Provider<DatabaseBundleConfigProviderHooker> configProvider,
		final AuthenticatorWindowExtraOptionHooker authenticatorWindowExtraOptionHooker,
		
		final DatasourceBundleDao dsBundleDao,
		final DatasourceBundleUiService dsBundleService,
		
		final EnterpriseUiService enterpriseService
		){
		
		hookHandler.attachHooker(DatasourceDefinitionConfigProviderHook.class, configProvider, 60);
		
		waitOnEventService.callbackOnEvent(EnterpriseCheckUiModule.REPORTSERVER_ENTERPRISE_DETERMINED_BEFORE_LOGIN, new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				hookHandler.detachHooker(AuthenticatorWindowExtraOptionHook.class, authenticatorWindowExtraOptionHooker);
				
				if(! enterpriseService.isEnterprise())
					waitOnEventService.signalProcessingDone(ticket);
				else {
					dsBundleDao.getAvailableBundleKeys(new RsAsyncCallback<List<String>>(){
						public void onSuccess(List<String> result) {
							dsBundleService.setAvailableBundleKeys(result);
							
							if(null != result && ! result.isEmpty())
								hookHandler.attachHooker(AuthenticatorWindowExtraOptionHook.class, authenticatorWindowExtraOptionHooker);
							
							waitOnEventService.signalProcessingDone(ticket);
						};
						@Override
						public void onFailure(Throwable caught) {
							waitOnEventService.signalProcessingDone(ticket);
						}
					});
				}
								
			}
		});
	}
}
