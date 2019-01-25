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
 
 
package net.datenwerke.eximport;

import net.datenwerke.eximport.ex.ExportItemConfig;
import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.ex.ExportSupervisorFactory;
import net.datenwerke.eximport.ex.objectexporters.BasicObjectExporter;
import net.datenwerke.eximport.ex.objectexporters.BasicObjectExporterFactory;
import net.datenwerke.eximport.ex.objectexporters.EntityObjectExporter;
import net.datenwerke.eximport.ex.objectexporters.EntityObjectExporterFactory;
import net.datenwerke.eximport.im.ImportSupervisor;
import net.datenwerke.eximport.im.ImportSupervisorFactory;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporter;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporterFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * 
 *
 */
public class ExImportModule extends AbstractModule {

	@Override
	protected void configure() {
		/* services */
		bind(ImportService.class).to(ImportServiceImpl.class).in(Singleton.class);
		bind(ExportService.class).to(ExportServiceImpl.class).in(Singleton.class);
		
		/* assisted injection factories */
		bind(ExportSupervisorFactory.class).toProvider(FactoryProvider.newFactory(ExportSupervisorFactory.class, ExportSupervisor.class));
		bind(BasicObjectExporterFactory.class).toProvider(FactoryProvider.newFactory(BasicObjectExporterFactory.class, BasicObjectExporter.class));
		bind(EntityObjectExporterFactory.class).toProvider(FactoryProvider.newFactory(EntityObjectExporterFactory.class, EntityObjectExporter.class));
		
		bind(ImportSupervisorFactory.class).toProvider(FactoryProvider.newFactory(ImportSupervisorFactory.class, ImportSupervisor.class));
		bind(BasicObjectImporterFactory.class).toProvider(FactoryProvider.newFactory(BasicObjectImporterFactory.class, BasicObjectImporter.class));
		
		/* static injection */
		requestStaticInjection(
			ExportItemConfig.class,
			EnclosedObjectConfig.class
		);
		
		/* startup */
		bind(ExImportStartup.class).asEagerSingleton();
	}

}
