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

import java.io.OutputStream;
import java.util.Collection;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.eximport.ex.Exporter;

/**
 * A service that provides access to exporting capabilities. 
 * 
 * @see ImportService
 *
 */
public interface ExportService {

	/**
	 * Exports the given {@link ExportConfig} as XML and returns its content as a {@link String}
	 * @param config The {@link ExportConfig}
	 * @return A {@link String} containing the XML data of the exported {@link ExportConfig}
	 */
	public String export(ExportConfig config);

	/**
	 * Exports the given {@link ExportConfig} as XML and writes the result to the supplied output stream.
	 * 
	 * @param config The configuration
	 * @param os The output stream
	 * 
	 * @throws XMLStreamException
	 */
	void exportAsStream(ExportConfig config, OutputStream os)
			throws XMLStreamException;
	
	/**
	 * Returns a {@link Collection} of {@link String}s holding the IDs of the given exporters
	 * 
	 * @param exporters A {@link Collection} of {@link Class}es holding the exporters
	 * @return A {@link Collection} containing the exporters IDs
	 */
	public Collection<String> getExporterIds(Collection<Class<?>> exporters);
	
	/**
	 * Returns the {@link Exporter} for the given exporter type
	 * 
	 * @param exporterType The {@link Class} of the exporter type
	 * @return The {@link Exporter} for the given type
	 */
	public Exporter getExporterFor(Class<?> exporterType);

	/**
	 * Exports the given {@link ExportConfig} as XML and returns its content as a {@link String}
	 * properly indendent.
	 * 
	 * @see OutputKeys#INDENT
	 * 
	 * @param config The {@link ExportConfig}
	 * @return A {@link String} containing the XML data of the exported {@link ExportConfig}
	 */
	String exportIndent(ExportConfig config);

	/**
	 * Returns the correct exporter for the given object or null if none was found.
	 * 
	 * @param object The object to be exported
	 * @return The exporter 
	 */
	Exporter getExporterFor(Object object);

	
}
