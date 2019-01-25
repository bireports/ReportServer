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
 
 
package net.datenwerke.rs.incubator.client.outputformatauth.hookers;

import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter;
import net.datenwerke.rs.core.client.reportexporter.hooks.VetoReportExporterHook;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportPropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportStringPropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.decorator.ReportDtoDec;
import net.datenwerke.rs.incubator.client.outputformatauth.OutputFormatAuthUiModule;

public class ExportOptionVetoer implements VetoReportExporterHook {

	@Override
	public boolean doesVetoExporter(ReportExporter exporter, ReportDto report) {
		ReportDtoDec rep = (ReportDtoDec) report;
		
		ReportPropertyDto property = null;
		if(report instanceof ReportVariantDto)
			property = rep.getParentReportPropertyByName(OutputFormatAuthUiModule.PROPERTY_OUTPUT_FORMAT_AUTH);
		else
			property = rep.getReportPropertyByName(OutputFormatAuthUiModule.PROPERTY_OUTPUT_FORMAT_AUTH);
		
		if(null != property && property instanceof ReportStringPropertyDto){
			String allowedFormatsString = ((ReportStringPropertyDto) property).getStrValue();
			String[] formats = allowedFormatsString.split(",");
			
			boolean found = false;
			for(String f : formats){
				if(f.trim().toUpperCase().equals(exporter.getOutputFormat().toUpperCase())){
					found = true;
					break;
				}
			}
			return !found;
		}
		
		return false;
	}

}
