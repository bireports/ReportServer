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
 
 
package net.datenwerke.rs.uservariables.service.uservariables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import net.datenwerke.rs.uservariables.service.genrights.UserVariableAdminViewSecurityTarget;
import net.datenwerke.rs.uservariables.service.parameters.UserVariableParameterDefinition;
import net.datenwerke.rs.uservariables.service.parameters.UserVariableParameterDefinition__;
import net.datenwerke.rs.uservariables.service.uservariables.annotations.UserVariableTypes;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableInstance;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableInstance__;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.eventlogger.annotations.FireForceRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * 
 *
 */
public class UserVariableServiceImpl implements UserVariableService {

	private final Provider<EntityManager> entityManagerProvider;
	private final Set<UserVariableDefinition> availableUserVariableDefinitions;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	
	@Inject
	public UserVariableServiceImpl(
		Provider<EntityManager> entityManagerProvider,
		@UserVariableTypes Set<UserVariableDefinition> availableUserVariableDefinitions,
		Provider<AuthenticatorService> authenticatorServiceProvider
		){
		
		this.entityManagerProvider = entityManagerProvider;
		this.availableUserVariableDefinitions = availableUserVariableDefinitions;
		this.authenticatorServiceProvider = authenticatorServiceProvider;
	}
	
	@Override
	public Set<UserVariableDefinition> getAvailableUserVariableDefinitionTypes() {
		return availableUserVariableDefinitions;
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FireMergeEntityEvents
	public UserVariableDefinition merge(UserVariableDefinition definition) {
		EntityManager em = entityManagerProvider.get();
		return em.merge(definition);
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FirePersistEntityEvents
	public void persist(UserVariableDefinition definition) {
		EntityManager em = entityManagerProvider.get();
		em.persist(definition);
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FireRemoveEntityEvents
	public void remove(UserVariableDefinition definition) {
		EntityManager em = entityManagerProvider.get();
		definition = em.find(UserVariableDefinition.class, definition.getId());
		if(null != definition)
			em.remove(definition);
	}
	
	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FireForceRemoveEntityEvents
	public void forceRemove(UserVariableDefinition definition) {
		this.remove(definition);
	}

	@Override
	@QueryById
	public UserVariableDefinition getUserVariableDefinitionById(long id) {
		return null; // by magic
	}

	@Override
	@SimpleQuery
	public Set<UserVariableDefinition> getDefinedVariableDefinitions() {
		return null; // by magic
	}

	@Override
	@QueryByAttribute(where=UserVariableInstance__.folk)
	public Collection<UserVariableInstance> getDefinedInstancesFor(AbstractUserManagerNode folk) {
		return null; // magic
	}
	
	@Override
	public List<UserVariableInstance> getInheritedInstancesFor(AbstractUserManagerNode folk) {
		List<UserVariableInstance> inheritedVariables = new ArrayList<UserVariableInstance>();
		
		AbstractUserManagerNode parent = folk.getParent();
		while(null != parent){
			nextVariable: for(UserVariableInstance i : getDefinedInstancesFor(parent)){
				for(UserVariableInstance inh : inheritedVariables){
					if(inh.getDefinition().equals(i.getDefinition())){
						continue nextVariable;
					}
				}
				
				inheritedVariables.add(i);
			}
			parent = parent.getParent();
		}
		
		return inheritedVariables;
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FireMergeEntityEvents
	public UserVariableInstance merge(UserVariableInstance instance) {
		EntityManager em = entityManagerProvider.get();
		return em.merge(instance);
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FirePersistEntityEvents
	public void persist(UserVariableInstance instance) {
		EntityManager em = entityManagerProvider.get();
		em.persist(instance);
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FireRemoveEntityEvents
	public void remove(UserVariableInstance instance) {
		EntityManager em = entityManagerProvider.get();
		em.remove(em.find(UserVariableInstance.class, instance.getId()));
	}
	
	@Override
	@SecurityChecked(
			genericTargetVerification = @GenericTargetVerification(
				target=UserVariableAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		)
	@FireForceRemoveEntityEvents
	public void forceRemove(UserVariableInstance instance) {
		this.remove(instance);
	}

	@Override
	@QueryById
	public UserVariableInstance getUserVariableInstanceById(long id) {
		return null; // by magic
	}

	@Override
	public boolean hasVariableInstance(AbstractUserManagerNode folk, UserVariableDefinition definition) {
		for(UserVariableInstance instance: getDefinedInstancesFor(folk))
			if(instance.getDefinition().equals(definition))
				return true;
		return false;
	}

	@Override
	public UserVariableInstance getVariableInstanceForCurrentUser(UserVariableDefinition variableDefinition) {
		User user = authenticatorServiceProvider.get().getCurrentUser();
		return getVariableInstanceForUser(user, variableDefinition);
	}
	
	@QueryByAttribute(where=UserVariableInstance__.definition)
	public Collection<UserVariableInstance> getInstancesForDefinition(UserVariableDefinition def){
		return null; // magic
	}
	
	@Override
	@QueryByAttribute(where=UserVariableParameterDefinition__.userVariableDefinition)
	public Collection<UserVariableParameterDefinition> getParametersFor(UserVariableDefinition uvdd) {
		return null; // magic
	}

	@Override
	public UserVariableInstance getVariableInstanceForUser(User user, UserVariableDefinition variableDefinition) {
		if(null == user){
			throw new IllegalArgumentException("Failed retrieving value for UserVariable \"" + variableDefinition.getName() + "\": supplied User was null");
		}
		
		/* check variables defined at user */
		Collection<UserVariableInstance> instances = getDefinedInstancesFor(user);
		for(UserVariableInstance instance : instances)
			if(variableDefinition.equals(instance.getDefinition()))
				return instance;
		
		/* test inherited variables */
		instances = getInheritedInstancesFor(user);
		for(UserVariableInstance instance : instances)
			if(variableDefinition.equals(instance.getDefinition()))
				return instance;
		
		return null;
	}

}
