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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import net.datenwerke.dbpool.DbPoolService;
import net.datenwerke.dbpool.config.predefined.StandardConnectionConfig;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.db.H2;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.core.service.internaldb.MondrianLoader;
import net.datenwerke.rs.core.service.internaldb.pool.DemoDbConnectionPool;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoDataInstallTask implements DbInstallationTask {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private static final String DEMO_DATASOURCES_FOLDER_NAME = "sample data";
	private static final String DEMO_DATA_SOURCE_NAME = "Demo Data";

	private DbPoolService<Connection> dbPoolService;
	private DatasourceService datasourceService;
	private DBHelperService dbHelperService;

	@Inject
	public DemoDataInstallTask(
			DbPoolService dbPoolService, 
			DatasourceService datasourceService,
			DBHelperService dbHelperService
			) {

		this.dbPoolService = dbPoolService;
		this.datasourceService = datasourceService;
		this.dbHelperService = dbHelperService;
	}

	@Override
	public void executeOnStartup() {
		logger.info("Loading demodata");
		try(Connection connection = getDemoConnection().get()){

			connection.prepareStatement("DROP ALL OBJECTS").execute();

			/* classicmodes */
			connection.prepareStatement("RUNSCRIPT FROM 'classpath:resources/demo/demodata.sql'").execute();
			connection.commit();

			/* foodmart */
			MondrianLoader ml = new MondrianLoader();
			ml.setAggregates(true);
			ml.setTables(true);
			ml.setData(true);
			ml.setIndexes(true);
			ml.setJdbcOutput(true);
			ml.setConnection(connection);
			ml.load();


		} catch (Exception e) {
			logger.warn("Failed to load demodata", e);
		}


	}

	protected Future<Connection> getDemoConnection() throws SQLException{
		return dbPoolService.getConnection(new DemoDbConnectionPool(getDemoJdbcConnectionUrl()), new StandardConnectionConfig());
	}

	private String getDemoJdbcConnectionUrl() {
		return "jdbc:h2:mem:demodata;DB_CLOSE_DELAY=-1";
	}

	@Override
	public void executeOnFirstRun() {
		installDatasource();
	}

	protected void installDatasource() {
		DatasourceFolder folder = new DatasourceFolder(); 
		folder.setName(DEMO_DATASOURCES_FOLDER_NAME); //$NON-NLS-1$

		AbstractDatasourceManagerNode root = datasourceService.getRoots().get(0);
		root.addChild(folder);
		datasourceService.persist(folder);

		String url = getDemoJdbcConnectionUrl();
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
		mds.setUrl("jdbc:mondrian:Jdbc=" + url);

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
