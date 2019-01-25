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
 
 
package net.datenwerke.rs.client;

import net.datenwerke.gxtdto.client.dialog.error.DetailErrorDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ReportServer implements EntryPoint {

	private final RSGinjector ginjector = GWT.create(RSGinjector.class);
	
	public void onModuleLoad() {
		/* ext exception swallow problem */
		/* http://code.google.com/p/gwt-ext/issues/detail?id=424 */
		if (!GWT.isScript()) {
		      GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void onUncaughtException(Throwable e) {
					new DetailErrorDialog(e); 
				}
			});
		}
		
		ginjector.getDispatcherService().dispatch();
	}
}
