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
 
 
package net.datenwerke.rs.search.service.search.terminal.commands;

import java.util.List;

import net.datenwerke.gf.service.history.HistoryLink;
import net.datenwerke.gf.service.history.HistoryService;
import net.datenwerke.rs.search.service.search.SearchService;
import net.datenwerke.rs.search.service.search.locale.SearchMessages;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.Argument;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.utils.jpa.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class LocateCommand implements TerminalCommandHook{
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final static String BASE_COMMAND = "locate";

	private final SearchService searchService;
	private final EntityUtils entityUtils;
	private final HistoryService historyService;
	
	@Inject
	public LocateCommand(
			SearchService searchService,
			EntityUtils entityUtils, 
			HistoryService historyService
	) {
		this.searchService = searchService;
		this.entityUtils = entityUtils;
		this.historyService = historyService;
	}
	

	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	@CliHelpMessage(
			messageClass = SearchMessages.class,
			name = BASE_COMMAND,
			description = "commandSearch_description",
			args = {
				@Argument(flag="t", hasValue=true, valueName="type", description="commandSearch_arg1")
			},
			nonOptArgs = {
				@NonOptArgument(name="query", description="commandSearch_arg2", mandatory = true)
			}
		)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		Class<?> entityType; 
		
		if(parser.hasOption("t")){
			String typeName = String.valueOf(parser.parse("t:").valueOf("t"));
			entityType = entityUtils.getEntityBySimpleName(typeName);
		}else{
			entityType = Object.class;
		}
		
		List<?> searchResults;
		searchResults = searchService.locate(entityType, parser.getArgumentString());
		
		CommandResult cr = new CommandResult();
		for(Object o : searchResults){
			try {
				//cr.addResultLine(entityUtils.getId(o) + " - " + String.valueOf(o));
				List<HistoryLink> links = historyService.buildLinksFor(o);
				for(HistoryLink link : links){
					cr.addResultHyperLink(link.getObjectCaption() + " (" + link.getHistoryLinkBuilderId() + ")", link.getLink());
				}
			} catch (IllegalArgumentException e) {
				logger.warn( e.getMessage(), e);
			}
			
		}
		
		return cr;
	}
	


	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

}
