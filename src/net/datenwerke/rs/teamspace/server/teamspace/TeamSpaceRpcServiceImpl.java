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
 
 
package net.datenwerke.rs.teamspace.server.teamspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.StrippedDownTeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.rpc.TeamSpaceRpcService;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceMember;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceRole;
import net.datenwerke.rs.teamspace.service.teamspace.genrights.TeamSpaceSecurityTarget;
import net.datenwerke.rs.teamspace.service.teamspace.security.rights.TeamSpaceAdministrator;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Delete;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * 
 *
 */
@Singleton
public class TeamSpaceRpcServiceImpl extends SecuredRemoteServiceServlet implements TeamSpaceRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8916329387136371782L;

	private final DtoService dtoService;
	private final TeamSpaceService teamSpaceService;
	private final UserManagerService userManager;
	
	@Inject
	public TeamSpaceRpcServiceImpl(
		DtoService dtoService,
		TeamSpaceService teamSpaceService,
		UserManagerService userManager
		){
		
		/* store objects */
		this.dtoService = dtoService;
		this.teamSpaceService = teamSpaceService;
		this.userManager = userManager;
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TeamSpaceDto getPrimarySpace() throws ServerCallFailedException {
		TeamSpace teamSpace = teamSpaceService.getPrimarySpace();
		
		return (TeamSpaceDto) dtoService.createDto(teamSpace);
	}
	
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=TeamSpaceSecurityTarget.class,
				verify=@RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void setPrimarySpace(TeamSpaceDto teamSpaceDto)
			throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights: tried to use a teamspace you have no access to as your primary teamspace");

		teamSpaceService.setPrimarySpace(teamSpace);
	}

	/**
	 * Creates a new teamspace with the currently logged in user as owner.
	 * 
	 * <h1>Necessary Rights</h1>
	 * Write on TeamSpaceTarget
	 */
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=TeamSpaceSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TeamSpaceDto createNewTeamSpace(TeamSpaceDto dummySpace)
			throws ServerCallFailedException {
		TeamSpace teamSpace = teamSpaceService.createTeamSpace();
		teamSpace.setName(dummySpace.getName());
		teamSpace.setDescription(dummySpace.getDescription());

		teamSpace = teamSpaceService.merge(teamSpace);
		
		return (TeamSpaceDto) dtoService.createDto(teamSpace);
	}

	/**
	 * Returns the teamspaces for the logged in user
	 * 
	 * <h1>Necessary Rights</h1>
	 * Read on TeamSpaceTarget
	 */
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=TeamSpaceSecurityTarget.class,
				verify=@RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public ListLoadResult<TeamSpaceDto> loadTeamSpaces()
			throws ServerCallFailedException {
		Collection<TeamSpace> teamSpaces = teamSpaceService.getTeamSpaces();
		
		List<TeamSpaceDto> dtos = new ArrayList<TeamSpaceDto>();
		for(TeamSpace space: teamSpaces)
			dtos.add((TeamSpaceDto) dtoService.createDto(space));
		
		return new ListLoadResultBean<TeamSpaceDto>(dtos);
	}

	/**
	 * Deletes the passed teamspace
	 *
	 * <h1>Necessary Rights</h1>
	 * Delete on TeamSpaceTarget and additionally: the user has to own the teamspace
	 * 
	 * 
	 */
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=TeamSpaceSecurityTarget.class,
				verify=@RightsVerification(rights=Delete.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void removeTeamSpace(TeamSpaceDto teamSpaceDto)
			throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		if(null == teamSpace)
			return;
		
		/* check rights */
		if(! teamSpaceService.isAdmin(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights: to remove a teamspace \"admin\" level access is required");

		/* delete team space */
		teamSpaceService.remove(teamSpace);
	}

	/**
	 * 
	 * <h1>Necessary rights</h1>
	 * User has to own the space, be a space manager or be a report administrator
	 */
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TeamSpaceDto editTeamSpaceSettings(TeamSpaceDto teamSpaceDto) throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);

		/* check rights */
		if(! teamSpaceService.isAdmin(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights: editing teamspace properties requires \"admin\" level access");

		/* merge and persist */
		dtoService.mergePoso(teamSpaceDto, teamSpace);
		teamSpace = teamSpaceService.merge(teamSpace);
		
		return (TeamSpaceDto) dtoService.createDto(teamSpace);
	}

	/**
	 * 
	 * <h1>Necessary rights</h1>
	 * User has to own the space, be a space manager or be a report administrator
	 */
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TeamSpaceDto setMembers(TeamSpaceDto teamSpaceDto,
			Collection<StrippedDownTeamSpaceMemberDto> members)
			throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.isManager(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights: to edit teamspace members the \"manager\" level access is required");
		
		Collection<Long> validMemberIds = new HashSet<Long>();
		
		for(StrippedDownTeamSpaceMemberDto strippedMember : members){
			try{
				User user = (User) userManager.getNodeById(strippedMember.getUserId());
				if(teamSpace.isOwner(user)){
					continue;
					//throw new ExpectedException("DEBUG: tried to add the teamspace owner as a member. aborting. ");
				}
				
				/* new role */
				TeamSpaceRole role = (TeamSpaceRole) dtoService.createPoso(strippedMember.getRole());
				
				/* is there already a member user */
				TeamSpaceMember member = teamSpaceService.getMemberFor(teamSpace, user);
				
				if(role == TeamSpaceRole.ADMIN && ! teamSpaceService.isAdmin(teamSpace)){
					if(null == member || member.getRole() != TeamSpaceRole.ADMIN)
						role = TeamSpaceRole.MANAGER;
				}
				
				if(null != member){
					member.setRole(role);
				} else {
					member = new TeamSpaceMember();
					member.setRole(role);
					member.setUser(user);
					
					teamSpaceService.persist(member);
					teamSpace.addMember(member);
				}
					
				validMemberIds.add(member.getId());
			}  catch(ServerCallFailedException e){
				throw e;
			} catch(Exception e){
				throw new ServerCallFailedException(e); 
			}
		}
		
		/* remove members */
		Iterator<TeamSpaceMember> memberIterator = teamSpace.getMembers().iterator();
		while(memberIterator.hasNext()){
			TeamSpaceMember member = memberIterator.next();
			if(! validMemberIds.contains(member.getId())){
				memberIterator.remove();
				teamSpaceService.remove(member);
			}
		}
			
		/* merge space */
		teamSpaceService.merge(teamSpace);
		
		return (TeamSpaceDto) dtoService.createDto(teamSpace);
	}

	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TeamSpaceDto reloadTeamSpace(TeamSpaceDto teamSpaceDto)
			throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);

		/* make sure user is allowed */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights: you tried to load a teamspace you have no access to");
			
		return (TeamSpaceDto) dtoService.createDto(teamSpace);
	}

	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=TeamSpaceSecurityTarget.class,
				verify=@RightsVerification(rights=TeamSpaceAdministrator.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public ListLoadResult<TeamSpaceDto> loadAllTeamSpaces()
			throws ServerCallFailedException {
		Collection<TeamSpace> spaces =  teamSpaceService.getAllTeamSpaces();
		
		List<TeamSpaceDto> dtos = new ArrayList<TeamSpaceDto>();
		for(TeamSpace ts : spaces)
			dtos.add((TeamSpaceDto)dtoService.createListDto(ts));
			
		return new ListLoadResultBean<TeamSpaceDto>(dtos);
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public TeamSpaceDto reloadTeamSpaceForEdit(TeamSpaceDto teamSpaceDto)
			throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);

		/* make sure user is allowed */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
			
		return (TeamSpaceDto) dtoService.createDtoFullAccess(teamSpace);
	}


}
