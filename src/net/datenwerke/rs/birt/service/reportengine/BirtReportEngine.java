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
 
 
package net.datenwerke.rs.birt.service.reportengine;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.datenwerke.rs.base.service.datasources.transformers.DatasourceTransformationService;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReport;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtOutputGenerator;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtOutputGeneratorManager;
import net.datenwerke.rs.birt.service.reportengine.output.metadata.BirtMetadataExporter;
import net.datenwerke.rs.birt.service.reportengine.output.metadata.BirtMetadataExporterManager;
import net.datenwerke.rs.birt.service.reportengine.sandbox.BirtEngineEnvironment;
import net.datenwerke.rs.birt.service.reportengine.sandbox.BirtEngineEnvironmentFactory;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngine;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorRuntimeException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

@Singleton
public class BirtReportEngine extends ReportEngine<Connection, BirtOutputGenerator, BirtMetadataExporter>{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final BirtReportService birtReportService;
	private final BirtEngineEnvironmentFactory birtEngineEnvironmentFactory;


	@Inject
	public BirtReportEngine(
			BirtReportService birtReportService,
			BirtOutputGeneratorManager outputGeneratorManager,
			BirtMetadataExporterManager metadataExporterManager,
			BirtEngineEnvironmentFactory birtEngineEnvironmentFactory, 
			DatasourceTransformationService datasourceTransformationService 
			) {

		super(outputGeneratorManager, metadataExporterManager, datasourceTransformationService);
		this.birtReportService = birtReportService;

		this.birtEngineEnvironmentFactory = birtEngineEnvironmentFactory;
	}

	@Override
	protected CompiledReport doExecute(OutputStream os, Report report, User user, ParameterSet parameters, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		if(! birtReportService.isBirtEnabled())
			throw new ReportExecutorException("Birt is disabled");
		
		/* validate arguments and cast them */
		if(!(report instanceof BirtReport))
			throw new IllegalArgumentException("Need a report of type BirtReport."); //$NON-NLS-1$
		BirtReport bReport = (BirtReport) report;

		Connection con = transformDatasource(Connection.class, report, parameters);

		return executeReport(con, bReport, parameters, outputFormat, user, configs);
	}
	
	private CompiledReport executeReport(Connection connection, BirtReport bReport, ParameterSet parameters, String outputFormat, User user, ReportExecutionConfig... configs) {
		try{
			/* get output generator */
			BirtOutputGenerator outputGenerator = outputGeneratorManager.getOutputGenerator(outputFormat);
			byte[] reportBytes = bReport.getReportFile().getContent().getBytes();
			
			IReportEngine reportEngine = birtReportService.getReportEngine();
			BirtEngineEnvironment env = birtEngineEnvironmentFactory.create(reportEngine, reportBytes, parameters, connection, outputFormat, outputGenerator, configs);
			
			return env.call();
		}catch(Exception e){
			throw new ReportExecutorRuntimeException(e);
		} finally {
			try {
				if(null != connection && ! connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				logger.info( "Could not close connection after birt report execution", e);
			}
		}
	}

	@Override
	public boolean consumes(Report report) {
		return report instanceof BirtReport;
	}

}
