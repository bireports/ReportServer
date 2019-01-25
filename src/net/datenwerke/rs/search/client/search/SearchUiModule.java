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
 
 
package net.datenwerke.rs.search.client.search;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class SearchUiModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SearchUiService.class).to(SearchUiServiceImpl.class).in(Singleton.class);
		
		bind(SearchUiStartup.class).asEagerSingleton();
	}

}
