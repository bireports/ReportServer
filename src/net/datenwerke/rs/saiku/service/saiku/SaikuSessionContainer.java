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

import java.util.HashMap;

import org.saiku.olap.query.IQuery;
import org.saiku.olap.query2.ThinQuery;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.servlet.SessionScoped;

import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;

@SessionScoped
public class SaikuSessionContainer {

	private HashMap<String, SaikuReport> saikureports = new HashMap<String, SaikuReport>();
	private HashMap<String, ThinQuery> queries = new HashMap<String, ThinQuery>();
	private BiMap<SaikuReport, ThinQuery> reportQueryMap = HashBiMap.create();
	
	public void putReport(String token, SaikuReport report){
		saikureports.put(token, report);
	}
	
	public SaikuReport getReport(String token){
		return saikureports.get(token);
	}
	
	private void putQuery(String queryName, SaikuReport report, ThinQuery query) {
		if(null == queryName)
			throw new RuntimeException("Cannot create query. No query name assigned.");
		
		queries.put(queryName, query);
		reportQueryMap.put(report, query);
	}
	
	public ThinQuery getQuery(String queryName) {
		return queries.get(queryName);
	}

	public HashMap<String, ThinQuery> getQueries() {
		return queries;
	}
	
	public ThinQuery removeQuery(String queryName) {
		if (queries.containsKey(queryName)) {
			ThinQuery q = queries.remove(queryName);
			reportQueryMap.inverse().remove(q);
			return q;
		}
		return null;
	}

	public ThinQuery getQueryForReport(SaikuReport report) {
		return reportQueryMap.get(report);
	}
	
	public void putQuery(ThinQuery query, SaikuReport report) {
		String queryName = query.getName();
		putQuery(queryName, report, query);
	}
	
	public void putQuery(ThinQuery query) {
		String queryName = query.getName();
		if(null == queryName)
			throw new RuntimeException("Cannot create query. No query name assigned.");
		
		SaikuReport report = reportQueryMap.inverse().get(getQuery(queryName));
		if(null == report)
			throw new RuntimeException("Cannot create query " + queryName + ". No report assigned.");
		
		putQuery(queryName, report, query);
	}
}
