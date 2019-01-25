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
 
 
package net.datenwerke.rs.core.service.reportmanager.hooks.adapter;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportExecutionNotificationHook;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.hookservices.HookAdapterProcessor")
public class ReportExecutionNotificationHookAdapter implements ReportExecutionNotificationHook {

	@Override
	public void notifyOfReportExecution(Report report, ParameterSet parameterSet, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
	}


	@Override
	public void notifyOfReportsSuccessfulExecution(CompiledReport compiledReport, Report report, ParameterSet parameterSet, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
	}


	@Override
	public void notifyOfReportsUnsuccessfulExecution(ReportExecutorException e, Report report, ParameterSet parameterSet, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
	}


	@Override
	public void doVetoReportExecution(Report report, ParameterSet parameterSet, User user, String outputFormat, net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig[] configs)  {
	}



}
