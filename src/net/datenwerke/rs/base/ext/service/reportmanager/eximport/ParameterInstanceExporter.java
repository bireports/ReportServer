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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.eximport;

import java.lang.reflect.Field;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.EnclosedObjectConfig;
import net.datenwerke.eximport.ExImportHelperService;
import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.ex.enclosed.EnclosedEntityExporter;
import net.datenwerke.eximport.ex.objectexporters.BasicObjectExporter;
import net.datenwerke.eximport.ex.objectexporters.BasicObjectExporter.ObjectExporterAdjuster;
import net.datenwerke.eximport.ex.objectexporters.EntityObjectExporterFactory;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;

import com.google.inject.Inject;

public class ParameterInstanceExporter extends EnclosedEntityExporter {

	public static final String KEY_ATTRIBUTE = "definitionKey";
	
	@Inject
	public ParameterInstanceExporter(
		EntityObjectExporterFactory exporterFactory, 
		ExImportHelperService eiHelper
		) {
		super(exporterFactory, eiHelper);
	}

	@Override
	public boolean consumesEnclosedObject(EnclosedObjectConfig config) {
		return config.getEnclosed() instanceof ParameterInstance<?>;
	}
	
	@Override
	public void exportEnclosed(final ExportSupervisor exportSupervisor, EnclosedObjectConfig config) throws XMLStreamException {
		BasicObjectExporter exporter = exporterFactory.create(exportSupervisor, config.getId(), config.getEnclosed());
		exporter.setAdjuster(new ObjectExporterAdjuster() {
			
			@Override
			public void preProcess(BasicObjectExporter exporter) throws XMLStreamException {
				ParameterInstance<?> instance = (ParameterInstance<?>) exporter.getToExport();
				eiHelper.setAttribute(exportSupervisor.getXmlStream(), KEY_ATTRIBUTE, instance.getDefinition().getKey());
			}

			@Override
			public void postProcessField(BasicObjectExporter basicObjectExporter,
					Field exportableField, Object value) {}

			@Override
			public void configureBase() throws XMLStreamException {
			}

			@Override
			public void postProcess(BasicObjectExporter basicObjectExporter) {
				
			}
		});
		exporter.exportWithoutBaseElementCreation();
	}
}
