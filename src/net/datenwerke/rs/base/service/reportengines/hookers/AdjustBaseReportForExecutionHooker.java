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
 
 
package net.datenwerke.rs.base.service.reportengines.hookers;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.NonFatalException;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableReportColumnMetadataService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.hooks.AdjustReportForExecutionHook;

import com.google.inject.Inject;

public class AdjustBaseReportForExecutionHooker implements AdjustReportForExecutionHook {

	private final TableReportColumnMetadataService columnMetadataService;
	
	@Inject
	public AdjustBaseReportForExecutionHooker(
		TableReportColumnMetadataService columnMetadataService
		){
		
		/* store objects */
		this.columnMetadataService = columnMetadataService;
	}
	
	@Override
	public void adjust(Report report) {
		if(report instanceof TableReport){
			try {
				columnMetadataService.augmentWithMetadata((TableReport)report);
			} catch (NonFatalException e) {
			}
		}
	}
	
	@Override
	public void adjust(ReportDto tmpReport) {
		
	}

}
