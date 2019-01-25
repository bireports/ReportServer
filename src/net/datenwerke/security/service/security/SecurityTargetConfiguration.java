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
 
 
package net.datenwerke.security.service.security;

import net.datenwerke.security.service.security.entities.GenericSecurityTargetEntity;

/**
 * Configures a security target.
 * 
 * <p>
 * A security target is either a class or a specific entity object. The 
 * {@link SecurityTargetConfiguration} is used to define what securees
 * are interested in the target and what rights are defined.
 * </p>
 * 
 * <p>
 * Two types of security targets exist. A Generic Target is anything that
 * is not an entity and hence cannot store their own ACL. A special
 * entity object ({@link GenericSecurityTargetEntity}) is created that stores
 * the ACL for the generic target.
 * </p>
 * 
 *
 */
public class SecurityTargetConfiguration {

	/**
	 * Used for targets that do not maintain their own ACL
	 */
	private final Class<?> genericTarget;
	
	/**
	 * Used for targets that maintain their own ACL
	 */
	private final Class<? extends SecurityTarget> target;
	
	/**
	 * Stores the one responsible for this configuration
	 */
	private final Securee securee;
	
	/**
	 * Creates a new configuration for a target. 
	 * 
	 * <p>The submitted class implements SecurityTarget,
	 * then the object maintains it ACL directly. Otherwise
	 * SecurityService takes over.
	 * </p> 
	 * @param target
	 */
	public SecurityTargetConfiguration(Securee securee, Class<? extends SecurityTarget> target, Class<?> genericTarget){
		this.securee = securee;
		this.target = target;
		this.genericTarget = genericTarget;
	}
	
	public boolean isGenericTarget(){
		return genericTarget != null;
	}

	public Class<?> getGenericTarget() {
		return genericTarget;
	}

	public Class<? extends SecurityTarget> getTarget() {
		return target;
	}

	public Securee getSecuree() {
		return securee;
	}
}
