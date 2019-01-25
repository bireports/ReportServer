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
 
 
package net.datenwerke.rs.incubator.service.filterreplacements.today;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.hooks.FilterReplacementProviderHook;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ManagedQuery;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.rs.utils.juel.wrapper.TodayWrapper;

public class TodayFilterReplacementProviderHooker implements
		FilterReplacementProviderHook {

	private static final String TODAY = "today";

	@Override
	public void enhance(SimpleJuel juel, Column col, QueryBuilder queryBuilder, ManagedQuery query) {
		if(null == col.getType())
			return;
		Class c = DatabaseHelper.mapSQLTypeToJava(col.getType());
		
		String format = "yyyyMMdd";
		if(c.equals(java.sql.Date.class))
			format = "yyyy-MM-dd";
		else if(c.equals(java.sql.Time.class))
			format="hh:mm:ss";
		else if(c.equals(java.sql.Timestamp.class))
			format = "yyyy-MM-dd hh:mm:ss";
		
		juel.addReplacement(TODAY, new TodayWrapper(format));
	}

}
