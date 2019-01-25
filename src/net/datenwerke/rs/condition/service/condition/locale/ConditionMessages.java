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
 
 
package net.datenwerke.rs.condition.service.condition.locale;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.Messages;


public interface ConditionMessages extends Messages{

	public final static ConditionMessages INSTANCE = LocalizationServiceImpl.getMessages(ConditionMessages.class);
	
	String commandRcondition_description();
	
	String commandRcondition_sub_list_desc();
	
	String commandRcondition_sub_create_desc();
	String commandRcondition_sub_create_par_reportId();
	String commandRcondition_sub_create_par_key();
	String commandRcondition_sub_create_par_name();
	String commandRcondition_sub_create_par_desc();
	
	String commandRcondition_sub_remove_desc();

	String skipMsg(String name);

	String retryMsg(String name);
}

