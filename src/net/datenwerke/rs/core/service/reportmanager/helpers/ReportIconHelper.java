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
 
 
package net.datenwerke.rs.core.service.reportmanager.helpers;

import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;

public class ReportIconHelper {

	public String getSmallIconForOutputFormat(String format){
		if(ReportExecutorService.OUTPUT_FORMAT_CSV.equals(format))
			return "csv";
		if(ReportExecutorService.OUTPUT_FORMAT_EXCEL.equals(format))
			return "xls";
		if(ReportExecutorService.OUTPUT_FORMAT_HTML.equals(format))
			return "html";
		if(ReportExecutorService.OUTPUT_FORMAT_PDF.equals(format))
			return "pdf";
		if(ReportExecutorService.OUTPUT_FORMAT_PNG.equals(format))
			return "png";
		if(ReportExecutorService.OUTPUT_FORMAT_RTF.equals(format))
			return "doc";
		
		return "file";
	}

}
