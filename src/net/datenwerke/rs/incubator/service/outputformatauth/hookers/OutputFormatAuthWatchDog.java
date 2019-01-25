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
 
 
package net.datenwerke.rs.incubator.service.outputformatauth.hookers;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportStringProperty;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportExecutionNotificationHook;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.incubator.service.outputformatauth.OutputFormatAuthModule;
import net.datenwerke.rs.incubator.service.outputformatauth.locale.OutputFormatAuthMessages;
import net.datenwerke.security.service.usermanager.entities.User;

public class OutputFormatAuthWatchDog implements
		ReportExecutionNotificationHook {

	@Override
	public void notifyOfReportExecution(Report report,
			ParameterSet parameterSet, User user, String outputFormat,
			ReportExecutionConfig[] configs) throws ReportExecutorException {

	}

	@Override
	public void notifyOfReportsSuccessfulExecution(
			CompiledReport compiledReport, Report report,
			ParameterSet parameterSet, User user, String outputFormat,
			ReportExecutionConfig[] configs) {

	}

	@Override
	public void notifyOfReportsUnsuccessfulExecution(ReportExecutorException e,
			Report report, ParameterSet parameterSet, User user,
			String outputFormat, ReportExecutionConfig[] configs) {

	}

	@Override
	public void doVetoReportExecution(Report report, ParameterSet parameterSet,
			User user, String outputFormat, ReportExecutionConfig[] configs) throws ReportExecutorException {
		if(report instanceof ReportVariant)
			report = (Report) report.getParent();
		
		/* always allow certain formats */
		if(ReportExecutorService.METADATA_FORMAT_PLAIN.equals(outputFormat) ||
				ReportExecutorService.OUTPUT_FORMAT_DATACOUNT.equals(outputFormat) ||
				ReportExecutorService.OUTPUT_FORMAT_METADATA.equals(outputFormat) ||
				ReportExecutorService.OUTPUT_FORMAT_TABLE.equals(outputFormat) ){
			return;
		}
		
		ReportProperty property = report.getReportProperty(OutputFormatAuthModule.PROPERTY_OUTPUT_FORMAT_AUTH);
		
		if(property instanceof ReportStringProperty){
			String formatString = ((ReportStringProperty) property).getStrValue();
			if(null != formatString){
				List<String> formats = Arrays.asList(StringUtils.split(formatString,','));
				if(! formats.contains(outputFormat))
					throw new ReportExecutorException(OutputFormatAuthMessages.INSTANCE.exceptionInvlaidFormat(outputFormat));
			}
		}
	}

}
