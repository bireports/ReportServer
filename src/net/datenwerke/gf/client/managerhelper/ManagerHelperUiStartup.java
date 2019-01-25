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
 
 
package net.datenwerke.gf.client.managerhelper;

import net.datenwerke.gf.client.managerhelper.hookers.BaseTreeComponentsForTreeNavToolbarHooker;
import net.datenwerke.gf.client.managerhelper.hooks.ManagerHelperTreeToolbarEnhancerHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ManagerHelperUiStartup {

	@Inject
	public ManagerHelperUiStartup(
		HookHandlerService hookHandler,
		
		Provider<BaseTreeComponentsForTreeNavToolbarHooker> baseTreeComponentsHooker
		){
		
		hookHandler.attachHooker(ManagerHelperTreeToolbarEnhancerHook.class, baseTreeComponentsHooker);
	}
}
