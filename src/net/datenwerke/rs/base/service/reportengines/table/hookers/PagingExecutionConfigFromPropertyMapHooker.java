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
 
 
package net.datenwerke.rs.base.service.reportengines.table.hookers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.datenwerke.rs.core.service.reportmanager.engine.config.RECSinglePage;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.hooks.ReportExecutionConfigFromPropertyMapHook;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public class PagingExecutionConfigFromPropertyMapHooker implements
ReportExecutionConfigFromPropertyMapHook {

	@Override
	public Collection<ReportExecutionConfig> parse(Report report,
			HttpServletRequest request, Map<String, String[]> properties) {
		HashSet<ReportExecutionConfig> configs = new HashSet<ReportExecutionConfig>();                           
		try{
			if(properties.containsKey("page")){
				int page = Integer.parseInt(properties.get("page")[0]);
				RECSinglePage pageconfig = new RECSinglePage(page);

				if(properties.containsKey("pagesize")){
					int size = Integer.parseInt(properties.get("pagesize")[0]);
					pageconfig.setPageSize(size);
				}
				configs.add(pageconfig);
			}
		} catch(RuntimeException e){}
		return configs;  
	}

}
