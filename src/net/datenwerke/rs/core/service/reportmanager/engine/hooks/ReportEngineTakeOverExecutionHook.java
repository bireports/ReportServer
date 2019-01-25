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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.hooks;

import java.io.OutputStream;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.hookservices.annotations.HookConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngine;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

@HookConfig
public interface ReportEngineTakeOverExecutionHook extends Hook {

	/**
	 * return true in order to bypass the report engine and perform manual execution of the report
	 * 
	 * @param engine
	 * @param report
	 * @param additionalParameters
	 * @param user
	 * @param outputFormat
	 * @param configs
	 * @return
	 */
	boolean takesOver(ReportEngine engine, Report report, ParameterSet additionalParameters,
			User user, String outputFormat, ReportExecutionConfig[] configs);

	/**
	 * Execute the report. Note that if os is not null then the actual report data needs to be
	 * written to the output stream.
	 * 
	 * @param engine
	 * @param os
	 * @param report
	 * @param additionalParameters
	 * @param user
	 * @param outputFormat
	 * @param configs
	 * @return
	 * @throws ReportExecutorException
	 * @throws ExpectedException 
	 */
	CompiledReport executeReport(ReportEngine engine, OutputStream os, Report report,
			ParameterSet additionalParameters, User user, String outputFormat,
			ReportExecutionConfig[] configs) throws ReportExecutorException, ExpectedException;
	
	/**
	 * Return an empty CompiledReport object to specify mime type and extension.
	 * 
	 * @param engine
	 * @param report
	 * @param additionalParameters
	 * @param user
	 * @param outputFormat
	 * @param configs
	 * @return
	 * @throws ReportExecutorException
	 * @throws ExpectedException 
	 */
	CompiledReport executeReportDry(ReportEngine engine, Report report,
			ParameterSet additionalParameters, User user, String outputFormat,
			ReportExecutionConfig[] configs) throws ReportExecutorException, ExpectedException;

	boolean supportsStreaming(ReportEngine reportEngine,
			Report report, ParameterSet parameterSet, User user,
			String outputFormat, ReportExecutionConfig[] configs);

}
