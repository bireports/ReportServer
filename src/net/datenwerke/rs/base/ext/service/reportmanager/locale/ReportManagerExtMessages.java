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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.locale;

import net.datenwerke.rs.utils.localization.Messages;


public interface ReportManagerExtMessages extends Messages{
	String commandReportmod_description();
	
	String commandReportmod_sub_setUUID_description();
	String commandReportmod_sub_setUUID_arg1();
	
	String commandReportmod_sub_setProperty_description();
	String commandReportmod_sub_setProperty_arg1();
	String commandReportmod_sub_setProperty_arg2();
	String commandReportmod_sub_setProperty_arg3();
	
	String commandReportmod_sub_removeProperty_description();
	String commandReportmod_sub_removeProperty_arg1();
	String commandReportmod_sub_removeProperty_arg2();
	
	String commandReportmod_sub_listProperty_description();
	String commandReportmod_sub_listProperty_arg1();
	String commandReportmod_sub_listProperty_arg2();
}

