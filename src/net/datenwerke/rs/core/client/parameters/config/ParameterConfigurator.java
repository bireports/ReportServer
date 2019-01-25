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
 
 
package net.datenwerke.rs.core.client.parameters.config;

import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterProposalDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;

/**
 * Used to configure parameters in front end.
 * 
 * 
 */
public interface ParameterConfigurator<D extends ParameterDefinitionDto, I extends ParameterInstanceDto> {

	public enum ParameterType {
		Normal,
		Separator,
		Container
	}
	
	public ParameterType getType();
	
	public ParameterDefinitionDto getNewDto(ReportDto report);

	public String getName();

	public Widget getEditComponentForDefinition(D definition);
	
	public void updateDefinitionOnSubmit(D definition, Widget component);
	
	public Widget getEditComponentForInstance(I instance, D definition, Collection<ParameterInstanceDto> relevantInstances, boolean initial, int labelWidth, String executeReportToken);

	public ImageResource getIcon();

	public boolean canHandle(ParameterProposalDto proposal);

	public ParameterDefinitionDto getNewDto(ParameterProposalDto proposal, ReportDto report);

	public void dependeeInstanceChanged(I instance,
			D aDefinition,
			Collection<ParameterInstanceDto> relevantInstances);

	public boolean consumes(Class<? extends ParameterDefinitionDto> type);

	boolean canDependOnParameters();

	public List<String> validateParameter(D definition,
			I instance, Widget widget);

	public boolean isAvailable();

}
