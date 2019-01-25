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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.eventhandler;

import java.util.List;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;

import com.google.inject.Inject;

public class HandleReportForceRemoveEvent implements EventHandler<ForceRemoveEntityEvent> {


	private final TsDiskService diskService;
	
	@Inject
	public HandleReportForceRemoveEvent(TsDiskService diskService) {
		this.diskService = diskService;
	}

	@Override
	public void handle(ForceRemoveEntityEvent event) {
		Report report = (Report) event.getObject();
		
		List<TsDiskReportReference> references = diskService.getReferencesTo(report);
		if(null != references && ! references.isEmpty()){
			for(TsDiskReportReference ref : references){
				ref.setReport(null);
				diskService.merge(ref);
			}
		}
	}


}
