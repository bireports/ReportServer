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
 
 
package net.datenwerke.rs.saiku.server.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import mondrian.olap.MondrianServer;
import mondrian.olap.MondrianServer.MondrianVersion;
import mondrian.server.monitor.ConnectionInfo;
import mondrian.server.monitor.Monitor;
import mondrian.server.monitor.ServerInfo;
import mondrian.server.monitor.StatementInfo;

@Path("/saiku/statistics")
public class StatisticsResource {

//	StringWriter sqlWriter = new StringWriter();
//	StringWriter mdxWriter = new StringWriter();
//	StringWriter profileWriter = new StringWriter();
//	StringWriter saikuWriter = new StringWriter();
//	
//	public StatisticsResource() {
//		setupLog("mondrian.sql", "DEBUG", sqlWriter);
//		setupLog("mondrian.mdx", "DEBUG", mdxWriter);
//		setupLog("mondrian.profile", "DEBUG", profileWriter);
//		setupLog("org.saiku.service", "INFO", saikuWriter);
//		System.out.println("##########################SETUP LOG");
//		
//		
//	}
	
//	private void setupLog(String category, String level, StringWriter writer) {
//		Logger pkgLogger = Logger.getRootLogger().getLoggerRepository().getLogger(category);
//		pkgLogger.setLevel(Level.toLevel(level));
//		WriterAppender appender = new WriterAppender(new PatternLayout("%d{ISO8601} %p - %m%n"), writer);
//		appender.setName("CONSOLE_APPENDER");
//		appender.setThreshold(Level.ERROR);
//		pkgLogger.addAppender(appender);
//		Logger.getRootLogger().addAppender(appender);
//	}
	
	
	@GET
	@Produces({"application/json" })
	@Path("/mondrian")
	public MondrianStats getMondrianStats() {
		
		MondrianServer mondrianServer = MondrianServer.forId(null);
		if (mondrianServer != null) {
			MondrianVersion mv = mondrianServer.getVersion();
			
			final Monitor monitor = mondrianServer.getMonitor();
	        final ServerInfo server = monitor.getServer();
	        
	        int statementCurrentlyOpenCount = server.statementCurrentlyOpenCount();
	        int connectionCurrentlyOpenCount = server.connectionCurrentlyOpenCount();
	        int sqlStatementCurrentlyOpenCount = server.sqlStatementCurrentlyOpenCount();
	        int statementCurrentlyExecutingCount = server.statementCurrentlyExecutingCount();
	        float avgCellDimensionality =  ((float) server.cellCoordinateCount / (float) server.cellCount);
	        
	        final List<ConnectionInfo> connections = monitor.getConnections();
	        final List<StatementInfo> statements = monitor.getStatements();
	        
	        return new MondrianStats(
	        		server,
	        		mv,
	        		statementCurrentlyOpenCount,
	        		connectionCurrentlyOpenCount,
	        		sqlStatementCurrentlyOpenCount,
	        		statementCurrentlyExecutingCount,
	        		avgCellDimensionality,
	        		connections,
	        		statements
	        		);
		}
		
		return null;
	}
	

	@GET
	@Produces({"application/json" })
	@Path("/mondrian/server")
	public ServerInfo getMondrianServer() {
		MondrianServer mondrianServer = MondrianServer.forId(null);
		if (mondrianServer != null) {
			MondrianVersion mv = mondrianServer.getVersion();
			
			final Monitor monitor = mondrianServer.getMonitor();
	        final ServerInfo server = monitor.getServer();
	        return server;
		}
		return null;
	}
	
//	@GET
//	@Produces({"text/plain" })
//	@Path("/log/sql")
//	public String getSqlLog() {
//		return sqlWriter.toString();
//	}
//	
//	@GET
//	@Produces({"text/plain" })
//	@Path("/log/mdx")
//	public String getMdxLog() {
//		return mdxWriter.toString();
//	}
//	
//	@GET
//	@Produces({"text/plain" })
//	@Path("/log/profile")
//	public String getProfileLog() {
//		return profileWriter.toString();
//	}
//	
//	@GET
//	@Produces({"text/plain" })
//	@Path("/log/saiku")
//	public String getSaikuLog() {
//		return saikuWriter.toString();
//	}
	
	
}
