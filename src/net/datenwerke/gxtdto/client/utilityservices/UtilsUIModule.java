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
 
 
package net.datenwerke.gxtdto.client.utilityservices;

import net.datenwerke.gxtdto.client.utilityservices.grid.GridHelperService;
import net.datenwerke.gxtdto.client.utilityservices.grid.GridHelperServiceImpl;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerService;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerServiceImpl;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarServiceImpl;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class UtilsUIModule extends AbstractGinModule {

	@Override
	protected void configure() {
		/* bind service */
		bind(UtilsUIService.class).to(UtilsUIServiceImpl.class).in(Singleton.class);

		bind(GridHelperService.class).to(GridHelperServiceImpl.class).in(Singleton.class);
		bind(ToolbarService.class).to(ToolbarServiceImpl.class).in(Singleton.class);
		bind(SubmitTrackerService.class).to(SubmitTrackerServiceImpl.class).in(Singleton.class);
	}

}
