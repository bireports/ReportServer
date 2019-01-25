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
 
 
package net.datenwerke.rs.base.service.dbhelper.querybuilder.queryconditions;

import java.util.LinkedList;
import java.util.List;

import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;

public abstract class BooleanFunctionQueryCondition implements QryCondition {

	private QryCondition first;
	private QryCondition second;
	
	public BooleanFunctionQueryCondition(QryCondition first, QryCondition second) {
		this.first = first;
		this.second = second;
	}

	public abstract String getBooleanFunctionName();
	
	@Override
	public void appendToBuffer(StringBuffer buf, ColumnNamingService columnNamingService) {
		buf.append("((");
		first.appendToBuffer(buf, columnNamingService);
		buf.append(") ");
		buf.append(getBooleanFunctionName());
		buf.append(" (");
		second.appendToBuffer(buf, columnNamingService);
		buf.append("))");
	}
	
	@Override
	public List<Column> getContainedColumns() {
		LinkedList<Column> res = new LinkedList<Column>();
		res.addAll(first.getContainedColumns());
		res.addAll(second.getContainedColumns());
		return res;
	}

}
