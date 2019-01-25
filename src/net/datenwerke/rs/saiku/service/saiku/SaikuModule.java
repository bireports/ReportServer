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
 
 
package net.datenwerke.rs.saiku.service.saiku;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import net.datenwerke.rs.saiku.server.rest.SaikuRestModule;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.saiku.reportengine.SaikuReportService;
import net.datenwerke.rs.saiku.service.saiku.reportengine.SaikuReportServiceImpl;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.metadata.SaikuMetadataExporter;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.metadata.SaikuPlainMetadataExporter;

public class SaikuModule extends AbstractModule {

	public final static String OUTPUT_FORMAT_CHART_HTML = "SAIKU_CHART_HTML";
	
	@Override
	protected void configure() {
		requestStaticInjection(MondrianDatasource.class);
		
		/* bind metadata exporter */
		Multibinder<SaikuMetadataExporter> metadataExporterBinder = Multibinder.newSetBinder(binder(), SaikuMetadataExporter.class);
		metadataExporterBinder.addBinding().to(SaikuPlainMetadataExporter.class);
		
		bind(OlapUtilService.class).to(OlapUtilServiceImpl.class);
		bind(OlapQueryService.class).to(OlapQueryServiceImpl.class);
		bind(SaikuReportService.class).to(SaikuReportServiceImpl.class);
		bind(SaikuStartup.class).asEagerSingleton();
		
		install(new SaikuRestModule());
	}


}
