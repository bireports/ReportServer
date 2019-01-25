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
 
 
package net.datenwerke.rs.terminal.service.terminal;

import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletScopes;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.terminal.service.terminal.genrights.GenRightsTerminalManagerModule;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.HelpMessageInterceptor;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.objresolver.ObjectResolverModule;
import net.datenwerke.rs.terminal.service.terminal.vfs.VirtualFileSystemModule;

/**
 * 
 *
 */
public class TerminalModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		bind(TerminalService.class).to(TerminalServiceImpl.class).in(Singleton.class);
		bind(TerminalSessionMap.class).in(ServletScopes.SESSION);
		
		/* submodules */
		install(new VirtualFileSystemModule());
		install(new ObjectResolverModule());
		
		/* interceptors */
		HelpMessageInterceptor helpMessageInterceptor = new HelpMessageInterceptor();
		requestInjection(helpMessageInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(CliHelpMessage.class), helpMessageInterceptor);
		
		/* startup */
		bind(TerminalStartup.class).asEagerSingleton();
		
		/* rights */
		install(new GenRightsTerminalManagerModule());
	}

}
