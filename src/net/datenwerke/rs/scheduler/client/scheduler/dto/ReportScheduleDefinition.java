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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DateTriggerConfigDto;

/**
 * Simple transfer object to transfer the schedule information to the server.
 * 
 *
 */
public class ReportScheduleDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 529346772433967794L;

	/**
	 * make sure to install all types
	 */
	private ReportDto report;
	private String outputFormat;
	
	private List<ReportExecutionConfigDto> exportConfiguration;
	
	private List<Long> recipients;
	
	private List<AdditionalScheduleInformation> additionalInfos = new ArrayList<AdditionalScheduleInformation>();
	
	private DateTriggerConfigDto schedulerConfig;
	
	private Long jobId;
	
	private ArrayList<ReportScheduleDefinitionSendToConfig> sendToConfigs = new ArrayList<ReportScheduleDefinitionSendToConfig>();

	public ReportDto getReport() {
		return report;
	}
	
	public void setReport(ReportDto report) {
		this.report = report;
	}
	
	public void setExportConfiguration(
			List<ReportExecutionConfigDto> exportConfiguration) {
		this.exportConfiguration = exportConfiguration;
	}

	public List<ReportExecutionConfigDto> getExportConfiguration() {
		return exportConfiguration;
	}

	public void setSchedulerConfig(DateTriggerConfigDto config) {
		this.schedulerConfig = config;
	}

	public DateTriggerConfigDto getSchedulerConfig() {
		return schedulerConfig;
	}

	public void setAdditionalInfos(List<AdditionalScheduleInformation> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

	public void addAdditionalInfo(AdditionalScheduleInformation info){
		this.additionalInfos.add(info);
	}
	
	public List<AdditionalScheduleInformation> getAdditionalInfos() {
		return additionalInfos;
	}
	
	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	
	public void setRecipients(List<Long> recipients) {
		this.recipients = recipients;
	}

	public List<Long> getRecipients() {
		return recipients;
	}
	
	public ArrayList<ReportScheduleDefinitionSendToConfig> getSendToConfigs() {
		return sendToConfigs;
	}
	
	public void setSendToConfigs(
			ArrayList<ReportScheduleDefinitionSendToConfig> sendToConfigs) {
		this.sendToConfigs = sendToConfigs;
	}
	
	public void addSendToConfigs(ReportScheduleDefinitionSendToConfig sendToConfig) {
		if(null == sendToConfigs)
			this.sendToConfigs = new ArrayList<ReportScheduleDefinitionSendToConfig>();
		sendToConfigs.add(sendToConfig);
	}
	
	public ReportScheduleDefinitionSendToConfig getSendToConfig(String id) {
		if(null == sendToConfigs)
			return null;
		
		for(ReportScheduleDefinitionSendToConfig config : sendToConfigs)
			if(id.equals(config.getId()))
				return config;
		
		return null;
	}
	

	public <A> A getAdditionalInfo(Class<A> type) {
		if(null == type || null == additionalInfos)
			return null;

		for(AdditionalScheduleInformation info : getAdditionalInfos())
			if(type.equals(info.getClass()))
				return (A) info;
		
		return null;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getJobId() {
		return jobId;
	}

	RsDto whitelist_1;

	
}
