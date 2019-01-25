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
 
 
package net.datenwerke.rs.base.service.parameterreplacements.provider;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;

public class ReportForJuel {
	private String name = "";
	private String description ="";
	private String key = "";
	private Long id = -1l;
	private boolean isVariant = false;
	
	public ReportForJuel(Report report) {
		if(null != report){
			if(null != report.getName())
				this.name = report.getName();
			if(null != report.getDescription())
				this.description = report.getDescription();
			if(null != report.getKey())
				this.key = report.getKey();
			this.id = null == report.getId() ? report.getOldTransientId() : report.getId();
			this.isVariant = report instanceof ReportVariant;
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getKey() {
		return key;
	}
	
	public Long getId(){
		return id;
	}
	
	public Boolean isVariant(){
		return isVariant;
	}
}
