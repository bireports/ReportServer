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
 
 
package net.datenwerke.rs.condition.service.condition;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.condition.service.condition.hookers.ScheduleConditionConfigProvider;
import net.datenwerke.rs.condition.service.condition.hookers.SchedulerVetoProvider;
import net.datenwerke.rs.condition.service.condition.terminal.commands.ConditionTerminalCommand;
import net.datenwerke.rs.condition.service.condition.terminal.commands.CreateConditionCommand;
import net.datenwerke.rs.condition.service.condition.terminal.commands.ListConditionCommand;
import net.datenwerke.rs.condition.service.condition.terminal.commands.RemoveConditionCommand;
import net.datenwerke.rs.condition.service.condition.terminal.commands.hooks.ConditionSubCommandHook;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerExecutionHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConditionStartup {

	@Inject
	public ConditionStartup(
		HookHandlerService hookHandler,
		
		Provider<ConditionTerminalCommand> condTerminalCommand,
		Provider<CreateConditionCommand> createCommand,
		Provider<ListConditionCommand> listCommand,
		Provider<RemoveConditionCommand> removeCommand,
		
		Provider<ScheduleConditionConfigProvider> conditionConfigProvider,
		
		Provider<SchedulerVetoProvider> vetoProvider
		){
		
		
		hookHandler.attachHooker(TerminalCommandHook.class, condTerminalCommand);
		
		hookHandler.attachHooker(ConditionSubCommandHook.class, createCommand);
		hookHandler.attachHooker(ConditionSubCommandHook.class, listCommand);
		hookHandler.attachHooker(ConditionSubCommandHook.class, removeCommand);
		
		
		hookHandler.attachHooker(ScheduleConfigProviderHook.class, conditionConfigProvider);
		
		hookHandler.attachHooker(SchedulerExecutionHook.class, vetoProvider);
	}
	
}
