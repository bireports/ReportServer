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
 
 
package net.datenwerke.rs.incubator.client.jaspertotable.hooker;

import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.core.client.reportexporter.exporter.generic.Export2Excel;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.decorator.ReportDtoDec;
import net.datenwerke.rs.incubator.client.jaspertotable.JasperToTableUIModule;
import net.datenwerke.rs.incubator.client.jaspertotable.locale.JasperToTableMessages;

/**
 * 
 *
 */
public class Jasper2TableExcel extends Export2Excel {

	public String getExportDescription() {
		return JasperToTableMessages.INSTANCE.exportDescription(); 
	}

	public String getExportTitle() {
		return JasperToTableMessages.INSTANCE.exportDescription();
	}

	public String getOutputFormat() {
		return "TABLE_EXCEL"; //$NON-NLS-1$
	}
	
	public boolean consumes(ReportDto report) {
		return report instanceof JasperReportDto && ((ReportDtoDec)report).hasReportPropertyWithName(JasperToTableUIModule.PROPERTY_NAME);
	}
}
