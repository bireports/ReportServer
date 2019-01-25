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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.util;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.utils.xml.XMLUtilsService;

import com.google.inject.Scopes;

/**
 * The jasper utils module provides some jasper related utility methods (e.g. for converting).
 * 
 * <h1>Description</h1>
 * <p>
 * The Jasper utils provides the user with methods for converting JRXML files and acting on them.
 * </p>
 * 
 * <h1>Content</h1>
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link JasperUtilsService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Client Module</h2>
 * <p>
 * <ul>
 * <li>{@link JasperUIModule}</li>
 * </ul>
 * </p>
 * 
 * <h2>Singletons</h2>
 * <p>
 * <ul>
 * <li>{@link JasperUtilsService}</li>
 * </ul>
 * </p>
 * 
 * <h1>Dependencies</h1>
 * 
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link XMLUtilsService}</li>
 * </ul>
 * </p>
 * 
 * <h2>3rd-Party</h2>
 * <p>
 * <ul>
 * <li><a href="http://code.google.com/p/google-guice/">Google Guice</a></li>
 * </ul>
 * </p>
 */
public class JasperUtilsModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		bind(JasperUtilsService.class).to(JasperUtilsServiceImpl.class).in(Scopes.SINGLETON);
	}

}
