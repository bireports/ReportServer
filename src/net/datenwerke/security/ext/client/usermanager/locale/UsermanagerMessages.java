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
 
 
package net.datenwerke.security.ext.client.usermanager.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface UsermanagerMessages extends Messages {
	
	public final static UsermanagerMessages INSTANCE = GWT.create(UsermanagerMessages.class);
	
	String assignedRoles();
	String assignedUsers();
	String memberOus();
	
	
	String changePassword();
	String changePasswordDescription();
	String editOU();
	String editRole();
	String editUser();
	String email();
	String exportGroup();
	String firstname();
	String gender();
	String genderFemale();
	String genderMale();
	String importConfigFailureNoParent();
	String importerName();
	String importMainPropertiesDescription();
	String importMainPropertiesHeadline();
	String importWhereTo();
	String lastname();
	String ou();
	String password();
	String passwordChangeSuccess();
	String personDetails();
	String quickExportText();
	String role();
	String security();
	String user();
	String usermanagement();
	String usermanagementDescription();
	String userManagernavtext();
	
	String username();
	
	String userProfileUserDataName();
	
	String userProfileViewContainerName();
	String noRecipientSelected();
	

}
