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
 
 
package net.datenwerke.rs;


import java.util.Properties;

import net.datenwerke.gf.service.jpa.annotations.JpaUnit;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;

import com.google.inject.persist.jpa.JpaPersistModule;

public class ReportServerPUModule extends AbstractReportServerModule {
	
	public final static String JPA_UNIT_NAME = "reportServerPU";
	private final static Properties jpaProperties = new Properties();


	@Override
	protected void configure() {
		/* database */
		bindConstant().annotatedWith(JpaUnit.class).to(JPA_UNIT_NAME); //$NON-NLS-1$
		bind(Properties.class).annotatedWith(JpaUnit.class).toInstance(jpaProperties);
		bind(ReportServerPUStartup.class).asEagerSingleton();

		install(new JpaPersistModule(JPA_UNIT_NAME).properties(jpaProperties));
	}
}
