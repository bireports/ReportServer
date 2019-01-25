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
 
 
package net.datenwerke.gxtdto.client.objectinformation.hooks;

import java.util.Date;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;

import com.google.gwt.resources.client.ImageResource;

public interface ObjectInfoKeyInfoProvider extends Hook {

	public boolean consumes(Object object);
	
	String getName(Object object);
	String getDescription(Object object);
	String getType(Object object);
	Date getCreatedOn(Object object);
	Date getLastUpdatedOn(Object object);

	public ImageResource getIconSmall(Object object);
	
	
	
}
