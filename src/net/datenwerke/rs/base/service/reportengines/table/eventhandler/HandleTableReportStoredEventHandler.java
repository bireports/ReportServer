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

import net.datenwerke.rs.base.service.reportengines.table.TableReportUtilsImpl;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.JpaEvent;
import net.datenwerke.security.service.eventlogger.jpa.MergeEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.PersistEntityEvent;

import com.google.inject.Inject;

public class HandleTableReportStoredEventHandler implements EventHandler<JpaEvent> {

	private final TableReportUtilsImpl service;
	
	@Inject
	public HandleTableReportStoredEventHandler(TableReportUtilsImpl service) {
		this.service = service;
	}

	@Override
	public void handle(JpaEvent event) {
		if(! (event instanceof PersistEntityEvent) && !(event instanceof MergeEntityEvent))
			return;
		
		Object obj = event.getObject();
		if(! (obj instanceof TableReport))
			return;
		
		TableReport report = (TableReport) obj;
		
		/* persist additional columns */
		if(null != report.getAdditionalColumns())
			for(AdditionalColumnSpec spec : report.getAdditionalColumns())
				if(null == spec.getId())
					service.persist(spec);
		
		/* persist columns */
		if(null != report.getAdditionalColumns())
			for(Column col : report.getColumns())
				if(null == col.getId())
					service.persist(col);
	}



}
