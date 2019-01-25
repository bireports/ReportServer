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
 
 
package net.datenwerke.security.ext.client.security.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface SecurityMessages extends Messages {
	
	public final static SecurityMessages INSTANCE = GWT.create(SecurityMessages.class);
	
	String accessControlEntries();
	String accessType();
	String accountBlockedAdministratively();
	String accountBlockedTemporarily();
	String accountExpirationDate();
	String accountInhibitionLabel();
	String accountUsable();
	String aceAdded();
	String aceApplied();
	String aceAtPosition(int pos);
	String AceManager();
	String acesDeleted();
	String allow();
	String changePasswordTitle();
	String confirmDeleteHeader();
	String confirmDeleteText();
	String deny();
	String dependsOn();
	String editACE();
	String fastAccess();
	String folk();
	String fullAccess();
	String generalSettings();
	String genericSecurityAdminViewDescription();
	String genericSecurityAdminViewHeading();
	String genericSecurityViewAdminModuleHeading();
	String genericSecurityViewHeading();
	String here();
	String hereAndInherited();
	String inheritance();
	String inherited();
	String inheritedACEs();
	String lostPassword();
	String lostPasswordMessageText();
	String lostPasswordSuccessNotam();
	String newPassword();
	String noAccess();
	String noInheritedACEs();
	String oldPassword();
	String passwordsDontMatch();
	String permissionManagement();
	String repeatPassword();
	String securityManagement();
	String statusCheckFailed();
	String undefined();
	String waitForStatus();
	String rights();
	String blockedTemporarily();
	
}
