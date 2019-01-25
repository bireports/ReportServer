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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper;

import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.metadata.JasperMetadataExporter;
import net.datenwerke.rs.base.service.reportengines.jasper.output.metadata.JasperPlainExporter;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsModule;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;

import com.google.inject.multibindings.Multibinder;

public class JasperReportModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		requestStaticInjection(
			JasperReport.class
		);
		
		/* bind metadata exporter */
		Multibinder<JasperMetadataExporter> metadataExporterBinder = Multibinder.newSetBinder(binder(), JasperMetadataExporter.class);
		metadataExporterBinder.addBinding().to(JasperPlainExporter.class);
		
		/* submodules */
		install(new JasperUtilsModule());
		
		bind(JasperReportStartup.class).asEagerSingleton();
	}
	
}
