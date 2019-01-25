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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.terminal.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceMember;
import net.datenwerke.rs.teamspace.service.teamspace.hooks.TeamspaceModSubCommandHook;
import net.datenwerke.rs.teamspace.service.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.Argument;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.ObjectResolverDeamon;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class AddUsersSubCommand implements TeamspaceModSubCommandHook {

public static final String BASE_COMMAND = "addusers";
	
	private final TeamSpaceService teamspaceService;

	@Inject
	public AddUsersSubCommand(
		TeamSpaceService teamspaceService
		){
		
		/* store objects */
		this.teamspaceService = teamspaceService;
	}
	
	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	@CliHelpMessage(
		messageClass = TeamSpaceMessages.class,
		name = BASE_COMMAND,
		description = "commandTeamspacemod_sub_useradd_description",
		args = {
			@Argument(flag="c", description="commandTeamspacemod_sub_useradd_cflag")
		},
		nonOptArgs = {
			@NonOptArgument(name="teamspaceId", description="commandTeamspacemod_sub_useradd_arg1", mandatory=true),
			@NonOptArgument(name="users", description="commandTeamspacemod_sub_useradd_arg2", varArgs=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> arguments = parser.getNonOptionArguments();
		if(1 > arguments.size())
			throw new IllegalArgumentException();
		
		ObjectResolverDeamon objectResolver = session.getObjectResolver();

		/* locate teamspace */
		String teamspaceLocator = arguments.remove(0);
		Collection<Object> teamspaceCandidates = objectResolver.getObjects(teamspaceLocator, Read.class);
		if(teamspaceCandidates.size() != 1)
			throw new IllegalArgumentException("Could not find teamspace single teamspace: " + teamspaceLocator);
		if(! (teamspaceCandidates.iterator().next() instanceof TeamSpace))
			throw new IllegalArgumentException("Could not find teamspace single teamspace: " + teamspaceLocator);
		TeamSpace teamspace = (TeamSpace) teamspaceCandidates.iterator().next();
			
		/* check rights */
		if(! teamspaceService.isManager(teamspace))
			throw new ViolatedSecurityException();
		
		/* get users */
		Set<User> userList = new HashSet<User>();
		for(String locationStr : arguments){
			Collection<Object> objectList = objectResolver.getObjects(locationStr, Read.class);
			if(objectList.isEmpty())
				throw new IllegalArgumentException("No users selected");
			
			for(Object obj : objectList){
				if(! (obj instanceof AbstractUserManagerNode))
					throw new IllegalArgumentException("Found unknown objects in object selection: " + obj.getClass());
				if(obj instanceof User)
					userList.add((User) obj);
			}
		}
		
		if(parser.hasOption("c")){
			Iterator<TeamSpaceMember> it = teamspace.getMembers().iterator();
			while(it.hasNext()){
				teamspaceService.remove(it.next());
				it.remove();
			}
		}
		
		/* add members */
		for(User user : userList){
			if(! teamspace.isMember(user))
				teamspace.addMember(new TeamSpaceMember(user));
		}
		
		teamspaceService.merge(teamspace);
		
		return new CommandResult();
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}

}
