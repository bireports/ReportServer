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
 
 
package net.datenwerke.usermanager.ext.service.history;

import com.google.inject.Inject;

import net.datenwerke.rs.core.service.history.helper.TreePanelHistoryUrlBuilderHooker;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.ext.client.usermanager.UserManagerUIModule;
import net.datenwerke.security.service.genrights.usermanager.UserManagerAdminViewSecurityTarget;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.usermanager.ext.service.locale.UserManagerMessages;

public class UserManagerHistoryUrlBuilderHooker extends TreePanelHistoryUrlBuilderHooker {

	private final UserManagerMessages messages = LocalizationServiceImpl.getMessages(UserManagerMessages.class);
	
	private final static String HISTORY_BUILDER_NAME = "Usermanager";

	private final SecurityService securityService;
	
	@Inject
	public UserManagerHistoryUrlBuilderHooker(SecurityService securityService){
		this.securityService = securityService;
		
	}
	
	@Override
	public boolean consumes(Object o) {
		if(!( o instanceof AbstractUserManagerNode))
			return false;
		
		return securityService.checkRights(UserManagerAdminViewSecurityTarget.class, Read.class);
	}

	@Override
	protected String getTokenName() {
		return UserManagerUIModule.USERMANAGER_FAV_HISTORY_TOKEN;
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
		return "users";
	}

}
