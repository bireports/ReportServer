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

import javax.persistence.EntityManager;

import net.datenwerke.eximport.ex.enclosed.EnclosedEntityExporter;
import net.datenwerke.eximport.hooks.ImporterPersistEnclosedEntityHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.jpa.EntityUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class EnclosedEntityImporter extends EnclosedObjectImporter {

	protected EntityUtils entityServices;
	protected Provider<EntityManager> entityManagerProvider;
	protected HookHandlerService hookHandler;
	
	@Inject
	public void setHookHandler(HookHandlerService hookHandler) {
		this.hookHandler = hookHandler;
	}
	
	@Inject
	public void setEntityServices(EntityUtils entityServices) {
		this.entityServices = entityServices;
	}
	
	@Inject
	public void setEntityManagerProvider(
			Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{EnclosedEntityExporter.class};
	}
	
	 @Override
	public boolean postProcess(String id, Object object, boolean enclosed) {
		 try{
			 boolean foundHook = false;
			 for(ImporterPersistEnclosedEntityHook persister : hookHandler.getHookers(ImporterPersistEnclosedEntityHook.class)){
				 if(persister.consumes(object)){
					 persister.persist(object);
					 foundHook = true;
					 break;
				 }
			 }
				 
			 if(! foundHook && entityServices.isEntity(object) && null == entityServices.getId(object))
				 entityManagerProvider.get().persist(object);

			 return true;
		 } catch(Exception e){
			 throw new IllegalArgumentException(e);
		 }
	}
}
