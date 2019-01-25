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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface TeamSpaceMessages extends Messages {

	public final static TeamSpaceMessages INSTANCE = GWT.create(TeamSpaceMessages.class);
	
	String adminButton();
	String clientModuleName();
	
	String configureCurrentSpaceText();
	String defaultTeamSpaceLabel();
	String deleteTeamSpaceConfirmMessage(String name);
	String deleteTeamSpaceConfirmTitle();
	String editTeamSpaceApplicationsDescription();
	String editTeamSpaceApplicationsHeadline();
	
	String editTeamSpaceApplicationsName();
	
	String editTeamSpaceApplicationsSubmitted();
	
	String editTeamSpaceBasicSettingsErrorMsg();
	
	String editTeamSpaceBasicSettingsName();
	
	String editTeamSpaceBasicSettingsSubmitted();
	String editTeamSpaceHeading(String name);
	
	String editTeamSpaceMembersAddMember();

	String editTeamSpaceMembersGridOUColumn();
	
	String editTeamSpaceMembersGridRoleColumn();
	String editTeamSpaceMembersName();
	String editTeamSpaceMembersRemoveAllMembers();
	
	String editTeamSpaceMembersRemoveMember();
	String editTeamSpaceMembersSubmitted();
	String editTeamSpaceWindowServerRequestMask();
	String loadPrimarySpaceMessage();
	String manageSpacesText();
	String memberRoleAdmin();
	String memberRoleGuest();
	String memberRoleManager();
	
	String memberRoleUser();
	String newTeamSpaceCreatedMessage();
	String newTeamSpaceDescriptionLabel();
	String newTeamSpaceHeading();
	
	String newTeamSpaceNameLabel();
	String removeCurrentSpaceConfirmText(String name);
	
	String removeCurrentSpaceConfirmTitle();
	String removeCurrentSpaceText();
	String submitCreateTeamSpace();
	
	String teamSpaceRemoved();
	
	
	String teamSpaceSecurityTitle();
	
	String teamSpaceSeucirtyDescription();
	
	String createSpaceText();
	
	String firstname();
	String lastname();
	String ou();
	
	String noAppInstalled();
	String noAppInstalledMsg();
	
	String noAccess();
	String noSpaceExists();
	String createNewSpace();
	String owner();
	String editTeamSpaceMembersAddGroupMember();
}
