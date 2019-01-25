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
 
 
package net.datenwerke.security.service.usermanager;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.Group;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.treedb.service.treedb.TreeDBManager;



/**
 * 
 *
 */
public interface UserManagerService extends TreeDBManager<AbstractUserManagerNode> {
	
	public static final String MODULE_NAME = "UserManager"; //$NON-NLS-1$
	
	public static final String SECUREE_ID = "UserManagerService_Default"; //$NON-NLS-1$

	/**
	 * Finds a {@link User} by name
	 * 
	 * @param name The {@link User}'s name
	 * @return The corresponding {@link User}
	 */
	public abstract User getUserByName(String name);
	
	/**
	 * Finds a {@link User} by email
	 * 
	 * @param name The {@link User}'s email
	 * @return The corresponding {@link User}
	 */
	public abstract User getUserByMail(String mail);

	/**
	 * Finds a {@link Group} by name
	 * 
	 * @param name The {@link Group}'s name
	 * @return The corresponding {@link Group}
	 */
	public abstract Group getGroupByName(String name);

	/**
	 * Searches for a {@link OrganisationalUnit} by its name
	 * @param name The name of the {@link OrganisationalUnit}
	 * @return The corresponding {@link OrganisationalUnit}
	 */
	public abstract  List<OrganisationalUnit> getOUsByName(String name);
	
	/**
	 * Searches for a {@link AbstractUserManagerNode} by its id
	 * 
	 * @param id The ID
	 * @return The {@link AbstractUserManagerNode} identified by the given ID
	 */
	public abstract AbstractUserManagerNode getNodeById(long id);
	
	/**
	 * Returns a {@link Collection} of all existing {@link User}s
	 * 
	 * @return A {@link Collection} of {@link User}s
	 */
	public abstract Collection<User> getAllUsers();
	
	/**
	 * Returns a {@link Collection} of all existing {@link Group}s
	 * 
	 * @return A {@link Collection} of {@link Group}s
	 */
	public abstract Collection<Group> getAllGroups();
	
	public abstract Collection<OrganisationalUnit> getAllOUs();
	
	/**
	 * Sets the {@link User}s password
	 * 
	 * @param user The {@link User}
	 * @param newPassword The new password
	 */
	public abstract void setPassword(User user, String newPassword);
	
	public void changePassword(User user, String oldPassword, String newPassword) throws ExpectedException;

	public void changePassword(String username, String oldPassword, String newPassword) throws ExpectedException;
	
	
	/**
	 * Persists the submitted {@link AbstractUserManagerNode}
	 * 
	 * @param node The {@link AbstractUserManagerNode}
	 */
	public void persist(AbstractUserManagerNode node);
	
	/**
	 * Merge's the submitted {@link AbstractUserManagerNode} with the representation
	 * in the database
	 * 
	 * @param node The {@link AbstractUserManagerNode}
	 */
	public AbstractUserManagerNode merge(AbstractUserManagerNode node);
	
	/**
	 * Removes the submitted {@link AbstractUserManagerNode} from the database
	 * 
	 * @param node {@link AbstractUserManagerNode}
	 */
	public void remove(AbstractUserManagerNode node);

	/**
	 * Tests whether the submitted {@link User} is part of the folk.
	 * 
	 * @param user The {@link User} to test
	 * @param folk The {@link AbstractUserManagerNode} (the folk)
	 * @return true if the {@link User} is part of folk; false otherwise
	 */
	public boolean userInFolk(User user, AbstractUserManagerNode folk);

	/**
	 * Returns a {@link Set} of {@link User}s defined by the given {@link Collection} of
	 * IDs
	 * 
	 * @param ids A {@link Collection} of {@link Long}s
	 * @return A {@link Set} of {@link User}s
	 */
	public abstract Set<User> getUsers(Collection<Long> ids);
	
	/**
	 * Returns {@link User} objects for all given IDs (be they user or group)
	 * 
	 * @param ids A {@link Collection} of {@link Long}s containing the IDs
	 * @param dereferenceGroups Set to <b>true</b> if the result should contain groups
	 * @return A {@link Set} of {@link User}s
	 */
	public abstract Set<User> getUsers(Collection<Long> ids, boolean dereferenceGroups);
	
	/**
	 * Returns a {@link Set} of {@link Group}s defined by the given {@link Collection} of
	 * IDs
	 * 
	 * @param groupIds A {@link Collection} of {@link Long}s
	 * @return A {@link Set} of {@link Group}s
	 */
	public abstract Set<Group> getGroups(Collection<Long> groupIds);

	public Collection<User> getUsersByMail(String email);

	public List<Group> getGroupsWithMember(Group group);

	public List<Group> getGroupsWithMember(OrganisationalUnit ou);

	/**
	 * Returns all groups in which the user is a direct or indirect member.
	 * 
	 * @param user
	 * @return
	 */
	Collection<Group> getReferencedGroups(User user);
	
}