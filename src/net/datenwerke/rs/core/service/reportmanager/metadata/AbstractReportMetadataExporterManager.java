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
 
 
package net.datenwerke.rs.core.service.reportmanager.metadata;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Provider;

/**
 * 
 *
 */
abstract public class AbstractReportMetadataExporterManager<E extends ReportMetadataExporter> {

	protected final Provider<Set<E>> exporters;
	
	public AbstractReportMetadataExporterManager(
		Provider<Set<E>> exporters	
		){
		this.exporters = exporters;
	}
	
	/**
	 * Gets a specific output generator that generates the specified format.
	 * 
	 * @param format
	 * @return The corresponding output generator
	 */
	public E getMetadataExporter(String format){
		if(null == format)
			throw new IllegalArgumentException("No format specified"); //$NON-NLS-1$
		
		for(E g : getRegisteredMetadataExporters())
			for(String f: g.getFormats())
				if(format.equals(f))
					return g;
		
		return null;
	}
	
	/**
	 * Returns all registered generators
	 * @return
	 */
	public Set<E> getRegisteredMetadataExporters(){
		return exporters.get();
	}
	
	/**
	 * Returns an array with all registered output formats
	 * 
	 * @return
	 */
	public String[] getRegisteredExporterFormats(){
		Set<String> formats = new HashSet<String>();

		for(E g : getRegisteredMetadataExporters())
			for(String format : g.getFormats())
				formats.add(format);
		
		return formats.toArray(new String[]{});
	}
}
