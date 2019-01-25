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

import net.datenwerke.rs.base.service.reportengines.table.TableReportUtils;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.ColumnReference;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;

public class RemoveAdditionalColumnSpecEventHandler implements EventHandler<RemoveEntityEvent> {

	private final TableReportUtils utils;
	
	@Inject
	public RemoveAdditionalColumnSpecEventHandler(TableReportUtils utils) {
		this.utils = utils;
	}

	@Override
	public void handle(RemoveEntityEvent event) {
		AdditionalColumnSpec spec = (AdditionalColumnSpec) event.getObject();
		
		for(ColumnReference ref : utils.getColumnReferencesFor(spec)){
			ref.setReference(null);
			utils.merge(ref);
		}
	}

}
