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
 
 
package net.datenwerke.rs.core.server.reportexport.events;

import java.util.List;

import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.utils.eventbus.Event;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

public class ExportReportViaMailFailedEvent implements Event {

	
	private ReportDto reportDto;
	private String executorToken; 
	private String format;
	private List<ReportExecutionConfigDto> configs; 
	private String subject;
	private String message ;
	private List<StrippedDownUser> recipients;
	
	public ExportReportViaMailFailedEvent(){
		
	}

	public ExportReportViaMailFailedEvent(ReportDto reportDto,
			String executorToken, String format,
			List<ReportExecutionConfigDto> configs, String subject,
			String message, List<StrippedDownUser> recipients) {
		super();
		this.reportDto = reportDto;
		this.executorToken = executorToken;
		this.format = format;
		this.configs = configs;
		this.subject = subject;
		this.message = message;
		this.recipients = recipients;
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

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public List<StrippedDownUser> getRecipients() {
		return recipients;
	}
	
	

}
