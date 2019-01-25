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
 
 
package net.datenwerke.security.client.security.hooks;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.security.client.security.GenericTargetIdentifier;

import com.google.gwt.resources.client.ImageResource;

/**
 * Defines a hook for attaching generic security targets to the generic security target view.
 * 
 *
 */
public interface GenericSecurityViewDomainHook extends Hook {

	/**
	 * The target's name, which is used as link in the navigation panel.
	 * @return
	 */
	public String genericSecurityViewDomainHook_getName();
	
	/**
	 * A longer description which describes the target. 
	 * @return
	 */
	public String genericSecurityViewDomainHook_getDescription();
	
	/**
	 * The icon used in the navigation panel
	 * @return
	 */
	public ImageResource genericSecurityViewDomainHook_getIcon();
	
	/**
	 * A mapping object which is used to identify the marker class on the server side.
	 * 
	 * <p>This is used to be typesafe and not to have strings.</p>
	 * 
	 * @return
	 */
	public GenericTargetIdentifier genericSecurityViewDomainHook_getTargetId();
}
