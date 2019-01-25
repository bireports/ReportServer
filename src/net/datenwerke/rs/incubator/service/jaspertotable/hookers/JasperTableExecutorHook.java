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
 
 
package net.datenwerke.rs.incubator.service.jaspertotable.hookers;

import java.io.OutputStream;

import net.datenwerke.rs.base.service.reportengines.jasper.JasperReportEngine;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngine;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.hooks.ReportEngineTakeOverExecutionHook;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.incubator.service.jaspertotable.JasperToTableService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class JasperTableExecutorHook implements
		ReportEngineTakeOverExecutionHook {

	private final ReportExecutorService reportExecutorService;
	private final JasperToTableService jasperTableService;
	
	@Inject
	public JasperTableExecutorHook(
		ReportExecutorService reportExecutorService,
		JasperToTableService jasperTableService
		){
		
		/* store objects */
		this.reportExecutorService = reportExecutorService;
		this.jasperTableService = jasperTableService;
	}
	
	@Override
	public boolean takesOver(ReportEngine engine, Report report,
			ParameterSet additionalParameters, User user, String outputFormat,
			ReportExecutionConfig[] configs) {
		return JasperReportEngine.class.equals(engine.getClass()) && outputFormat.startsWith("TABLE_");
	}

	@Override
	public CompiledReport executeReport(ReportEngine engine, OutputStream os, Report report,
			ParameterSet additionalParameters, User user, String outputFormat,
			ReportExecutionConfig[] configs) throws ReportExecutorException {
		JasperReport jReport = (JasperReport) report;
		
		TableReport tReport = jasperTableService.transformToTableReport(jReport);
		
		return reportExecutorService.execute(os, tReport, additionalParameters, user, outputFormat.substring(6), configs);
	}
	
	@Override
	public CompiledReport executeReportDry(ReportEngine engine,
			Report report, ParameterSet additionalParameters,
			User user, String outputFormat, ReportExecutionConfig[] configs)
			throws ReportExecutorException {
		return reportExecutorService.executeDry(report, additionalParameters, user, outputFormat.substring(6), configs);
	}

	@Override
	public boolean supportsStreaming(ReportEngine reportEngine, Report report,
			ParameterSet parameterSet, User user, String outputFormat,
			ReportExecutionConfig[] configs) {

		return false;
	}

}
