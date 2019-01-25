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
 
 
package net.datenwerke.dbpool.config;

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import net.datenwerke.dbpool.annotations.ConnectionPoolConfigFile;
import net.datenwerke.rs.utils.misc.Nullable;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ConnectionPoolConfigImpl implements ConnectionPoolConfig {

	protected final Long id;
	
	protected String driver;

	protected Properties properties;
	
	protected String username;
	protected String password = "";
	protected String jdbcUrl;
	
	protected boolean mightChange = true;
	
	protected boolean poolable;

	private Date lastUpdated;

	@Inject
	public ConnectionPoolConfigImpl(
		@Nullable @ConnectionPoolConfigFile HierarchicalConfiguration config,
		@Nullable @Assisted Long id
		){
		this.id = id;
		this.setPoolable(null != id);
		
		properties = new Properties();
		
		/* try to set default values */
		if(null != config){
			try{
				SubnodeConfiguration defaultCon = config.configurationAt("pool.defaultconfig");
				if(null != defaultCon){
					Iterator<String> keys = defaultCon.getKeys();
					while(keys.hasNext()){
						String key = keys.next();
						properties.setProperty(key, defaultCon.getString(key));
					}
				}
			} catch(Exception ignore){}
			if(null == properties)
				properties = new Properties();
			
			try{
				SubnodeConfiguration specCon = config.configurationAt("pool.pool" + id);
				if(null != specCon){
					Iterator<String> keys = specCon.getKeys();
					while(keys.hasNext()){
						String key = keys.next();
						properties.setProperty(key, specCon.getString(key));
					}
				}
			} catch(Exception ignore){}
			
		}
	}
	


	public Long getId() {
		return id;
	}

	@Override
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setMightChange(boolean mightChange) {
		this.mightChange = mightChange;
	}

	@Override
	public boolean isMightChange() {
		return mightChange;
	}
	
	public void setPoolable(boolean poolable) {
		this.poolable = poolable;
	}

	@Override
	public boolean isPoolable() {
		return poolable;
	}

	@Override
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ConnectionPoolConfigImpl))
			return false;
		if(null == id)
			return false;
		return id.equals(((ConnectionPoolConfigImpl)obj).id);
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public Properties getProperties(){
		return properties;
	}
	
	public void setProperty(String key, int i) {
		properties.setProperty(key, String.valueOf(i));
	}
	
	public void setProperty(String key, String value){
		properties.setProperty(key, value);
	}

}
