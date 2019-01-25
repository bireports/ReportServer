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
 
 
package net.datenwerke.rs.base.client.reportengines.table.execute;

import com.google.inject.Inject;

import net.datenwerke.gf.client.config.ClientConfigJSONService;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterDao;
import net.datenwerke.rs.core.client.reportexporter.exporter.generic.Export2CSV;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

/**
 * 
 *
 */
public class Table2CSV extends Export2CSV {

	@Inject
	public Table2CSV(ReportExporterDao exporterDao,ClientConfigJSONService jsonService) {
		super(exporterDao, jsonService);
	}

	public boolean consumes(ReportDto report) {
		return report instanceof TableReportDto && !((TableReportDto)report).isCubeFlag();
	}
}
