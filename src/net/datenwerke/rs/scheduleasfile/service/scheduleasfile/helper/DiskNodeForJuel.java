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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.helper;

import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;

public class DiskNodeForJuel {

	private String name = "";
	private String description = "";
	private Long id = -1l;
	
	public DiskNodeForJuel(AbstractTsDiskNode space){
		if(null != space){
			if(null != space.getName())
				name = space.getName();
			if(null != space.getDescription())
				description = space.getDescription();
			if(null != space.getId())
				id = space.getId();
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}
	
	
}
