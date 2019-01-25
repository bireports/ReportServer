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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.lfs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.terminal.service.terminal.hooks.SubCommand;
import net.datenwerke.rs.terminal.service.terminal.hooks.SubCommandContainerImpl;

public class LfsCommand extends SubCommandContainerImpl {
	
	public static final String BASE_COMMAND = "lfs";
	
	private HookHandlerService hookHandler;
	
	@Inject
	public LfsCommand(HookHandlerService hookHandler) {
		this.hookHandler = hookHandler;
	}

	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}

	@Override
	public List<SubCommand> getSubCommands() {
		List<LfsSubCommandHook> list =  hookHandler.getHookers(LfsSubCommandHook.class);
		return new ArrayList<SubCommand>(list);
	}

}
