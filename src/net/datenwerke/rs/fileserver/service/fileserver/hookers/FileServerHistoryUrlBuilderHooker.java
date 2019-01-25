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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.hookers;

import net.datenwerke.rs.core.service.history.helper.TreePanelHistoryUrlBuilderHooker;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerUiModule;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.genrights.FileServerManagerAdminViewSecurityTarget;
import net.datenwerke.rs.fileserver.service.fileserver.locale.FileserverMessages;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

public class FileServerHistoryUrlBuilderHooker extends TreePanelHistoryUrlBuilderHooker {

	private final FileserverMessages messages = LocalizationServiceImpl.getMessages(FileserverMessages.class);
	
	private final static String HISTORY_BUILDER_NAME = "FileServerManager";

	private final SecurityService securityService;

	@Inject
	public FileServerHistoryUrlBuilderHooker(SecurityService securityService){
		this.securityService = securityService;
		
	}
	
	@Override
	public boolean consumes(Object o) {
		if(! (o instanceof AbstractFileServerNode))
			return false;
		
		return securityService.checkRights(FileServerManagerAdminViewSecurityTarget.class, Read.class);
	}

	@Override
	protected String getTokenName() {
		return FileServerUiModule.FILESERVER_HISTORY_TOKEN;
	}

	@Override
	protected String getBuilderId() {
		return HISTORY_BUILDER_NAME;
	}

	@Override
	protected String getNameFor(Object o) {
		return messages.historyUrlBuilderName();
	}

	@Override
	protected String getIconFor(Object o) {
		return messages.historyUrlBuilderIcon();
	}

}
