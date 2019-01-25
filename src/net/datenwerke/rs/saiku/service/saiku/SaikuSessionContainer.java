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

import java.util.HashMap;

import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;

import org.saiku.olap.query.IQuery;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class SaikuSessionContainer {

	private HashMap<String, SaikuReport> saikureports = new HashMap<String, SaikuReport>();
	private HashMap<String, IQuery> queries = new HashMap<String, IQuery>();
	private BiMap<SaikuReport, IQuery> reportQueryMap = HashBiMap.create();
	
	public void putReport(String token, SaikuReport report){
		saikureports.put(token, report);
	}
	
	public SaikuReport getReport(String token){
		return saikureports.get(token);
	}
	
	public void putQuery(String queryName, SaikuReport report, IQuery query) {
		queries.put(queryName, query);
		reportQueryMap.put(report, query);
	}
	
	public IQuery getQuery(String queryName) {
		return queries.get(queryName);
	}

	public HashMap<String, IQuery> getQueries() {
		return queries;
	}
	
	public void removeQuery(String queryName) {
		if (queries.containsKey(queryName)) {
			IQuery q = queries.remove(queryName);
			reportQueryMap.inverse().remove(q);
			try {
				q.cancel();
			} catch (Exception e) {}
			q = null;
		}
	}

	public IQuery getQueryForReport(SaikuReport report) {
		return reportQueryMap.get(report);
	}
	
	public void putQuery(String queryName, IQuery query) {
		SaikuReport report = reportQueryMap.inverse().get(getQuery(queryName));
		if(null == report)
			throw new RuntimeException("Cannot create query " + queryName + ". No report assigned.");
		
		putQuery(queryName, report, query);
	}
}
