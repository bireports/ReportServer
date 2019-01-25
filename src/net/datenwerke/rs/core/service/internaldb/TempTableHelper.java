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
 
 
package net.datenwerke.rs.core.service.internaldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.dbpool.DbPoolService;
import net.datenwerke.dbpool.config.ConnectionPoolConfig;
import net.datenwerke.rs.utils.juel.SimpleJuel;

public class TempTableHelper {
	
	@Inject
	private static Provider<SimpleJuel> simpleJuel;
	
	@Inject
	private static DbPoolService dbPoolService;
	
	private final static String PRAEFIX = "RS_TMPTBL_";
	
	public static interface CleanupCallback {
		public void doCleanup(TempTableHelper helper, String table);
	}

	private int id;
	private int rev = 0;
	private int aliasnum = 0;
	private HashMap<String, Integer> aliasMap = new HashMap<String,Integer>();
	private HashMap<String, ConnectionPoolConfig> configMap = new HashMap<String, ConnectionPoolConfig>();
	private HashMap<String, CleanupCallback> cleanupCallbacks = new HashMap<String, TempTableHelper.CleanupCallback>();

	
	public TempTableHelper(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the table for an existing table alias. To generate a fresh table on a new alias use {@link #getTableName(ConnectionPoolConfig, String)}
	 * @param tableAlias
	 * @return
	 */
	public String getTableName(String tableAlias) {
		 Integer alid = aliasMap.get(tableAlias);
		 return PRAEFIX + id + "_" + alid + "_" + rev;
	}
	
	public synchronized String getTableName(ConnectionPoolConfig cpc, String tableAlias){
		if(!aliasMap.containsKey(tableAlias)){
			aliasnum++;
			aliasMap.put(tableAlias, aliasnum);
			configMap.put(tableAlias, cpc);
		}
		
		return PRAEFIX + id + "_" + aliasMap.get(tableAlias) + "_" + rev;
	}
	
	public Collection<String> getTableAliases(){
		return new HashSet<String>(aliasMap.keySet());
	}
	
	public void addCustomCleanup(String tableAlias, CleanupCallback callback){
		cleanupCallbacks.put(tableAlias, callback);
	}
	
	public synchronized void cleanup(){
		for(String tableAlias : aliasMap.keySet()){
			if(cleanupCallbacks.containsKey(tableAlias)){
				cleanupCallbacks.get(tableAlias).doCleanup(this, getTableName(configMap.get(tableAlias), tableAlias));
			}else{
				try {
					cleanup(tableAlias);
				} catch (SQLException e) {
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
				}
			}
		}
		
	}
	
	private void cleanup(String alias) throws SQLException, InterruptedException, ExecutionException {
		if(aliasMap.containsKey(alias)){
			ConnectionPoolConfig cpc = configMap.get(alias);
			try(Connection connection = (Connection) dbPoolService.getConnection(cpc).get()){
				String qry = "DROP TABLE " + PRAEFIX + id + "_" + aliasMap.get(alias) + "_" + rev ;
				PreparedStatement stmt = connection.prepareStatement(qry);
				stmt.executeUpdate();
			}
		}
	}

	public synchronized String processQuery(String query) {
		SimpleJuel queryParser = simpleJuel.get();
		
		for(String alias : aliasMap.keySet())
			queryParser.addReplacement(alias, getTableName(configMap.get(alias), alias));

		return queryParser.parse(query);
	}

	public void incrementRevision() {
		rev++;
	}

	
}
