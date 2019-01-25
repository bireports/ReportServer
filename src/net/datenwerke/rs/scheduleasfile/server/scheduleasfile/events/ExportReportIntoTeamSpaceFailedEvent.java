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
 
 
package net.datenwerke.rs.scheduleasfile.server.scheduleasfile.events;

import java.util.List;

import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.utils.eventbus.Event;

public class ExportReportIntoTeamSpaceFailedEvent implements Event {

	private ReportDto reportDto;
	private String executorToken;
	private String format;
	private List<ReportExecutionConfigDto> configs;
	private AbstractTsDiskNodeDto folder;
	private String name;
	private String description;
	
	public ExportReportIntoTeamSpaceFailedEvent(){}
	
	public ExportReportIntoTeamSpaceFailedEvent(ReportDto reportDto,
			String executorToken, String format,
			List<ReportExecutionConfigDto> configs, AbstractTsDiskNodeDto folder,
			String name, String description) {
		this.reportDto = reportDto;
		this.executorToken = executorToken;
		this.format = format;
		this.configs = configs;
		this.folder = folder;
		this.name = name;
		this.description = description;
	}
	
	public ReportDto getReportDto() {
		return reportDto;
	}
	public String getExecutorToken() {
		return executorToken;
	}
	public String getFormat() {
		return format;
	}
	public List<ReportExecutionConfigDto> getConfigs() {
		return configs;
	}
	public AbstractTsDiskNodeDto getFolder() {
		return folder;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	
}
