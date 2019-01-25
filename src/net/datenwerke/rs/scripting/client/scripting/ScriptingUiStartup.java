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
 
 
package net.datenwerke.rs.scripting.client.scripting;

import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.codemirror.hooks.CodeMirrorKeyboardHook;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.enterprise.client.EnterpriseCheckUiModule;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.scripting.client.scripting.codemirror.plugin.GroovyImportCompleter;
import net.datenwerke.rs.scripting.client.scripting.hookers.ScriptingCommandResultProcessorHooker;
import net.datenwerke.rs.terminal.client.terminal.TerminalUIService;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;

public class ScriptingUiStartup {
	
	@Inject
	public ScriptingUiStartup(
		HookHandlerService hookHandler,
		final ScriptingDao scriptingDao,
		final TerminalUIService terminalService,
		final WaitOnEventUIService waitOnEventService,
		
		Provider<ScriptingCommandResultProcessorHooker> commandResultProcessor,
		
		Provider<GroovyImportCompleter> groovyImportCompleterProvider,
		
		final EnterpriseUiService enterpriseUiService
		){
		
		hookHandler.attachHooker(CodeMirrorKeyboardHook.class, groovyImportCompleterProvider);
		
		hookHandler.attachHooker(CommandResultProcessorHook.class, commandResultProcessor);
		
		/* execute loginScript */
		waitOnEventService.callbackOnEvent(EnterpriseCheckUiModule.REPORTSERVER_ENTERPRISE_DETERMINED_AFTER_LOGIN, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(!enterpriseUiService.isEnterprise())
					waitOnEventService.signalProcessingDone(ticket);
				else {
					scriptingDao.executeLoginScript(new RsAsyncCallback<List<CommandResultDto>>(){
						public void onSuccess(final List<CommandResultDto> result) {
							Scheduler.get().scheduleDeferred(new ScheduledCommand() {
								@Override
								public void execute() {
									if(null != result)
										for(CommandResultDto res : result)
											terminalService.processExternalResult(res);
								}
							});
							waitOnEventService.signalProcessingDone(ticket);
						};
						@Override
						public void onFailure(Throwable caught) {
							waitOnEventService.signalProcessingDone(ticket);
						}
					});
				}
			}
		});
		
	}

}
