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
 
 
package net.datenwerke.rs.base.service.dbhelper.hookers;

import java.util.regex.Pattern;

import net.datenwerke.rs.base.service.dbhelper.hooks.InnerQueryModificationHook;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;

import org.apache.commons.lang.StringUtils;

public class InnerQueryColumnReplacementHooker implements InnerQueryModificationHook {
	

	@Override
	public String modifyQuery(String currentQuery, QueryBuilder queryBuilder) {
		
		if(currentQuery.contains("/*<rs:cols>*/") && null != queryBuilder.getColumns() && !queryBuilder.getColumns().isEmpty()){
			String colstr = StringUtils.join(queryBuilder.getColumns(), ", ");
			
			Pattern pattern = Pattern.compile("/\\*<rs:cols>\\*/.*/\\*</rs:cols>\\*/", Pattern.DOTALL | Pattern.MULTILINE);
			return pattern.matcher(currentQuery).replaceAll(colstr);
		}
		
		return currentQuery;
	}

}
