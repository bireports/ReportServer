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
 
 
package net.datenwerke.eximport.ex.entity;

import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.EnclosedObjectConfig;
import net.datenwerke.eximport.ex.ExportItemConfig;
import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.ex.ExporterImpl;
import net.datenwerke.eximport.ex.objectexporters.BasicObjectExporter;
import net.datenwerke.eximport.ex.objectexporters.EntityObjectExporter;
import net.datenwerke.eximport.ex.objectexporters.EntityObjectExporterFactory;

import com.google.inject.Inject;

public abstract class GenericEntityExporter extends ExporterImpl<EntityExportItemConfig> {

	private EntityObjectExporterFactory entityExporterFactory;
	
	@Inject
	public void setEntityExporter(EntityObjectExporterFactory entityExporterFactory){
		this.entityExporterFactory = entityExporterFactory;
	}
	
	@Override
	public boolean consumes(ExportItemConfig<?> config) {
		if(! super.consumes(config)) 
			return false;
		
		return consumes(config.getItem());
	}
	
	@Override
	public boolean consumes(Object object) {
		if(null == object)
			return false;
		for(Class<?> exportableType : getExportableTypes())
			if(exportableType.isAssignableFrom(object.getClass()))
				return true;
		return false;
	}
	
	@Override
	protected void doExport(ExportSupervisor exportSupervisor,
			EntityExportItemConfig item) throws XMLStreamException {
		Object toExport = item.getItem();
		
		/* export */
		BasicObjectExporter exporter = entityExporterFactory.create(exportSupervisor, item.getId(), toExport);
		exporter.export();
	}

	@Override
	protected void doAddReferences(ExportSupervisor exportSupervisor,
			EntityExportItemConfig item, Collection<ExportItemConfig<?>> references) {
		EntityObjectExporter exporter = entityExporterFactory.create(exportSupervisor, item.getId(), item.getItem());
		references.addAll(exporter.getReferences());
	}
	
	@Override
	protected void doAddEnclosed(ExportSupervisor exportSupervisor, EntityExportItemConfig item,
			Collection<EnclosedObjectConfig> enclosed) {
		EntityObjectExporter exporter = entityExporterFactory.create(exportSupervisor, item.getId(), item.getItem());
		enclosed.addAll(exporter.getEnclosed());
	}
	
	@Override
	public ExportItemConfig<?> generateExportConfig(Object object) {
		for(Class<?> exportableType : getExportableTypes())
			if(exportableType.isAssignableFrom(object.getClass()))
				return new EntityExportItemConfig(object);
		return null;
	}
	
	abstract protected Class<?>[] getExportableTypes();
}
