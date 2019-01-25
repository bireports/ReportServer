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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.object;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
		dtoPackage="net.datenwerke.rs.base.client.reportengines.jasper.dto"
	)
public class CompiledCSVJasperReport extends CompiledRSJasperReport {
	
	private static final long serialVersionUID = 3887891839353567053L;
	
	
	@ExposeToClient
	private String report;
	
	public String getReport() {
		return this.report;
	}

	public void setReport(Object report) {
		if(! (report instanceof String))
			throw new IllegalArgumentException("An CSV Jasper Report should be of type String."); //$NON-NLS-1$
		
		this.report = (String) report;
	}
	
	public String getFileExtension() {
		return "csv"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "text/csv"; //$NON-NLS-1$
	}
	
	@Override
	public boolean isStringReport() {
		return true;
	}

}
