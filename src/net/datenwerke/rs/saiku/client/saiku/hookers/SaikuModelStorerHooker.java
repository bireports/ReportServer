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
 
 
package net.datenwerke.rs.saiku.client.saiku.hookers;

import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportexecutor.hooks.PrepareReportModelForStorageOrExecutionHook;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.saiku.client.saiku.dto.SaikuReportDto;

public class SaikuModelStorerHooker implements PrepareReportModelForStorageOrExecutionHook {

	@Override
	public boolean consumes(ReportDto report) {
		return report instanceof SaikuReportDto || ((report instanceof TableReportDto) && ((TableReportDto)report).isCube());
	}

	@Override
	public void prepareForExecutionOrStorage(ReportDto report, String executeToken) {
		String json = getModel("rs-saiku-" + executeToken);
		
		if(report instanceof SaikuReportDto)
			((SaikuReportDto)report).setQueryXml(json);
		else 
			((TableReportDto)report).setCubeXml(json);
	}
	
	protected native String getModel(String name) /*-{
	    // get document
	    var f = $doc.getElementById(name);
	    
	    if (null === f)
	    	return "";
	    
	    d = (f.contentWindow || f.contentDocument);
	    var json = d.RsSaikuWorkspace.query.model;
	    return JSON.stringify(json);
	}-*/; 

}
