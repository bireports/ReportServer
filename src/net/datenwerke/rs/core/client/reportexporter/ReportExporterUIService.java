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
 
 
package net.datenwerke.rs.core.client.reportexporter;

import java.util.List;

import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

public interface ReportExporterUIService {

	public List<ReportExporter> getAvailableExporters(ReportDto report);
	
	/**
	 * This method should generally be preferred over {@link #getAvailableExporters(ReportDto)}. It returns the exporters for a report which are allowed and in the right order.
	 * 
	 * @param report
	 * @return
	 */
	public List<ReportExporter> getCleanedUpAvailableExporters(ReportDto report);

	/**
	 * Returns all exporters that are able to export the report in practice
	 * @param report
	 * @return
	 */
	public String getExportServletPath();

	/**
	 * Returns all exporters that are able to export the report as it is configured at the moment
	 * @param report
	 * @return
	 */
	List<ReportExporter> getUsableExporters(ReportDto report);

}
