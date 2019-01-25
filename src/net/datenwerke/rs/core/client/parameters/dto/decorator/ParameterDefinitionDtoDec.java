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
 
 
package net.datenwerke.rs.core.client.parameters.dto.decorator;

import javax.persistence.Transient;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.interfaces.ReportAware;

/**
 * Dto Decorator for {@link ParameterDefinitionDto}
 *
 */
abstract public class ParameterDefinitionDtoDec extends ParameterDefinitionDto implements IdedDto, ReportAware {


	private static final long serialVersionUID = 1L;

	@Transient transient private ReportDto report;
	
	public ParameterDefinitionDtoDec() {
		super();
	}

	public ReportDto getReport() {
		return report;
	}
	
	public void setReport(ReportDto report) {
		this.report = report;
	}

}
