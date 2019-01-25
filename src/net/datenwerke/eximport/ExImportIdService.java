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

import net.datenwerke.eximport.hooks.ExImportIdProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import org.hibernate.proxy.HibernateProxy;

import com.google.inject.Inject;

/**
 * Helper service to provide IDs during the export.
 * 
 *
 */
public class ExImportIdService {

	private final HookHandlerService hookHandler;
	
	@Inject
	public ExImportIdService(
		HookHandlerService hookHandler	
		){
		
		/* store objects */
		this.hookHandler = hookHandler;
	}
	
	/**
	 * Loops over all registered {@link ExImportIdProviderHook} Hookees to
	 * provide an id for the given object.
	 * 
	 * @param object
	 * @return the id
	 */
	public String provideId(Object object){
		for(ExImportIdProviderHook provider : hookHandler.getHookers(ExImportIdProviderHook.class)){
			if(object instanceof HibernateProxy)
				object = ((HibernateProxy)object).getHibernateLazyInitializer().getImplementation();
			
			String id = provider.provideIdFor(object);
			if(null != id)
				return id;
		}
		return null;
	}
}
