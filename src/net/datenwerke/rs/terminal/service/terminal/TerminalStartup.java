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
 
 
package net.datenwerke.rs.terminal.service.terminal;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.terminal.service.terminal.basecommands.CatCommand;
import net.datenwerke.rs.terminal.service.terminal.basecommands.DescCommand;
import net.datenwerke.rs.terminal.service.terminal.basecommands.EchoCommand;
import net.datenwerke.rs.terminal.service.terminal.basecommands.ElizaCommand;
import net.datenwerke.rs.terminal.service.terminal.basecommands.HelloWorldCommand;
import net.datenwerke.rs.terminal.service.terminal.basecommands.HqlTerminalCommand;
import net.datenwerke.rs.terminal.service.terminal.basecommands.MeminfoCommand;
import net.datenwerke.rs.terminal.service.terminal.hijacker.data.SplitTableResultHijacker;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalSessionHijackHook;
import net.datenwerke.rs.terminal.service.terminal.operator.InBackgroundOperator;
import net.datenwerke.rs.terminal.service.terminal.operator.PipeOperator;
import net.datenwerke.rs.terminal.service.terminal.operator.TerminalCommandOperator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TerminalStartup {

	@Inject
	public TerminalStartup(
		HookHandlerService hookHandler,
		
		Provider<DescCommand> descCommandProvider,
		Provider<ElizaCommand> elizaCommandProvider,
		Provider<HelloWorldCommand> helloWorldCommandProvider,
		Provider<HqlTerminalCommand> hqlCommandProvider,
		Provider<MeminfoCommand> meminfoCommandProvider,
		Provider<EchoCommand> echoCommandProvider,
		Provider<CatCommand> catCommandProvider,
		
		Provider<InBackgroundOperator> inBgOperator,
		Provider<PipeOperator> pipeOperator,
		
		Provider<SplitTableResultHijacker> splitTableResultHijacker
		){
		
		hookHandler.attachHooker(TerminalCommandHook.class, descCommandProvider);
		hookHandler.attachHooker(TerminalCommandHook.class, elizaCommandProvider);
		hookHandler.attachHooker(TerminalCommandHook.class, helloWorldCommandProvider);
		hookHandler.attachHooker(TerminalCommandHook.class, hqlCommandProvider);
		hookHandler.attachHooker(TerminalCommandHook.class, meminfoCommandProvider);
		hookHandler.attachHooker(TerminalCommandHook.class, echoCommandProvider);
		hookHandler.attachHooker(TerminalCommandHook.class, catCommandProvider);
		
		hookHandler.attachHooker(TerminalCommandOperator.class, inBgOperator);
		hookHandler.attachHooker(TerminalCommandOperator.class, pipeOperator);
		
		hookHandler.attachHooker(TerminalSessionHijackHook.class, splitTableResultHijacker);
	}
}
