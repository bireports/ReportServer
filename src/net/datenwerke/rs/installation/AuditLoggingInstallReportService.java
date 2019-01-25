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
 
 
package net.datenwerke.rs.installation;

import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasourceConfig;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;

import com.google.inject.Inject;

public class AuditLoggingInstallReportService {
	
	
	private final static String LOGGING_REPORT_FOLDER_NAME = "audit logs"; //$NON-NLS-1$
	
	
	private final ReportService reportService;
	
	@Inject
	public AuditLoggingInstallReportService(
		ReportService reportService
		) {
		this.reportService = reportService;
	}
	
	public void install(ReportFolder baseFolder, DatasourceDefinition reportServerDatasource){
		ReportFolder logReportsFolder = createFolder(baseFolder, LOGGING_REPORT_FOLDER_NAME);
		createSimpleLoggingReport(logReportsFolder, reportServerDatasource);
	}
	
	private ReportFolder createFolder(AbstractReportManagerNode parent, String name){
		ReportFolder loggingFolder = new ReportFolder();
		loggingFolder.setName(name);
		parent.addChild(loggingFolder);
		
		reportService.persist(loggingFolder);
		
		return loggingFolder;
	}
	
	private TableReport createSimpleLoggingReport(ReportFolder folder, DatasourceDefinition loggingDataSource) {
		TableReport simpleLoggingReport = new TableReport();
		simpleLoggingReport.setName("Audit Logs"); //$NON-NLS-1$
		DatabaseDatasourceConfig datasourceConfig = new DatabaseDatasourceConfig();
		
		String query = 
			"SELECT E.*, P.KEY_FIELD, P.VALUE\n" +
				"FROM RS_AUDIT_LOG_ENTRY E,\n" +
					"RS_AUDIT_LOG_PROPERTY P\n" +
				"WHERE\n" +
					"E.ENTITY_ID = P.LOG_ENTRY_ID";
		
		datasourceConfig.setQuery(query); //$NON-NLS-1$
		
		DatasourceContainer dsContainer = new DatasourceContainer();
		dsContainer.setDatasource(loggingDataSource);
		dsContainer.setDatasourceConfig(datasourceConfig);
		simpleLoggingReport.setDatasourceContainer(dsContainer);
		
		folder.addChild(simpleLoggingReport);
		reportService.persist(simpleLoggingReport);
		
		return simpleLoggingReport;
	}

}