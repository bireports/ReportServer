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
 
 
package net.datenwerke.eximport.im;

import java.util.Collection;
import java.util.Queue;

import net.datenwerke.eximport.exceptions.ImportException;
import net.datenwerke.eximport.obj.EnclosedItemProperty;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.rs.utils.reflection.ReflectionService;

import com.google.inject.Inject;

/**
 * 
 *
 */
public abstract class ImporterImpl<C extends ImportItemConfig> implements Importer {

	protected Queue<ImportItemConfig> itemConfigs;
	protected ImportSupervisor importSupervisor;
	protected Collection<ImporterSpecificConfig> specificConfigs;
	
	protected ReflectionService reflectionServices;
	
	@Inject
	public void setReflectionServices(ReflectionService reflectionServices){
		this.reflectionServices = reflectionServices;
	}
	
	@Override
	public void configure(ImportSupervisor supervisor, Collection<ImporterSpecificConfig> specificConfigs, Queue<ImportItemConfig> itemConfigs) {
		this.itemConfigs = itemConfigs;
		this.specificConfigs = specificConfigs;
		this.importSupervisor = supervisor;
	}
	
	protected <T extends ImporterSpecificConfig> T getSpecificConfig(Class<T> type){
		for(ImporterSpecificConfig config : specificConfigs)
			if(type.isAssignableFrom(config.getClass()))
				return (T)config;
		return null;
	}

	@Override
	public boolean consumes(ExportedItem exportedItem, ImportItemConfig itemConfig) {
		if(null == itemConfig) 
			return false;
		
		if(! reflectionServices.getGenericType(getClass()).isAssignableFrom(itemConfig.getClass()))
			return false;

		for(Class<?> recognizedExporter : getRecognizedExporters())
			if(exportedItem.getExporterType().equals(recognizedExporter))
				return true;
		
		return false;
	}

	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{};
	}

	@Override
	public final void importData() {
		while(! itemConfigs.isEmpty()){
			ImportItemConfig itemConfig = itemConfigs.poll();
			doImport(itemConfig);
		}
	}

	@Override
	public boolean postProcess(String id, Object object, boolean enclosed){
		return true;
	}
	
	@Override
	public void importReference(ImportItemConfig itemConfig) {
		if(! itemConfigs.contains(itemConfig))
			throw new ImportException("Item has already been imported or a terrible error (probably result has not been registered) occured. Id: " + itemConfig.getId());
		
		itemConfigs.remove(itemConfig);
		doImport(itemConfig);
	}
	
	@Override
	public Object importEnclosedObject(EnclosedItemProperty property, boolean registerImportedObject) {
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void doImport(ImportItemConfig itemConfig) {
		switch(itemConfig.getImportMode()){
		case IGNORE:
			doImportIgnoreMode((C) itemConfig);
			break;
		case CREATE:
			doImportCreateMode((C) itemConfig);
			break;
		case MERGE:
			doImportMergeMode((C) itemConfig);
			break;
		case REFERENCE:
			doImportReferenceMode((C) itemConfig);
			break;
		default:
			doImportOtherMode((C) itemConfig);
			break;
		}
	}

	protected void doImportCreateMode(C itemConfig) {
	}

	protected void doImportMergeMode(C itemConfig) {
	}

	protected void doImportReferenceMode(C itemConfig) {
	}

	protected void doImportIgnoreMode(C itemConfig) {
		importSupervisor.registerImportedObject(itemConfig.getId(), null);
		importSupervisor.notifyImportDone(itemConfig.getId(), null);
	}
	
	protected void doImportOtherMode(C itemConfig) {
	}
}
