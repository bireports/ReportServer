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
 
 
package net.datenwerke.rs.core.client;

import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectPreviewTabProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.genrights.AccessRsSecurityTargetDomainHooker;
import net.datenwerke.rs.core.client.objectinfo.BasicObjectInfo;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class RsCoreUiStartup {

	@Inject
	public RsCoreUiStartup(
		HookHandlerService hookHandler,
		
		AccessRsSecurityTargetDomainHooker securityTargetDomain,
		
		final Provider<BasicObjectInfo> baseObjectInfo
		
		){
		
		/* attach security target domains */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);
		
		hookHandler.attachHooker(ObjectPreviewTabProviderHook.class, baseObjectInfo, HookHandlerService.PRIORITY_LOWER);
	}
}
