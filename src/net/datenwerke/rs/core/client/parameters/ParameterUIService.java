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
 
 
package net.datenwerke.rs.core.client.parameters;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.datenwerke.rs.core.client.parameters.config.ParameterConfigurator;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;

public interface ParameterUIService {

	public List<ParameterConfigurator> getAvailableParameterConfigurators();
	
	public ParameterConfigurator getConfigurator(ParameterDefinitionDto pd);

	public String getMandatoryPrefix();

	public String getMandatorySuffix();

	public String getOptionalPrefix();

	public String getOptionalSuffix();

	public ParameterInstanceDto getParameterInstanceFor(Set<ParameterInstanceDto> instances, ParameterDefinitionDto definition);

	public Collection<ParameterInstanceDto> getRelevantInstancesFor(List<ParameterDefinitionDto> defs, Set<ParameterInstanceDto> instances, ParameterDefinitionDto definition);

	public List<ParameterDefinitionDto> getAllParameterDependents(List<ParameterDefinitionDto> defs, ParameterDefinitionDto paramDefinition);

	public ParameterDefinitionDto getParameterDefinitionByKey(List<ParameterDefinitionDto> defs, String name);

}
