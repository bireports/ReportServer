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
 
 
package net.datenwerke.rs.base.ext.service.security.eximport;

import net.datenwerke.eximport.im.entity.EntityImportItemConfig;
import net.datenwerke.eximport.im.entity.GenericEntityImporter;
import net.datenwerke.eximport.im.objectimporters.BasicObjectImporter;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.entities.Ace;
import net.datenwerke.security.service.security.entities.GenericSecurityTargetEntity;

import com.google.inject.Inject;

public class GenericRightsImporter extends GenericEntityImporter {

	public static final String IMPORTER_ID = "GenericRightsImporter";
	
	private SecurityService securityService;
	
	@Inject
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@Override
	public String getId() {
		return IMPORTER_ID;
	}

	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{GenericRightsExporter.class};
	}
	
	@Override
	protected void doImportMergeMode(EntityImportItemConfig itemConfig) {
		GenericSecurityTargetEntity securityTarget = (GenericSecurityTargetEntity) itemConfig.getReferenceObject();
		
		ExportedItem exportedItem = importSupervisor.getExportedItemFor(itemConfig);
		BasicObjectImporter importer = objectImporterFactory.create(importSupervisor, exportedItem);
		importer.setRegisterImportedObject(false);
		GenericSecurityTargetEntity imported = (GenericSecurityTargetEntity) importer.importObject();
		
		for(Ace ace : imported.getAcl().getAces()){
			securityTarget.getAcl().addAce(ace);
			securityService.persist(ace);
		}
		
		securityService.merge(securityTarget);
		
		/* register as external reference */
		importSupervisor.registerExternalReference(itemConfig.getId(), securityTarget);
	}
}
