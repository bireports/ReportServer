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
 
 
package net.datenwerke.eximport.im.enclosed;

import java.util.Collection;
import java.util.Queue;

import net.datenwerke.eximport.ex.enclosed.EnclosedObjectExporter;
import net.datenwerke.eximport.im.ImportItemConfig;
import net.datenwerke.eximport.im.ImportSupervisor;
import net.datenwerke.eximport.im.Importer;
import net.datenwerke.eximport.im.ImporterSpecificConfig;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporter;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporterFactory;
import net.datenwerke.eximport.obj.EnclosedItemProperty;
import net.datenwerke.eximport.obj.ExportedItem;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class EnclosedObjectImporter implements Importer {

	public static final String IMPORTER_ID = "EnclosedObjectImporter";
	
	protected BasicObjectImporterFactory objectImporterFactory;
	protected ImportSupervisor importSupervisor;
	
	@Inject
	public void setObjectImporterFactory(
			BasicObjectImporterFactory objectImporterFactory) {
		this.objectImporterFactory = objectImporterFactory;
	}
	
	@Override
	public void configure(ImportSupervisor importSupervisor,
			Collection<ImporterSpecificConfig> specificConfigs,
			Queue<ImportItemConfig> collection) {
		this.importSupervisor = importSupervisor;
	}

	@Override
	public boolean consumes(ExportedItem exportedItem, ImportItemConfig itemConfig) {
		return false;
	}

	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{EnclosedObjectExporter.class};
	}

	@Override
	public void importData() {
	}

	@Override
	public Object importEnclosedObject(EnclosedItemProperty property, boolean registerImportedObject) {
		BasicObjectImporter importer = objectImporterFactory.create(importSupervisor, property);
		Object imported = importer.importObject();
		return imported;
	}

	@Override
	public void importReference(ImportItemConfig itemConfig) {
	}

	@Override
	public boolean postProcess(String id, Object object, boolean enclosed) {
		return true;
	}

	@Override
	public String getId() {
		return IMPORTER_ID;
	}

	
}
