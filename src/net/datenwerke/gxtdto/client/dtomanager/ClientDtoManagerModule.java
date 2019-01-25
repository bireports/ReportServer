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
 
 
package net.datenwerke.gxtdto.client.dtomanager;

import net.datenwerke.gxtdto.client.dtomanager.stores.DtoAwareListStore;
import net.datenwerke.gxtdto.client.dtomanager.stores.DtoAwareTreeStore;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * 
 *
 */
public class ClientDtoManagerModule extends AbstractGinModule {

	@Override
	protected void configure() {
		/* bind utils */
		bind(ProxyIdGenerator.class).to(ProxyIdGeneratorImpl.class).in(Singleton.class);
		
		/* bind service */
		bind(ClientDtoManagerService.class).to(ClientDtoManagerServiceImpl.class).in(Singleton.class);
		
		/* static injection */
		requestStaticInjection(
			Dao.class,
			
			DtoAwareTreeStore.class,
			DtoAwareListStore.class
			
			
		);
	}

}
