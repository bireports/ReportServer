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

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.db.H2;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;

public class DemoDataInstallTask implements DbInstallationTask {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private static final String DEMO_DATASOURCES_FOLDER_NAME = "sample data";
	private static final String DEMO_DATA_SOURCE_NAME = "Demo Data";

	private DatasourceService datasourceService;
	private DBHelperService dbHelperService;

	@Inject
	public DemoDataInstallTask(
			DatasourceService datasourceService,
			DBHelperService dbHelperService
			) {
		this.datasourceService = datasourceService;
		this.dbHelperService = dbHelperService;
	}

	@Override
	public void executeOnStartup() {
	}

	@Override
	public void executeOnFirstRun() {
		installDatasource();
	}

	protected void installDatasource() {
		logger.info("install demo data datasources");
		
		DatasourceFolder folder = new DatasourceFolder(); 
		folder.setName(DEMO_DATASOURCES_FOLDER_NAME); //$NON-NLS-1$

		AbstractDatasourceManagerNode root = datasourceService.getRoots().get(0);
		root.addChild(folder);
		datasourceService.persist(folder);

		String url = "rs:demodata";
		String username = "demo";
		String password = "demo";
		String driver = "org.h2.Driver";
		String dbHelperName = "";

		for(DatabaseHelper dh : dbHelperService.getDatabaseHelpers()){
			if(driver.equals(dh.getDriver())){
				dbHelperName = dh.getDescriptor();
				break;
			}
		}


		DatabaseDatasource demoDataSource = new DatabaseDatasource();
		demoDataSource.setDatabaseDescriptor(dbHelperName);
		demoDataSource.setUrl(url);
		demoDataSource.setName(DEMO_DATA_SOURCE_NAME);
		demoDataSource.setUsername(username);
		demoDataSource.setPassword(password);
		folder.addChild(demoDataSource);
		datasourceService.persist(demoDataSource);

		MondrianDatasource mds = new MondrianDatasource();
		InputStream is = getClass().getClassLoader().getResourceAsStream("resources/demo/FoodMart-schema.xml");
		mds.setName("Foodmart");

		mds.setUsername(username);
		mds.setPassword(password);
		mds.setUrl("rs:mondrian:demodata");

		try {
			mds.setMondrianSchema(IOUtils.toString(is));
		} catch (IOException e) {
		}
		String props = "type=OLAP\n" + 
				"name=foodmart\n" + 
				"driver=mondrian.olap4j.MondrianOlap4jDriver\n" + 
				"jdbcDrivers=" + H2.DB_DRIVER + ""; 
		mds.setProperties(props);

		folder.addChild(mds);
		datasourceService.persist(mds);

	}
}
