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
 
 
package net.datenwerke.security.ext.server.usermanager;

import javax.persistence.NoResultException;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.utils.entitycloner.EntityClonerService;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.ext.client.usermanager.rpc.UserManagerTreeLoader;
import net.datenwerke.security.ext.client.usermanager.rpc.UserManagerTreeManager;
import net.datenwerke.security.ext.server.locale.DwSecurityMessages;
import net.datenwerke.security.server.TreeDBManagerTreeHandler;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.Group;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class UserManagerTreeHandlerImpl extends TreeDBManagerTreeHandler<AbstractUserManagerNode> implements UserManagerTreeLoader, UserManagerTreeManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7239838179341589474L;

	final private UserManagerService userManager;
	
	@Inject
	public UserManagerTreeHandlerImpl(
		UserManagerService userManager,	
		DtoService dtoGenerator,
		SecurityService securityService,
		EntityClonerService entityClonerService
		){
		
		super(userManager, dtoGenerator, securityService, entityClonerService);

		this.userManager = userManager;
	}
	
	@Override
	protected void doSetInitialProperties(AbstractUserManagerNode inserted) {
		if(inserted instanceof User){
			((User)inserted).setFirstname(DwSecurityMessages.INSTANCE.firstname());
			((User)inserted).setLastname(DwSecurityMessages.INSTANCE.lastname());
		} else if(inserted instanceof Group){
			((Group)inserted).setName(DwSecurityMessages.INSTANCE.unnamed()); 
		} else if(inserted instanceof OrganisationalUnit){
			((OrganisationalUnit)inserted).setName(DwSecurityMessages.INSTANCE.unnamed()); 
		}
	}

	@Override
	protected void doUpdateNode(AbstractNodeDto node, AbstractUserManagerNode realNode) {
		/* if user, set password by hand */
		if(realNode instanceof User){
			// TODO : add more sophisticated password criteria!
			if( null != ((UserDto)node).getPassword()){
				userManager.setPassword((User)realNode, ((UserDto)node).getPassword());
			}
		}
	}

	@SecurityChecked(
		argumentVerification = {
			@ArgumentVerification(
				name = "node",
				isDto = true,
				verify = @RightsVerification(rights=Write.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public AbstractNodeDto updateNode(@Named("node")AbstractNodeDto node, Dto state)  throws ServerCallFailedException  {
		/* if node is user, test that username has not been used already */
		if(node instanceof UserDto){
			UserDto user = (UserDto) node;
			if(null != user.getUsername()){
				try{
					User realUser = userManager.getUserByName(user.getUsername());
					if(null != realUser && ! realUser.getId().equals(node.getId()))
						throw new ExpectedException("Username already exists.");
				}catch(NoResultException ex){
				}
			}
		}
		
		return super.updateNode(node, state);
	}
	
	
}
