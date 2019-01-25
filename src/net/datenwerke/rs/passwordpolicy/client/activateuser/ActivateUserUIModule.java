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
 
 
package net.datenwerke.rs.passwordpolicy.client.activateuser;

import com.google.gwt.inject.client.AbstractGinModule;

public class ActivateUserUIModule extends AbstractGinModule{

	public static final String PROPERTY_CONTAINER = "Security_ActivateUser_PropertyContainer"; //$NON-NLS-1$
	public static final String PROPERTY_EMAIL_SUBJECT = "security:activateaccount:email:subject";
	public static final String PROPERTY_EMAIL_TEXT = "security:activateaccount:email:text";

	
	@Override
	protected void configure() {
		bind(ActivateUserUIStartup.class).asEagerSingleton();
	}
	
	
}
