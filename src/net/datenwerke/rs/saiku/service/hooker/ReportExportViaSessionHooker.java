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
 
 
package net.datenwerke.rs.saiku.service.hooker;

import javax.inject.Inject;
import javax.inject.Provider;

import org.saiku.olap.query2.ThinQuery;

import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReportVariant;
import net.datenwerke.rs.core.server.reportexport.hooks.ReportExportViaSessionHook;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECReportExecutorToken;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.saiku.service.saiku.SaikuSessionContainer;
import net.datenwerke.rs.saiku.service.saiku.ThinQueryService;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReportVariant;

public class ReportExportViaSessionHooker implements ReportExportViaSessionHook {

	private Provider<SaikuSessionContainer> saikuSessionContainer;
	private ThinQueryService tqService;

	@Inject
	public ReportExportViaSessionHooker(Provider<SaikuSessionContainer> saikuSessionContainer,
			ThinQueryService tqService) {
		this.saikuSessionContainer = saikuSessionContainer;
		this.tqService = tqService;
	}

	@Override
	public void adjustReport(Report adjustedReport, ReportExecutionConfig... configs) {
		RECReportExecutorToken executorToken = getConfig(RECReportExecutorToken.class, configs);
		if(null != executorToken && adjustedReport instanceof SaikuReportVariant){
			SaikuReport origReport = saikuSessionContainer.get().getReport(executorToken.getToken());
			ThinQuery query = saikuSessionContainer.get().getQueryForReport(origReport);
			if(null != query){
				String xml = tqService.toJSONString(query);
				((SaikuReportVariant) adjustedReport).setQueryXml(xml);
			}
			if(null != origReport)
				((SaikuReportVariant) adjustedReport).setHideParents(origReport.isHideParents());
		} else if(null != executorToken && adjustedReport instanceof TableReportVariant && ((TableReport)adjustedReport).isCubeFlag()){
			SaikuReport origReport = saikuSessionContainer.get().getReport(executorToken.getToken());
			ThinQuery query = saikuSessionContainer.get().getQueryForReport(origReport);
			if(null != query){
				String xml = tqService.toJSONString(query);
				((TableReportVariant) adjustedReport).setCubeXml(xml);
			}
			if(null != origReport)
				((TableReportVariant) adjustedReport).setHideParents(origReport.isHideParents());
		}
	}
	
	private <C extends ReportExecutionConfig> C getConfig(Class<C> type, ReportExecutionConfig... configs){
		if(null == configs || configs.length == 0)
			return null;
		
		for(ReportExecutionConfig config : configs)
			if(type.isAssignableFrom(config.getClass()))
				return (C) config;
			
		return null;
	}

}
