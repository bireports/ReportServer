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
 
 
package net.datenwerke.rs.core.service;

import com.google.inject.AbstractModule;

import net.datenwerke.rs.core.service.contexthelp.ContextHelpModule;
import net.datenwerke.rs.core.service.i18ntools.I18nToolsService;
import net.datenwerke.rs.core.service.i18ntools.I18nToolsServiceImpl;
import net.datenwerke.rs.core.service.i18ntools.RemoteMessageModule;
import net.datenwerke.rs.core.service.jarextension.ReportServerExtenderModule;
import net.datenwerke.rs.core.service.urlview.UrlViewModule;
import net.datenwerke.rs.core.service.urlview.UrlViewRestrictor;

public class RsCoreModule extends AbstractModule {

	public static final String TEMPLATES_CONFIG_FILE = "main/templates.cf";
	public static final String ERROR_TEMPLATE_PROPERTY = "errors.html";
	
	@Override
	protected void configure() {
		bind(I18nToolsService.class).to(I18nToolsServiceImpl.class);
		
		install(new ContextHelpModule());
		install(new RemoteMessageModule());
		
		install(new	ReportServerExtenderModule());
		
		install(new UrlViewModule());
		
		bind(RsCoreStartup.class).asEagerSingleton();
		
		requestStaticInjection(UrlViewRestrictor.class);
	}

}
