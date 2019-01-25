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
 
 
package net.datenwerke.rs.scripting.service.scripting.locale;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.Messages;

import com.google.gwt.core.client.GWT;


public interface ScriptingMessages extends Messages{

	public final static ScriptingMessages INSTANCE = LocalizationServiceImpl.getMessages(ScriptingMessages.class);
	
	String commandPs_description();
	String commandKill_description();
	String commandKill_description_forceFlag();
	String commandKill_description_id();
	
	String commandExec_description();
	String commandExec_cFlag();
	String commandExec_sFlag();
	String commandExec_nFlag();
	String commandExec_tFlag();	
	String commandExec_eFlag();
	String commandExec_execArgument();
	
	String commandScheduleScript_description();
	
	String commandScheduleScript_sub_list_description();
	
	String commandScheduleScript_sub_execute_description();
	String commandScheduleScript_sub_setProperty_arg1();
	String commandScheduleScript_sub_setProperty_arg2();
	
}

