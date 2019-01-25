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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto.decorator;

import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnFilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnReferenceDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterSpecDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;

/**
 * Dto Decorator for {@link ColumnFilterDto}
 *
 */
public class ColumnFilterDtoDec extends ColumnFilterDto {


	private static final long serialVersionUID = 1L;

	public ColumnFilterDtoDec() {
		super();
	}

	@Override
	public String toDisplayTitle() {
		return "Filter: " + (null != getColumn() ? getColumn().getName() : "");
	}

	@Override
	public boolean isStillValid(TableReportDto report) {
		ColumnDto a = getColumn();

		if(null == a)
			return false;
		
		if(! (a instanceof ColumnReferenceDto))
			return true;
		
		for(ColumnDto col : report.getAdditionalColumns())
			if(a.getName().equals(col.getName()))
				return true;
		
		return false;
	}
	
	@Override
	public FilterSpecDto cloneFilter() {
		ColumnFilterDtoDec clone = new ColumnFilterDtoDec();

		if(null != getColumn())
			clone.setColumn(((ColumnDtoDec)getColumn()).cloneColumnForSelection());
		
		return clone;
	}
}
