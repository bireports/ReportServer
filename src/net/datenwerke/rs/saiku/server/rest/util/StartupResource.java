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
 
 
package net.datenwerke.rs.saiku.server.rest.util;

import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.servlet.WebComponent;

public class StartupResource {
	
	private static final Logger log = LoggerFactory.getLogger(StartupResource.class);
	
	public void init() {
		//com.sun.jersey.spi.container.servlet.WebComponent
		try {
			java.util.logging.Logger jerseyLogger = java.util.logging.Logger.getLogger(WebComponent.class.getName());
			if (jerseyLogger != null) {
				jerseyLogger.setLevel(Level.SEVERE);
				log.debug("Disabled INFO Logging for com.sun.jersey.spi.container.servlet.WebComponent");
			} else {
				
			}
		} catch (Exception e) {
			log.error("Trying to disabling logging for com.sun.jersey.spi.container.servlet.WebComponent INFO Output failed", e);
		}
	}

}
