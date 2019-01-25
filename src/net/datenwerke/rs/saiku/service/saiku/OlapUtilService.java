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
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;
import org.saiku.olap.dto.SaikuDimension;
import org.saiku.olap.dto.SaikuHierarchy;
import org.saiku.olap.dto.SaikuMember;
import org.saiku.olap.dto.SimpleCubeElement;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;

public interface OlapUtilService {

	OlapConnection getOlapConnection(SaikuReport report) throws ClassNotFoundException, IOException, SQLException;

	OlapConnection getOlapConnection(MondrianDatasource mondrianDatasource)
			throws IOException, ClassNotFoundException, SQLException;

	Cube getCube(SaikuReport report) throws ClassNotFoundException, IOException, SQLException;

	List<Member> getAllMeasures(Cube cube) throws OlapException;

	List<Dimension> getAllDimensions(Cube cube);

	List<Member> getAllMembers(Cube cube, String dimension, String hierarchy, String level) throws OlapException;

	List<Level> getAllLevels(Cube cube, String dimension, String hierarchy) throws OlapException;

	List<Member> getHierarchyRootMembers(Cube cube, String hierarchyName) throws OlapException;

	List<String> getCubes(MondrianDatasource datasource) throws ClassNotFoundException, IOException, SQLException;

	Map<String, Object> getProperties(Cube cube);

	List<SaikuHierarchy> getAllDimensionHierarchies(Cube cube, String dimensionName);

	SaikuDimension getDimension(Cube cube, String dimensionName);

	List<SimpleCubeElement> getAllMembers(Cube cube, String hierarchy, String level);

	List<SimpleCubeElement> getAllMembers(Cube nativeCube, String hierarchy, String level, String searchString,
			int searchLimit);

	SaikuMember getMember(Cube cube, String uniqueMemberName);

	/**
	 * Clears the Mondrian Cache of the given report.
	 * 
	 * @param report
	 *            the report of which the cache should be cleared
	 */
	void flushCache(Report report);

}
