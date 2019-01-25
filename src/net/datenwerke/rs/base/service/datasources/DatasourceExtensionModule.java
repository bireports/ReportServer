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
 
 
package net.datenwerke.rs.base.service.datasources;

import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.base.service.datasources.events.ConnectionExceptionEvent;
import net.datenwerke.rs.base.service.datasources.events.ConnectionPossiblyBrokenEvent;
import net.datenwerke.rs.base.service.datasources.statementmanager.StatementManagerService;
import net.datenwerke.rs.base.service.datasources.statementmanager.StatementManagerServiceImpl;
import net.datenwerke.rs.base.service.datasources.table.TableDatasourceModule;
import net.datenwerke.rs.base.service.datasources.transformers.DatasourceTransformationService;
import net.datenwerke.rs.base.service.datasources.transformers.DatasourceTransformationServiceImpl;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;

public class DatasourceExtensionModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		/* startup */
		bind(DatasourceExtensionStartup.class).asEagerSingleton();
		bind(StatementManagerService.class).to(StatementManagerServiceImpl.class);
		bind(DatasourceTransformationService.class).to(DatasourceTransformationServiceImpl.class);
		
		/* install private modules */
		install(new TableDatasourceModule());
		
		/* static injection */
		requestStaticInjection(
			DatabaseDatasource.class,
			ConnectionExceptionEvent.class,
			ConnectionPossiblyBrokenEvent.class
		);
	}

}
