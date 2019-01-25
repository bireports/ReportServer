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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.config;

public class RECFirstPage implements RECPaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5880814866716333531L;

	private int pageSize = 100;

	public RECFirstPage(){
		// dummy
	}
	
	public RECFirstPage(int pageSize){
		this.pageSize = pageSize;
	}
	
	@Override
	public int getFirstPage() {
		return 1;
	}

	@Override
	public int getLastPage() {
		return 1;
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public int hashCode() {
		return pageSize;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(null == obj || ! (obj instanceof RECFirstPage))
			return false;
		
		return pageSize == ((RECFirstPage)obj).pageSize;
	}
}
