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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.hookers;

import javax.inject.Inject;

import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterDao;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterUIService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.dashboard.client.dashboard.hookers.ReportDadgetDefaultExportHooker;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.ScriptReportDto;

public class ScriptReportDadgetExporter extends ReportDadgetDefaultExportHooker {

	@Inject
	public ScriptReportDadgetExporter(ReportExecutorUIService reportExecutorService,
			ReportExporterUIService reportExportService,
			ReportExecutorDao reportExecutorDao,
			ReportExporterDao reportExporterDao) {
		super(reportExecutorService, reportExportService, reportExecutorDao, reportExporterDao);
	}

	@Override
	protected boolean isSupportedReport(ReportDto report) {
		return report instanceof ScriptReportDto;
	}

	@Override
	public String getPropertyName() {
		return "scriptConfig";
	}
}
