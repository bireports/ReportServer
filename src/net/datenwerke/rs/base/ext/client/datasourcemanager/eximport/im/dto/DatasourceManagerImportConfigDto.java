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
 
 
package net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.dto;

import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportConfigDto;

public class DatasourceManagerImportConfigDto extends TreeImportConfigDto<AbstractDatasourceManagerNodeDto> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1879599255965355795L;

	private DatasourceDefinitionDto defaultDatasource;
	
	public void setDefaultDatasource(DatasourceDefinitionDto defaultDatasource) {
		this.defaultDatasource = defaultDatasource;
	}
	
	public DatasourceDefinitionDto getDefaultDatasource() {
		return defaultDatasource;
	}
	
}
