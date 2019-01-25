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
 
 
package net.datenwerke.rs.uservariables.client.uservariables.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface UserVariablesMessages extends Messages {
	
	public final static UserVariablesMessages INSTANCE = GWT.create(UserVariablesMessages.class);
	
	public String confirmRemoveAllHeader();
	public String confirmRemoveAllText();
	public String confirmRemoveText();

	public String editVariable();

	public String mainPanelView_description();

	public String mainPanelView_header();

	public String mainPanelView_inheritedDescription();

	public String moduleName();


	public String removedVariable();

	public String uservariableManagamentHeading();

	public String userVariableManager();

	public String userVariablesegnericAdminHeading();

	public String userVariablesGenericAdmindescription();

	public String variableCreated();

	public String variableDeleted();

	public String viewDescription();
	public String viewHeadline();
	
	public String userVariablesParameterText();

	public String listVariableText();
	public String stringVariableText();
}
