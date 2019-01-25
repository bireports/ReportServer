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
 
 
package net.datenwerke.rs.core.service.reportmanager;

import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ReportDtoServiceImpl implements ReportDtoService {

	private final ReportService reportService;
	private final Provider<TemporaryReportMap> tempReportMapProvider;
	
	@Inject
	public ReportDtoServiceImpl(
		ReportService reportService,
		Provider<TemporaryReportMap> tempReportMapProvider) {
		
		this.reportService = reportService;
		this.tempReportMapProvider = tempReportMapProvider;
	}

	/**
	 * The reference Report of a reportdto is either the corresponding report if its a base report or the parent ..
	 */
	@Override
	public Report getReferenceReport(ReportDto reportDto) {
		if(null == reportDto.getId() && null == reportDto.getKey()){
			throw new IllegalStateException("report id and key are null");
		}
		
		Report referenceReport;
		if(null != reportDto.getId())
			referenceReport = (Report) reportService.getReportById(reportDto.getId());
		else
			referenceReport = (Report) reportService.getReportByKey(reportDto.getKey());
		
		if(referenceReport instanceof ReportVariant)
			referenceReport = reportService.getReportById((((ReportVariant) referenceReport).getBaseReport().getId()));
		
		return referenceReport;
	}

	@Override
	public Report getReport(ReportDto reportDto) {
		Report referenceReport = getReferenceReport(reportDto);
		
		Report report;
		if(null != reportDto.getId())
			report = (Report) reportService.getReportById(reportDto.getId());
		else
			report = (Report) reportService.getReportByKey(reportDto.getKey());
		
		if(! referenceReport.equals(report.getParent()) && ! report.equals(referenceReport))
			throw new IllegalStateException();
	
		return report;
	}

}
