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
 
 
package net.datenwerke.eximport.im.entity;

import javax.persistence.EntityManager;

import net.datenwerke.eximport.im.ImporterImpl;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporter;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporterFactory;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.rs.utils.jpa.EntityUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class GenericEntityImporter extends ImporterImpl<EntityImportItemConfig> {

	public static final String IMPORTER_ID = "GenericEntityImporter";
	
	protected BasicObjectImporterFactory objectImporterFactory;
	protected EntityUtils jpaService;
	protected Provider<EntityManager> entityManagerProvider;

	@Override
	public String getId() {
		return IMPORTER_ID;
	}
	
	@Inject
	public void setObjectImporterFactory(BasicObjectImporterFactory objectImporterFactory){
		this.objectImporterFactory = objectImporterFactory;
	}
	
	@Inject
	public void setJpaServices(EntityUtils jpaService){
		this.jpaService = jpaService;
	}
	
	@Inject
	public void setEntityManagerProvider(Provider<EntityManager> entityManagerProvider){
		this.entityManagerProvider = entityManagerProvider;
	}
	
	@Override
	protected void doImportCreateMode(final EntityImportItemConfig itemConfig) {
		/* create importer */
		ExportedItem exportedItem = importSupervisor.getExportedItemFor(itemConfig);
		BasicObjectImporter importer = objectImporterFactory.create(importSupervisor, exportedItem);
		importer.importObject();
	}
	
	@Override
	protected void doImportReferenceMode(EntityImportItemConfig itemConfig) {
		importSupervisor.registerExternalReference(itemConfig.getId(), itemConfig.getReferenceObject());
	}
	
	@Override
	public boolean postProcess(String id, Object object, boolean enclosed) {
		if(jpaService.isEntity(object)){
			try{
				if(null == jpaService.getId(object))
					entityManagerProvider.get().persist(object);
				return true;
			} catch(Exception e){
				return false;
			}
		}
		
		return true;
	}
}
