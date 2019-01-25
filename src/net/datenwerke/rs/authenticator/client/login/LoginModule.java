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
 
 
package net.datenwerke.rs.authenticator.client.login;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.rs.authenticator.client.login.sessiontimeout.SessionTimeoutWarningDialog;
import net.datenwerke.rs.authenticator.client.login.sessiontimeout.SessionTimeoutWatchdog;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class LoginModule extends AbstractGinModule{

	@Override
	protected void configure() {
		/* bind service */
		bind(LoginService.class).to(LoginServiceImpl.class).in(Singleton.class);
		
		/* bind startup */
		bind(LoginServiceStartup.class).asEagerSingleton();
		
		requestStaticInjection(SessionTimeoutWarningDialog.class);
		requestStaticInjection(SessionTimeoutWatchdog.class);
		requestStaticInjection(AuthenticatorWindow.class);
		
	}
	
}
