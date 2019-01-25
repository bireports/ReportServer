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
 
 
package net.datenwerke.eximport.im;

import java.util.Collection;
import java.util.Queue;

import net.datenwerke.eximport.obj.EnclosedItemProperty;
import net.datenwerke.eximport.obj.ExportedItem;

/**
 * 
 *
 */
public interface Importer {

	public String getId();
	
	public Class<?>[] getRecognizedExporters();
	
	public boolean consumes(ExportedItem exportedItem, ImportItemConfig itemConfig);
	
	public void configure(ImportSupervisor importSupervisor, Collection<ImporterSpecificConfig> specificConfigs, Queue<ImportItemConfig> collection);
	
	public void importData();

	public void importReference(ImportItemConfig itemConfig);

	public Object importEnclosedObject(EnclosedItemProperty property, boolean registerImportedObject);

	public boolean postProcess(String id, Object object, boolean enclosed);
	
}
