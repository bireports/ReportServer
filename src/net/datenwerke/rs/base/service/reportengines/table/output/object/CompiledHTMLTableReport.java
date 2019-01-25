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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.client.reportexecutor.dto.CompiledHtmlReportDto;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledHtmlReport;

/**
 * 
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	generateDto2Poso=false,
	dtoImplementInterfaces=CompiledHtmlReportDto.class
)
public class CompiledHTMLTableReport extends CompiledTableReport implements CompiledHtmlReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5784381788512286605L;
	
	@ExposeToClient
	private final String report;
	
	public CompiledHTMLTableReport(String htmlReport) {
		this.report = htmlReport;
	}
	
	public String getFileExtension() {
		return "html"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "text/html"; //$NON-NLS-1$
	}

	public String getReport() {
		return report;
	}

}
