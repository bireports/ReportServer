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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.locale;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.Messages;


public interface VfsMessages extends Messages{

	public final static VfsMessages INSTANCE = LocalizationServiceImpl.getMessages(VfsMessages.class);
	
	String commandLs_description();
	String commandLs_lArgument();
	String commandLs_dirArgument();
	
	String commandPwd_description();
	String commandPwd_argument();
	
	String commandListpath_description();
	String commandListpath_iflag();
	String commandListpath_objectarg();

	String commandMkdir_description();
	String commandMkdir_argument();
	
	String commandCp_description();
	String commandCp_source();
	String commandCp_target();
	
	String commandMv_description();
	String commandMv_source();
	String commandMv_target();
	
	String commandRm_description();
	String commandRm_rArgument();
	String commandRm_fArgument();
	String commandRm_dirArgument();
	
	String fileNotFound();
	String notSupported();
}

