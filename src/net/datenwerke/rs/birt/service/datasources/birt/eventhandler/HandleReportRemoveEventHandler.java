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
 
 
package net.datenwerke.rs.birt.service.datasources.birt.eventhandler;

import java.util.Collection;

import net.datenwerke.rs.birt.service.datasources.BirtDatasourceService;
import net.datenwerke.rs.birt.service.datasources.birtreport.entities.BirtReportDatasourceConfig;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;

public class HandleReportRemoveEventHandler implements
		EventHandler<RemoveEntityEvent> {

	private BirtDatasourceService datasourceService;

	@Inject
	public HandleReportRemoveEventHandler(BirtDatasourceService datasourceService) {
		this.datasourceService = datasourceService;
		
	}

	@Override
	public void handle(RemoveEntityEvent event) {
		Report node = (Report) event.getObject();
		if(! (node instanceof BirtReport))
			return;
		
		Collection<BirtReportDatasourceConfig> datasourceConfigs = datasourceService.getDatasourceConfigsWith(node);
		
		for(BirtReportDatasourceConfig config : datasourceConfigs){
			config.setReport(null);
			datasourceService.merge(config);
		}
	}

}
