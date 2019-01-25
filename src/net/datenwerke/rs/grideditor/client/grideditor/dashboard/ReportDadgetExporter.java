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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dashboard;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterDao;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterUIService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.dashboard.client.dashboard.hookers.ReportDadgetDefaultExportHooker;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorReportDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;

public class ReportDadgetExporter extends ReportDadgetDefaultExportHooker {

	@Inject
	public ReportDadgetExporter(ReportExecutorUIService reportExecutorService,
			ReportExporterUIService reportExportService,
			ReportExecutorDao reportExecutorDao,
			ReportExporterDao reportExporterDao) {
		super(reportExecutorService, reportExportService, reportExecutorDao, reportExporterDao);
	}

	@Override
	protected boolean isSupportedReport(ReportDto report) {
		return report instanceof GridEditorReportDto;
	}
	
	@Override
	protected Map<String, ImageResource> getIconMap() {
		LinkedHashMap<String, ImageResource> icons = new LinkedHashMap<String, ImageResource>();
		
		icons.put(DashboardMessages.INSTANCE.reportDadgetFormatFull(), BaseIcon.REPORT_PICTURE.toImageResource(1));
		icons.put(DashboardMessages.INSTANCE.reportDadgetFormatPreview(), BaseIcon.EYE.toImageResource(1));
			
		return icons;
	}

	@Override
	protected Map<String, String> getValueMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(DashboardMessages.INSTANCE.reportDadgetFormatPreview(), PREVIEW);
		map.put(DashboardMessages.INSTANCE.reportDadgetFormatFull(), FULL);
		
		return map;
	}


	@Override
	public String getPropertyName() {
		return "gridEditorConfig";
	}
}
