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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.metadata.JxlsMetadataExporter;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.metadata.JxlsPlainMetadataExporter;

public class JxlsReportModule extends AbstractModule {

	@Override
	protected void configure() {
		/* bind metadata exporter */
		Multibinder<JxlsMetadataExporter> metadataExporterBinder = Multibinder.newSetBinder(binder(), JxlsMetadataExporter.class);
		metadataExporterBinder.addBinding().to(JxlsPlainMetadataExporter.class);
		
		bind(JxlsReportService.class).to(JxlsReportServiceImpl.class);
		bind(JxlsReportStartup.class).asEagerSingleton();
		
	}

}
