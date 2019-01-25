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
 
 
package net.datenwerke.rs.core.service.jarextension.events;

import net.datenwerke.security.service.eventlogger.DwLoggedEvent;

public class ReoportServerExtenderLoadFailedEvent extends DwLoggedEvent {

	public ReoportServerExtenderLoadFailedEvent(String extender, String error){
		super("extender", extender, "error", error);
	}
	
	public ReoportServerExtenderLoadFailedEvent(String error) {
		super("error", error);
	}

	@Override
	public String getLoggedAction() {
		return "EXTENDER_STARTUP_FAILED";
	}

}
