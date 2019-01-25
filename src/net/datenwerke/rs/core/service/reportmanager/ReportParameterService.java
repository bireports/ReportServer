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
 
 
package net.datenwerke.rs.core.service.reportmanager;

import java.util.List;
import java.util.Set;

import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public interface ReportParameterService {

	/**
	 * Returns all installed parameter definitions.
	 * 
	 * @return
	 */
	public Set<Class<? extends ParameterDefinition>> getInstalledParameters();

	/**
	 * Persists the parameter
	 * @param pd
	 */
	public void persist(ParameterDefinition pd);
	
	public void persist(ParameterInstance pi);

	public ParameterDefinition getParameterById(long id);
	public ParameterDefinition getParameterByKey(long report, String key);
	
	public List<ParameterDefinition> getParameterDependees(ParameterDefinition definition);
	
	public List<ParameterInstance> getInstancesForParameterDefinition(ParameterDefinition pd);

	public ParameterDefinition merge(ParameterDefinition parameter);
	
	public AbstractReportManagerNode remove(ParameterDefinition parameter);

	/**
	 * Creates an instance for the report and all possible variants
	 * @param report
	 * @param definition
	 */
	public void addParameterDefinition(Report report, ParameterDefinition definition);
	
	public ParameterDefinition getUnmanagedParameterById(long id);
	
	public Report getReportWithParameter(ParameterDefinition pd);


	/**
	 * Moves a parameter to the specified position
	 * 
	 * @param parameter
	 * @param to
	 * @return
	 */
	public AbstractReportManagerNode moveParameter(ParameterDefinition parameter, int to);

	public void remove(ParameterInstance instance);

	public ParameterDefinition getUnmanagedParameter(
			ParameterDefinition<?> definition);

	void updateParameterInstances(Report report, ParameterDefinition definition);

	Report getReportWithInstance(ParameterInstance instance);
}
