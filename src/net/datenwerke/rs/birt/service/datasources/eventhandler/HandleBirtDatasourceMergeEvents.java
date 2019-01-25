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
 
 
package net.datenwerke.rs.birt.service.datasources.eventhandler;

import com.google.inject.Inject;

import net.datenwerke.rs.birt.service.datasources.birtreport.entities.BirtReportDatasourceDefinition;
import net.datenwerke.rs.resultcache.ResultCacheKeyDatasource;
import net.datenwerke.rs.resultcache.ResultCacheService;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.MergeEntityEvent;

public class HandleBirtDatasourceMergeEvents implements EventHandler<MergeEntityEvent> {

	private ResultCacheService resultCacheService;

	@Inject
	public HandleBirtDatasourceMergeEvents(ResultCacheService resultCacheService) {
		this.resultCacheService = resultCacheService;
	}

	@Override
	public void handle(MergeEntityEvent event) {
		BirtReportDatasourceDefinition ds = (BirtReportDatasourceDefinition) event.getObject();
		resultCacheService.removeFromResultCache(new ResultCacheKeyDatasource(ds));
	}

}
