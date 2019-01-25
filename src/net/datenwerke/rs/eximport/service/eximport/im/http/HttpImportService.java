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
 
 
package net.datenwerke.rs.eximport.service.eximport.im.http;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.datenwerke.eximport.ex.Exporter;
import net.datenwerke.eximport.exceptions.InvalidImportDocumentException;
import net.datenwerke.eximport.im.ImportItemConfig;
import net.datenwerke.eximport.im.ImportResult;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportPostProcessConfigDto;


public interface HttpImportService {

	boolean hasCurrentConfig();

	HttpImportConfiguration getCurrentConfig();

	void invalidateCurrentConfig();

	HttpImportConfiguration createNewConfig();

	void setData(String xmldata) throws InvalidImportDocumentException;

	Collection<String> getInvolvedExporterIds();

	void resetImportConfig();

	void addItemConfig(ImportItemConfig nodeConfig);

	void configureImport(Map<String, ImportConfigDto> configMap);

	List<ExportedItem> getExportedItemsFor(
			Class<? extends Exporter> exporterType) throws ClassNotFoundException;

	void runPostProcess(Map<String, ImportPostProcessConfigDto> configMap,
			ImportResult result);

}
