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
 
 
package net.datenwerke.rs.saiku.service.saiku;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.Member;
import org.saiku.datasources.connection.ISaikuConnection;

import net.datenwerke.dbpool.JdbcService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasourceConfig;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.hooks.OlapConnectionHook;

public class OlapUtilServiceImpl implements OlapUtilService {

	private static final String USERNAME_KEY = "jdbcUser";
	private static final String PASSWORD_KEY = "jdbcPassword";
	
	private HookHandlerService hookHandlerService;
	private JdbcService jdbcService;

	@Inject
	public OlapUtilServiceImpl(HookHandlerService hookHandlerService,
			JdbcService jdbcService) {
		this.hookHandlerService = hookHandlerService;
		this.jdbcService = jdbcService;
	}

	@Override
	public OlapConnection getOlapConnection(SaikuReport report) throws ClassNotFoundException, IOException, SQLException{
		DatasourceContainer dsc = report.getDatasourceContainer();
		MondrianDatasource datasource = (MondrianDatasource)dsc.getDatasource();

		return getOlapConnection(datasource);
	}

	@Override
	public Cube getCube(SaikuReport report) throws ClassNotFoundException, IOException, SQLException {
		DatasourceContainer dsc = report.getDatasourceContainer();
		MondrianDatasource datasource = (MondrianDatasource)dsc.getDatasource();
		MondrianDatasourceConfig datasourceConfig = (MondrianDatasourceConfig)dsc.getDatasourceConfig();

		OlapConnection olapConnection = getOlapConnection(datasource);

		return olapConnection.getOlapSchema().getCubes().get(datasourceConfig.getCube());
	}
	
	@Override
	public List<String> getCubes(MondrianDatasource datasource) throws ClassNotFoundException, IOException, SQLException {
		OlapConnection olapConnection = getOlapConnection(datasource);

		List<String> cubes = new ArrayList<String>();
		for(Cube cube : olapConnection.getOlapSchema().getCubes())
			cubes.add(cube.getName());
		return cubes;
	}


	@Override
	public List<Dimension> getAllDimensions(Cube cube) {
		List<Dimension> dims = new ArrayList<Dimension>();
		for(Dimension dim : cube.getDimensions()) {
			if(dim.getName().equals("Measures") || dim.getUniqueName().equals("[Measures]"))
				continue;
			dims.add(dim);
		}
		return dims;
	}

	@Override
	public List<Member> getAllMeasures(Cube cube) throws OlapException {
		List<Member> measures = new ArrayList<Member>();
		for (Measure measure : cube.getMeasures()) {
			if(measure.isVisible()) {
				measures.add(measure);
			}
		}
		if (measures.size() == 0) {
			Hierarchy hierarchy = cube.getDimensions().get("Measures").getDefaultHierarchy();
			measures = hierarchy.getRootMembers();
		}

		return measures;
	}

	@Override
	public OlapConnection getOlapConnection(MondrianDatasource mondrianDatasource) throws IOException, ClassNotFoundException, SQLException{
		Properties props = new Properties();
		props.load(new StringReader(mondrianDatasource.getProperties()));
		
		if(null != mondrianDatasource.getMondrianSchema()){
			props.setProperty("CatalogContent", mondrianDatasource.getMondrianSchema());
		}

		/* use fields if no property defined */
		if(!props.containsKey(USERNAME_KEY) && null != mondrianDatasource.getUsername()){
			props.setProperty(USERNAME_KEY, mondrianDatasource.getUsername());
		}
		if(!props.containsKey(PASSWORD_KEY) && null != mondrianDatasource.getPassword()) {
			props.setProperty(PASSWORD_KEY, mondrianDatasource.getPassword());
		}
		if(!props.containsKey(ISaikuConnection.URL_KEY) && null != mondrianDatasource.getUrl()) {
			props.setProperty(ISaikuConnection.URL_KEY, jdbcService.adaptJdbcUrl(mondrianDatasource.getUrl()));
		}
		
		String driver = props .getProperty(ISaikuConnection.DRIVER_KEY);
		String url = props.getProperty(ISaikuConnection.URL_KEY);

		if (url.length() > 0 && url.charAt(url.length()-1) != ';') {
			url += ";";
		}

		Class.forName(driver);

		OlapConnection connection = (OlapConnection) DriverManager.getConnection(url, props);
		
		for(OlapConnectionHook och : hookHandlerService.getHookers(OlapConnectionHook.class)){
			connection = och.postprocessConnection(connection);
		}

		return connection.unwrap(OlapConnection.class);
	}

	@Override
	public List<Member> getAllMembers(Cube cube, String dimensionName, String hierarchyName, String levelName) throws OlapException  {
		Dimension dim = cube.getDimensions().get(dimensionName);
		if (dim != null) {
			Hierarchy h = dim.getHierarchies().get(hierarchyName);
			if (h == null) {
				for (Hierarchy hlist : dim.getHierarchies()) {
					if (hlist.getUniqueName().equals(hierarchyName) || hlist.getName().equals(hierarchyName)) {
						h = hlist;
					}
				}
			}

			if (h!= null) {
				Level l = h.getLevels().get(levelName);
				if (l == null) {
					for (Level lvl : h.getLevels()) {
						if (lvl.getUniqueName().equals(levelName) || lvl.getName().equals(levelName)) {
							return lvl.getMembers();
						}
					}
				} else {
					return l.getMembers();
				}

			}
		}
		return new ArrayList<Member>();
	}

	@Override
	public List<Level> getAllLevels(Cube cube, String dimensionName, String hierarchyName) throws OlapException {
		Dimension dim = cube.getDimensions().get(dimensionName);
		if (dim != null) {
			Hierarchy h = dim.getHierarchies().get(hierarchyName);
			if (h == null) {
				for (Hierarchy hlist : dim.getHierarchies()) {
					if (hlist.getUniqueName().equals(hierarchyName) || hlist.getName().equals(hierarchyName)) {
						h = hlist;
					}
				}
			}

			if (h!= null) {
				return h.getLevels();
			}
		}
		
		return new ArrayList<Level>();
	}
	
	@Override
	public List<Member> getHierarchyRootMembers(Cube cube, String hierarchyName) throws OlapException {
		Hierarchy h = cube.getHierarchies().get(hierarchyName);
		if (h == null) {
			for (Hierarchy hlist : cube.getHierarchies()) {
				if (hlist.getUniqueName().equals(hierarchyName) || hlist.getName().equals(hierarchyName)) {
					h = hlist;
				}
			}
		}
		if (h!= null) {
			return h.getRootMembers();
		}

		return Collections.EMPTY_LIST;
	}
	
	
}
