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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.supervisor;

import java.util.Collection;

import net.datenwerke.rs.base.service.reportengines.table.TableReportUtils;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.PreFilter;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.supervisor.Dto2ReportSupervisor;

import com.google.inject.Inject;

public class Dto2TableReportSupervisor extends Dto2ReportSupervisor {

	private final TableReportUtils trus;

	@Inject
	public Dto2TableReportSupervisor(
			TableReportUtils trus
		){
		this.trus = trus;
	}
	
	@Override
	public void enclosedObjectsRemovedFromCollection(ReportDto dto,
			Report poso, Collection<?> objectCollection, String fieldname) {
		super.enclosedObjectsRemovedFromCollection(dto, poso, objectCollection,
				fieldname);
		
		if(null == objectCollection)
			return;
		
		for(Object o : objectCollection){
			if(o instanceof AdditionalColumnSpec)
				trus.remove((AdditionalColumnSpec)o);
			else if(o instanceof Column)
				trus.remove((Column)o);
			if(o instanceof PreFilter)
				trus.remove((PreFilter)o);
		}
	}
}
