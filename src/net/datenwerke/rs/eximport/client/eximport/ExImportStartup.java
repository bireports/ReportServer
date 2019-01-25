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
 
 
package net.datenwerke.rs.eximport.client.eximport;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.client.eximport.security.ExportSecurityTargetDomainHooker;
import net.datenwerke.rs.eximport.client.eximport.security.ImportSecurityTargetDomainHooker;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;

import com.google.inject.Inject;

public class ExImportStartup {

	@Inject
	public ExImportStartup(
		HookHandlerService hookHandler,
			
		final ExportSecurityTargetDomainHooker exportSecurityTargetDomain,
		final ImportSecurityTargetDomainHooker importSecurityTargetDomain
		){
		
		/* attach to load rights */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(exportSecurityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, exportSecurityTargetDomain);

		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(importSecurityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, importSecurityTargetDomain);

	}
}
