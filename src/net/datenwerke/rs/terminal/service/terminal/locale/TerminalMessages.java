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
 
 
package net.datenwerke.rs.terminal.service.terminal.locale;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.Messages;


public interface TerminalMessages extends Messages{

	public final static TerminalMessages INSTANCE = LocalizationServiceImpl.getMessages(TerminalMessages.class);
	
	String commandDesc_description();
	String commandDesc_descFlag();
	String commandDesc_typeArgument();
	String commandDesc_objectsArgument();
	
	String commandEliza_description();
	String commandHelloWorld_description();
	String commandMeminfo_description();
	
	String commandHql_description();
	String commandHql_wFlag();
	String commandHql_hqlArgument();
	
	String helpDescription();
	String usage();
	String helpFlagDescription();
	
	String locationDoesNotExistException();
	String locationIsNoFolderException();
	String nodeDoesNotExistException();

	String commandCat_description();
	String cannotCatObject();
}

