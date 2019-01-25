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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.datenwerke.gf.service.jpa.annotations.JpaUnit;
import net.datenwerke.rs.ReportServerPUStartup;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.configservice.service.configservice.ConfigDirService;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;

public class InstallBaseDataTask implements DbInstallationTask{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	public final static String REPORTSERVER_DATA_SOURCE_NAME = "ReportServer Data Source"; //$NON-NLS-1$
	public static final String INTERNAL_DATASOURCES_FOLDER_NAME = "internal datasources"; //$NON-NLS-1$
	public static final String ADMINISTRATIVE_REPORTS_FOLDER = "administrative reports";  //$NON-NLS-1$
	
	private final AuditLoggingInstallReportService auditLoggingInstallService;
	private final DatasourceService datasourceService;
	private final ReportService reportService;
	private final DBHelperService dbHelperService;
	private final ConfigDirService configDirService;
	
	@Inject
	public InstallBaseDataTask(
		@JpaUnit String persistenceUnitName,
		AuditLoggingInstallReportService auditLoggingInstallService,
		DatasourceService datasourceService,
		DBHelperService dbHelperService,
		ConfigDirService configDirService,
		ReportService reportService
		){
		
		/* store objects */
		this.auditLoggingInstallService = auditLoggingInstallService;
		this.datasourceService = datasourceService;
		this.dbHelperService = dbHelperService;
		this.configDirService = configDirService;
		this.reportService = reportService;
	}
	
	
	@Override
	public void executeOnStartup() {
		
	}
	
	@Override
	public void executeOnFirstRun() {
		DatasourceDefinition rsDatasource = installDatasource();
		ReportFolder adminFolder = createAdminReportFolder();
		
		/* create logging report */
		auditLoggingInstallService.install(adminFolder, rsDatasource);
	}

	protected ReportFolder createAdminReportFolder() {
		ReportFolder adminFolder = new ReportFolder();
		adminFolder.setName(ADMINISTRATIVE_REPORTS_FOLDER);
		
		ReportFolder root = (ReportFolder) reportService.getRoots().get(0);
		root.addChild(adminFolder);
		
		reportService.persist(adminFolder);
		
		return adminFolder;
	}

	protected DatasourceDefinition installDatasource() {
		DatasourceFolder folder = new DatasourceFolder(); 
		folder.setName(INTERNAL_DATASOURCES_FOLDER_NAME); //$NON-NLS-1$

		AbstractDatasourceManagerNode root = datasourceService.getRoots().get(0);
		root.addChild(folder);
		datasourceService.persist(folder);
		
		/* read properties from persistence.properties */
		String url = ""; 
		String username = ""; 
		String password = ""; 
		String dbHelperName = "DBHelper_MySQL"; //$NON-NLS-1$
		
		try {
			PropertiesConfiguration peProps = null;
			try{
				peProps = new PropertiesConfiguration(ReportServerPUStartup.PERSISTENCE_PROP_NAME);
			} catch(Exception e){}
			if(configDirService.isEnabled()){
				try {
					File configDir = configDirService.getConfigDir();
					peProps = new PropertiesConfiguration(new File(configDir, ReportServerPUStartup.PERSISTENCE_PROP_NAME));
				} catch (Exception e) {
				}
			}
			
			if(null == peProps)
				throw new IllegalStateException("Could not load persistence.properties");
			
			url = peProps.getString("hibernate.connection.url", "");
			username = peProps.getString("hibernate.connection.username", "");
			password = peProps.getString("hibernate.connection.password", "");
			String driver = peProps.getString("hibernate.connection.driver_class", "");
			
			for(DatabaseHelper dh : dbHelperService.getDatabaseHelpers()){
				if(driver.equals(dh.getDriver())){
					dbHelperName = dh.getDescriptor();
					break;
				}
			}
			
		} catch (Exception e) {
			logger.warn("Could not extract internal datasource properties", e);
		}
		
		DatabaseDatasource rsDataSource = new DatabaseDatasource();
		rsDataSource.setDatabaseDescriptor(dbHelperName);
		rsDataSource.setUrl(url);
		rsDataSource.setName(REPORTSERVER_DATA_SOURCE_NAME);
		rsDataSource.setUsername(username);
		rsDataSource.setPassword(password);
		folder.addChild(rsDataSource);
		datasourceService.persist(rsDataSource);
		
		return rsDataSource;
	}

}
