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
 
 
package net.datenwerke.rs.base.service.reportengines.table.eventhandler;

import java.util.Iterator;
import java.util.List;

import net.datenwerke.rs.base.service.reportengines.table.TableReportUtils;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.rs.utils.exception.exceptions.NeedForcefulDeleteException;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;

public class HandleDatasourceRemoveEventHandler implements
		EventHandler<RemoveEntityEvent> {

	private final TableReportUtils tableReportUtils;
	
	@Inject
	public HandleDatasourceRemoveEventHandler(TableReportUtils tableReportUtils) {
		/* store obejcts */
		this.tableReportUtils = tableReportUtils;
	}

	@Override
	public void handle(RemoveEntityEvent event) {
		DatasourceDefinition ds = (DatasourceDefinition) event.getObject();
		
		List<TableReport> reports = tableReportUtils.getReportsWithMetadataDatasource(ds);
		if(null != reports && ! reports.isEmpty()){
			Iterator<TableReport> it = reports.iterator();
			StringBuilder error = new StringBuilder("Datasource " + ds.getId() + " is used in table reports. Report Ids: " + it.next().getId());
			while(it.hasNext())
				error.append(", ").append(it.next().getId());
			
			throw new NeedForcefulDeleteException(error.toString());
		}
	}

}
