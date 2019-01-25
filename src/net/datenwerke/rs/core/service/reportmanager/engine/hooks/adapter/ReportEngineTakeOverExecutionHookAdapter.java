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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.hooks.adapter;

import java.io.OutputStream;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngine;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.hooks.ReportEngineTakeOverExecutionHook;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.hookservices.HookAdapterProcessor")
public class ReportEngineTakeOverExecutionHookAdapter implements ReportEngineTakeOverExecutionHook {

	@Override
	public boolean takesOver(ReportEngine engine, Report report, ParameterSet additionalParameters, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
		return false;
	}


	@Override
	public CompiledReport executeReport(ReportEngine engine, OutputStream os, Report report, ParameterSet additionalParameters, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
		return null;
	}


	@Override
	public CompiledReport executeReportDry(ReportEngine engine, Report report, ParameterSet additionalParameters, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
		return null;
	}


	@Override
	public boolean supportsStreaming(ReportEngine reportEngine, Report report,
			ParameterSet parameterSet, User user, String outputFormat,
			ReportExecutionConfig[] configs) {
		return false;
	}



}
