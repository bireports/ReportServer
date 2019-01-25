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
 
 
package net.datenwerke.rs.birt.service;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;

import net.datenwerke.rs.birt.service.datasources.BirtDatasourceModule;
import net.datenwerke.rs.birt.service.reportengine.BirtReportService;
import net.datenwerke.rs.birt.service.reportengine.BirtReportServiceImpl;
import net.datenwerke.rs.birt.service.reportengine.output.metadata.BirtMetadataExporter;
import net.datenwerke.rs.birt.service.reportengine.output.metadata.BirtPlainMetadataExporter;
import net.datenwerke.rs.birt.service.reportengine.sandbox.BirtEngineEnvironmentFactory;
import net.datenwerke.rs.birt.service.utils.BirtUtilService;
import net.datenwerke.rs.birt.service.utils.BirtUtilServiceImpl;

public class BirtModule extends AbstractModule {

	public final static String BIRT_LIBRARY_BASE_FOLDER_ID_PROPERETY_NAME = "birt.library.folder.id";
	public final static String BIRT_LIBRARY_BASE_FOLDER_PATH_PROPERETY_NAME = "birt.library.folder.path";
	
	@Override
	protected void configure() {
		bind(BirtReportService.class).to(BirtReportServiceImpl.class).in(Singleton.class);
		
		install(new BirtDatasourceModule());
		
		/* bind metadata exporter */
		Multibinder<BirtMetadataExporter> metadataExporterBinder = Multibinder.newSetBinder(binder(), BirtMetadataExporter.class);
		metadataExporterBinder.addBinding().to(BirtPlainMetadataExporter.class);
	
		bind(BirtUtilService.class).to(BirtUtilServiceImpl.class);
	
		install(new FactoryModuleBuilder().build(BirtEngineEnvironmentFactory.class));
		
		bind(BirtStartup.class).asEagerSingleton();
	}
	
}
