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
 
 
package net.datenwerke.rs.saiku.service.saiku;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.mdx.IdentifierNode;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.Member;
import org.saiku.datasources.connection.ISaikuConnection;
import org.saiku.olap.dto.SaikuDimension;
import org.saiku.olap.dto.SaikuHierarchy;
import org.saiku.olap.dto.SaikuMember;
import org.saiku.olap.dto.SimpleCubeElement;
import org.saiku.olap.util.ObjectUtil;
import org.saiku.olap.util.exception.SaikuOlapException;
import org.saiku.service.util.MondrianDictionary;
import org.saiku.service.util.exception.SaikuServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mondrian.olap.CacheControl;
import mondrian.olap.CacheControl.CellRegion;
import mondrian.olap4j.SaikuMondrianHelper;
import mondrian.rolap.RolapConnection;
import mondrian.rolap.RolapSchema;
import net.datenwerke.dbpool.JdbcService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasourceConfig;
import net.datenwerke.rs.saiku.service.hooks.OlapCubeCacheHook;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.hooks.OlapConnectionHook;

public class OlapUtilServiceImpl implements OlapUtilService {

	private static final Logger log = LoggerFactory.getLogger(OlapUtilServiceImpl.class);
	
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
		for(OlapCubeCacheHook cache : hookHandlerService.getHookers(OlapCubeCacheHook.class)){
			Cube cube = cache.getCubeFromCache(report);
			if(null != cube)
				return cube;
			break; // only one cache
		}

		DatasourceContainer dsc = report.getDatasourceContainer();
		MondrianDatasource datasource = (MondrianDatasource)dsc.getDatasource();
		MondrianDatasourceConfig datasourceConfig = (MondrianDatasourceConfig)dsc.getDatasourceConfig();

		OlapConnection olapConnection = getOlapConnection(datasource);

		Cube cube = olapConnection.getOlapSchema().getCubes().get(datasourceConfig.getCubeName());

		for(OlapCubeCacheHook cache : hookHandlerService.getHookers(OlapCubeCacheHook.class)){
			cache.putCubeInCache(report, cube, olapConnection);
			break; // only one cache
		}

		return cube;
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
	public List<SaikuHierarchy> getAllDimensionHierarchies(Cube cube, String dimensionName) {
		SaikuDimension dim = getDimension(cube, dimensionName);
		if (dim == null) {
			throw new SaikuServiceException("Cannot find dimension ( "+ dimensionName + ") for cube ( " + cube + " )");
		}
		return dim.getHierarchies();
	}

	@Override
	public SaikuDimension getDimension(Cube cube, String dimensionName) {
		Dimension dim = cube.getDimensions().get(dimensionName);
		if (dim != null) {
			return ObjectUtil.convert(dim);
		}
		return null;
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
			connection = och.postprocessConnection(mondrianDatasource, connection);
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
	public SaikuMember getMember(Cube cube, String uniqueMemberName)  {
		try {
			Member m = cube.lookupMember(IdentifierNode.parseIdentifier(uniqueMemberName).getSegmentList());
			if (m != null) {
				return ObjectUtil.convert(m);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Cannot find member: " + uniqueMemberName + " in cube:"+cube.getName(),e);
		}
	}
	
	@Override
	public List<SimpleCubeElement> getAllMembers(Cube cube, String hierarchy, String level) {
		return getAllMembers(cube, hierarchy, level, null, -1);
	}
	
	@Override
	public List<SimpleCubeElement> getAllMembers(Cube nativeCube, String hierarchy, String level, String searchString, int searchLimit)  {
		try {
			OlapConnection con = nativeCube.getSchema().getCatalog().getDatabase().getOlapConnection();
			Hierarchy h = findHierarchy(hierarchy, nativeCube);
			
			boolean search = StringUtils.isNotBlank(searchString);
			int found = 0;
			List<SimpleCubeElement> simpleMembers;
			if (h!= null) {
				Level l = h.getLevels().get(level);
				if (l == null) {
					for (Level lvl : h.getLevels()) {
						if (lvl.getUniqueName().equals(level) || lvl.getName().equals(level)) {
							l = lvl;
							break;
						}
					}
				} 
				if (l == null) {
					throw new SaikuOlapException("Cannot find level " + level + " in hierarchy " + hierarchy + " of cube " + nativeCube.getName());
				}
				if (isMondrian(nativeCube)) {
					if (SaikuMondrianHelper.hasAnnotation(l, MondrianDictionary.SQLMemberLookup)) {
						if (search) {
							ResultSet rs = SaikuMondrianHelper.getSQLMemberLookup(con, MondrianDictionary.SQLMemberLookup, l, searchString);
							simpleMembers = ObjectUtil.convert2simple(rs);
							log.debug("Found " + simpleMembers.size() + " members using SQL lookup for level " + level);
							return simpleMembers;
						} else {
							return new ArrayList<>();
						}
					}
					
				}
				if (search || searchLimit > 0) {
					List<Member> foundMembers = new ArrayList<>();
				  List<Member> lokuplist;
				  if(SaikuMondrianHelper.isMondrianConnection(con) &&
					 SaikuMondrianHelper.getMondrianServer(con).getVersion().getMajorVersion()>=4) {
					lokuplist = SaikuMondrianHelper.getMDXMemberLookup(con, nativeCube.getName(), l);
				  }
				  else{
					lokuplist = l.getMembers();
				  }
					for (Member m : lokuplist) {
						if (search) {
							if (m.getName().toLowerCase().contains(searchString) || m.getCaption().toLowerCase().contains(searchString) ) {
									foundMembers.add(m);
									found++;
							}
						} else {
							foundMembers.add(m);
							found++;
						}
						if (searchLimit > 0 && found >= searchLimit) {
							break;
						}
					}
					simpleMembers = ObjectUtil.convert2Simple(foundMembers);
				} else {
				  List<Member> lookuplist = null;
				  if(SaikuMondrianHelper.isMondrianConnection(con) &&
					 SaikuMondrianHelper.getMondrianServer(con).getVersion().getMajorVersion()>=4) {
					 lookuplist = SaikuMondrianHelper.getMDXMemberLookup(con, nativeCube.getName(), l);
				  }
				  else{
					lookuplist = l.getMembers();
				  }
				  simpleMembers = ObjectUtil.convert2Simple(lookuplist);
				}
				return simpleMembers;
			}
		} catch (Exception e) {
			throw new SaikuServiceException("Cannot get all members",e);
		}

		return new ArrayList<>();

	}

	@Override
	public Map<String, Object> getProperties(Cube c) {
		Map<String, Object> properties = new HashMap<>();
		try {
			OlapConnection con = c.getSchema().getCatalog().getDatabase().getOlapConnection();
			properties.put("saiku.olap.query.drillthrough", c.isDrillThroughEnabled());
			properties.put("org.saiku.query.explain", con.isWrapperFor(RolapConnection.class));

			try {
				Boolean isScenario = (c.getDimensions().get("Scenario") != null);
				properties.put("org.saiku.connection.scenario", isScenario);
			} catch (Exception e) {
				properties.put("org.saiku.connection.scenario", false);
			}
		} catch (Exception e) {
			throw new SaikuServiceException(e);
		}
		return properties;
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
	
	private boolean isMondrian(Cube cube) {
		OlapConnection con = cube.getSchema().getCatalog().getDatabase().getOlapConnection();
		try {
			return con.isWrapperFor(RolapConnection.class);
		} catch (SQLException e) {
			log.error("SQLException", e.getNextException());
		}
		return false;
	}
	
	private Hierarchy findHierarchy(String name, Cube cube) {
		Hierarchy h = cube.getHierarchies().get(name);
		if (h != null) {
			return h;
		}
		for (Hierarchy hierarchy : cube.getHierarchies()) {
			if (hierarchy.getUniqueName().equals(name)) {
				return hierarchy;
			}
		}
		return null;
	}

	@Override
	public void flushCache(Report report) {
		if (report instanceof SaikuReport) {
			
			try {
				OlapConnection con = getOlapConnection((SaikuReport)report);
				RolapConnection rolapConnection = con.unwrap(mondrian.rolap.RolapConnection.class);
				RolapSchema rolapSchema = rolapConnection.getSchema();
				CacheControl cacheControl = rolapConnection.getCacheControl(null);
				
				for (mondrian.olap.Cube cube: rolapSchema.getCubes()) {
					Cube reportCube = getCube((SaikuReport) report);
					if (!reportCube.getName().equals(cube.getName())) 
						continue;
					
					CellRegion allCells = cacheControl.createMeasuresRegion(cube);
					cacheControl.flush(allCells);
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalArgumentException("Mondrian cache flush on illegal report type: " + report.getClass());
		}
	}

}
