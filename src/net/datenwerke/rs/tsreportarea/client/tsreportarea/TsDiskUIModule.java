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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * 
 *
 */
public class TsDiskUIModule extends AbstractGinModule {

	public static final String TEAMSPACE_OPEN_ITEM_HISTORY_TOKEN = "tsexec";
	public static final String TEAMSPACE_SELECT_ITEM_HISTORY_TOKEN = "tsselect";
	
	public static final String USER_PROPERTY_VIEW_VERTICAL_SPLIT = "favoriteService:view:split";
	public static final String USER_PROPERTY_VIEW_VIEW_ID = "favoriteService:view:id";
	
	@Override
	protected void configure() {
		/* service */
		bind(TsDiskUIService.class).to(TsDiskUIServiceImpl.class).in(Singleton.class);
		
		/* startup */
		bind(TsDiskUIStartup.class).asEagerSingleton();
	}

}
