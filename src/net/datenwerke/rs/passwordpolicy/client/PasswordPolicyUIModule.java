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
 
 
package net.datenwerke.rs.passwordpolicy.client;

import net.datenwerke.rs.passwordpolicy.client.accountinhibition.AccountInhibitionUIModule;
import net.datenwerke.rs.passwordpolicy.client.activateuser.ActivateUserUIModule;

import com.google.gwt.inject.client.AbstractGinModule;

public class PasswordPolicyUIModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(PasswordPolicyUIStartup.class).asEagerSingleton();
	
		/* install submodule */
		install(new ActivateUserUIModule());
		install(new AccountInhibitionUIModule());
	}
}
