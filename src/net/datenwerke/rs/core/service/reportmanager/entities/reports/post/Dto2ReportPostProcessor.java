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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.reports.post;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoPostProcessor;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportPropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportStringPropertyDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportStringProperty;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportCreatedFromDtoHook;

import com.google.inject.Inject;

public class Dto2ReportPostProcessor implements Dto2PosoPostProcessor<ReportDto, Report> {

	private final HookHandlerService hookHandler;
	
	@Inject
	public Dto2ReportPostProcessor(
		HookHandlerService hookHandler
		) {
		
		this.hookHandler = hookHandler;
	}

	@Override
	public void posoCreated(ReportDto reportDto, Report report) {
		copyStringProperties(reportDto, report);
		
		/* call hooks */
		for(ReportCreatedFromDtoHook hooker : hookHandler.getHookers(ReportCreatedFromDtoHook.class))
			hooker.reportCreated(reportDto, report);
	}

	@Override
	public void posoInstantiated(Report arg0) {
		
	}

	@Override
	public void posoLoaded(ReportDto arg0, Report arg1) {
		
	}

	@Override
	public void posoMerged(ReportDto reportDto, Report report) {
		copyStringProperties(reportDto, report);
		
		for(ReportCreatedFromDtoHook hooker : hookHandler.getHookers(ReportCreatedFromDtoHook.class))
			hooker.reportMerged(reportDto, report);
	}

	@Override
	public void posoCreatedUnmanaged(ReportDto reportDto, Report report) {
		copyStringProperties(reportDto, report);
		
		for(ReportCreatedFromDtoHook hooker : hookHandler.getHookers(ReportCreatedFromDtoHook.class))
			hooker.reportCreatedUnmanaged(reportDto, report);
	}

	protected void copyStringProperties(ReportDto reportDto, Report report) {
		/* copy string parameters */
		if(null == reportDto.getReportProperties())
			return;
		
		for(ReportPropertyDto prop : reportDto.getReportProperties()){
			if(prop instanceof ReportStringPropertyDto){
				if(null == report.getReportProperty(prop.getName())){
					ReportStringProperty copy = new ReportStringProperty();
					copy.setName(prop.getName());
					report.addReportProperty(copy);
				}
				ReportProperty varProp = report.getReportProperty(prop.getName());
				if(varProp instanceof ReportStringProperty)
					((ReportStringProperty)varProp).setStrValue(((ReportStringPropertyDto) prop).getStrValue());
			}
		}
	}

}
