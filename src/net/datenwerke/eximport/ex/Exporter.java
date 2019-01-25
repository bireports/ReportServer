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
 
 
package net.datenwerke.eximport.ex;

import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.EnclosedObjectConfig;
import net.datenwerke.eximport.obj.ExportedItem;

/**
 * 
 *
 */
public interface Exporter {

	public String getExporterId();
	
	/**
	 * Tells the exportservice whether this exporter can handle the given config item.
	 * 
	 * @param config
	 * @return
	 */
	public boolean consumes(ExportItemConfig<?> config);
	
	/**
	 * Tells the exportservice if this exporter can export the given object. 
	 * 
	 * <p>This case occurs if an enclosed entity is encountered and the exporter
	 * asks the supervisor for help</p>
	 *
	 * @param enclosed
	 * @return
	 */
	public boolean consumesEnclosedObject(EnclosedObjectConfig config);
	
	/**
	 * Starts the export of the given configuration.
	 * 
	 * @param exportSupervisor
	 * @throws XMLStreamException 
	 */
	public void export(ExportSupervisor exportSupervisor) throws XMLStreamException;

	/**
	 * Exports an enclosed entity.
	 * 
	 * @see #consumesEnclosedObject(Object)
	 * @param enclosed 
	 * @return
	 * @throws XMLStreamException 
	 */
	public void exportEnclosed(ExportSupervisor exportSupervisor, EnclosedObjectConfig config) throws XMLStreamException;
	
	/**
	 * Configures the exporter and tells it what items it should export.
	 * 
	 * <p>Exporter should throw Exception in case the configuration is invalid.</p>
	 * 
	 * @param queue
	 */
	public void configure(Collection<ExporterSpecificExportConfig> specificConfigs, Collection<ExportItemConfig<?>> configItems);

	
	public Collection<ExportItemConfig<?>> addReferences(
			ExportSupervisor exportSupervisor, Collection<ExportItemConfig<?>> configItems);
	
	public Collection<EnclosedObjectConfig> addEnclosed(
			ExportSupervisor exportSupervisor, Collection<ExportItemConfig<?>> configItems);
	
	
	/**
	 * Asks the exporter to generate an export config for the given object.
	 * 
	 * <p>If the exporter does not know the item, it should return null</p>
	 * 
	 * @param object
	 * @return
	 */
	public ExportItemConfig<?> generateExportConfig(Object object);

	public boolean hasConfigFor(Object value);

	public ExportItemConfig<?> getConfigFor(Object value);

	public Collection<ExportItemConfig<?>> addReferences(
			ExportSupervisor exportSupervisor, EnclosedObjectConfig enclosedCon);

	public Collection<EnclosedObjectConfig> addEnclosed(
			ExportSupervisor exportSupervisor, EnclosedObjectConfig enclosedCon);
	
	
	/**
	 * Generates a name for the ExportedItem to display to the user
	 * 
	 * @param exportedItem
	 * @return
	 */
	public String getDisplayNameFor(ExportedItem exportedItem);

	public boolean consumes(Object object);
	
}
