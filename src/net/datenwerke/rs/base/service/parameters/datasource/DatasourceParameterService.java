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
 
 
package net.datenwerke.rs.base.service.parameters.datasource;

import java.util.List;

import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;

public interface DatasourceParameterService {

	public List<DatasourceParameterData> getParameterData(
			DatasourceParameterDefinition parameter) throws ReportExecutorException;
	
	List<DatasourceParameterData> getParameterData(DatasourceParameterDefinition parameter,
			ParameterSet parameterSet) throws ReportExecutorException;

	List<DatasourceParameterDefinition> getParametersWithDatasource(
			DatasourceDefinition ds);

	public boolean isAllowDatasourceParameterPostProcessing();

}
