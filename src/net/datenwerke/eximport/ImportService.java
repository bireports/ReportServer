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

import java.util.List;

import net.datenwerke.eximport.im.ImportConfig;
import net.datenwerke.eximport.im.ImportResult;
import net.datenwerke.eximport.obj.EnclosedItemProperty;
import net.datenwerke.eximport.obj.ExportedItem;

import org.w3c.dom.Document;

/**
 * A service that allows to import previously exported objects.
 * 
 * @see ExportService
 *
 */
public interface ImportService {

	/**
	 * Returns a {@link List} of all {@link ExportedItem}s exported by the {@link ImportConfig}
	 * which needs to be configured.
	 * 
	 * @param config The {@link ImportConfig}
	 * @return A {@link List} of {@link ExportedItem}s
	 */
	public List<ExportedItem> getToBeConfigured(ImportConfig config);
	
	/**
	 * Imports the data from the given {@link ImportConfig}
	 * 
	 * @param config The {@link ImportConfig}
	 * @return The {@link ImportResult}
	 */
	public ImportResult importData(ImportConfig config);

	/**
	 * Returns the exported item identified by ID
	 * 
	 * @param data The {@link Document} exporting the desired item
	 * @param id The ID identifying the desired item
	 * @return The {@link ExportedItem}
	 */
	public ExportedItem getExportedItemById(ExportDataProvider dataProvider, String id);

	/**
	 * Returns the {@link EnclosedItemProperty} for the element identified by the given ID
	 * 
	 * @param data The {@link Document} holding the element
	 * @param id The ID of the element
	 * @return The {@link EnclosedItemProperty}
	 */
	public EnclosedItemProperty getEnclosedItemPropertyForId(ExportDataProvider dataProvider, String id);
	
}
