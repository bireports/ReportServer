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
 
 
package net.datenwerke.rs.core.client.reportexporter.exporter;

import java.util.List;

import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.http.client.Request;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ReportExporter {

	public interface ConfigurationFinishedCallback{
		void success();
	}
	
	public boolean consumesConfiguration(ReportDto report);
	
	public boolean consumes(ReportDto report);
	
	public Request export(ReportDto report, String executorToken);
	
	public void displayConfiguration(ReportDto report, String executorToken, boolean allowAutomaticConfig, ConfigurationFinishedCallback callack);
	
	public boolean isConfigured();
	
	public List<ReportExecutionConfigDto> getConfiguration();
	
	public String getExportTitle();
	
	public String getExportDescription();
	
	public ImageResource getIcon();
	
	public String getOutputFormat();

	public boolean hasConfiguration();

	public void configureFrom(List<ReportExecutionConfigDto> exportConfiguration);

	public boolean wantsToBeTop(ReportDto report);

	public Request prepareExportForPreview(ReportDto report,String executorToken, AsyncCallback<Void> callback);
}
