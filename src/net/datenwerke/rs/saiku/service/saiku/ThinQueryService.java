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

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.olap4j.CellSet;
import org.olap4j.metadata.Cube;
import org.saiku.olap.dto.SimpleCubeElement;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.query2.ThinQuery;
import org.saiku.olap.util.formatter.ICellSetFormatter;
import org.saiku.service.util.KeyValue;
import org.saiku.service.util.QueryContext;

import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;

public interface ThinQueryService extends Serializable {

	boolean isOldQuery(String filecontent);

	ThinQuery convertQuery(String xml, Cube cube, SaikuReport report);

	ThinQuery createQuery(ThinQuery tq, Cube cube);

	boolean isMdxDrillthrough(ThinQuery query, SaikuReport report);

	ResultSet drillthrough(ThinQuery tq, SaikuReport report);

	CellDataSet execute(ThinQuery tq, SaikuReport report, Cube cube);

	QueryContext getContext(String name);

	void cancel(String queryName) throws SQLException;

	ThinQuery updateQuery(ThinQuery old, Cube cube, SaikuReport report);

	List<SimpleCubeElement> getResultMetadataMembers(String queryName, Cube cube, boolean preferResult,
			String hierarchyName, String levelName, String searchString, int searchLimit);

	byte[] getExport(String queryName, Cube cube, String type);

	byte[] getExport(String queryName, Cube cube, String type, String formatter);

	ThinQuery zoomIn(String queryName, Cube cub, SaikuReport report, List<List<Integer>> realPositions);

	ResultSet drillthrough(String queryName, SaikuReport report, int maxrows, String returns);

	ResultSet drillthrough(String queryName, SaikuReport report, List<Integer> cellPosition, Integer maxrows,
			String returns);

	byte[] exportResultSetCsv(ResultSet rs);

	byte[] exportResultSetCsv(ResultSet rs, String delimiter, String enclosing, boolean printHeader,
			List<KeyValue<String, String>> additionalColumns);

	CellDataSet getFormattedResult(String query, Cube cube, String format) throws Exception;

	CellDataSet execute(ThinQuery tq, SaikuReport report, Cube cube, String formatter);

	ThinQuery drillacross(String queryName, Cube cub, SaikuReport report, List<Integer> cellPosition, Map<String, List<String>> levels);

	
	String toJSONString(ThinQuery query);

	CellSet executeInternalQuery(ThinQuery query, SaikuReport report, Cube cube) throws Exception;

	void calculateTotals(ThinQuery tq, Cube cub, CellDataSet result, CellSet cellSet, ICellSetFormatter formatter)
			throws Exception;

}
