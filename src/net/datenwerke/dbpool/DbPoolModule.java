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
 
 
package net.datenwerke.dbpool;

import net.datenwerke.dbpool.annotations.ConnectionPoolConfigFile;
import net.datenwerke.dbpool.annotations.PoolBoneCP;
import net.datenwerke.dbpool.annotations.PoolC3P0;
import net.datenwerke.dbpool.annotations.UseBoneCp;
import net.datenwerke.dbpool.annotations.UseConnectionPool;
import net.datenwerke.dbpool.config.ConnectionPoolConfig;
import net.datenwerke.dbpool.config.ConnectionPoolConfigFactory;
import net.datenwerke.dbpool.config.ConnectionPoolConfigImpl;
import net.datenwerke.dbpool.hooks.C3p0ConnectionHook;
import net.datenwerke.rs.utils.config.ConfigFileNotFoundException;
import net.datenwerke.rs.utils.config.ConfigService;

import org.apache.commons.configuration.HierarchicalConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class DbPoolModule extends AbstractModule {

	public static final String CONFIG_FILE = "datasources/pool.cf";
	
	
	@Override
	protected void configure() {
		bind(DbPoolService.class).annotatedWith(PoolBoneCP.class).to(BoneCpPoolServiceImpl.class).in(Singleton.class);
		bind(DbPoolService.class).annotatedWith(PoolC3P0.class).to(DbC3p0PoolServiceImpl.class).in(Singleton.class);
		
		bind(DbPoolService.class).to(MetaPoolService.class).in(Singleton.class);
		
		install(new FactoryModuleBuilder()
			.implement(ConnectionPoolConfig.class, ConnectionPoolConfigImpl.class)
			.build(ConnectionPoolConfigFactory.class));
		
		requestStaticInjection(C3p0ConnectionHook.class);
	}

	@Inject @Provides @ConnectionPoolConfigFile
	public HierarchicalConfiguration provideConfig(ConfigService service){
		try{
			return (HierarchicalConfiguration) service.getConfig(CONFIG_FILE);
		}catch(ConfigFileNotFoundException e){
			return null;
		}
	}
	
	@Provides @Inject @UseConnectionPool
	public Boolean provideUseConnectionPool(@ConnectionPoolConfigFile HierarchicalConfiguration config){
		try{
			if(null != config){
				String disablePool = config.getString("pool[@disable]", "false");
				return ! "true".equals(disablePool);
			}
		} catch(Exception ignore){}
		return true; 
	}
	
	@Inject @Provides @UseBoneCp
	public Boolean provideConfig(@ConnectionPoolConfigFile HierarchicalConfiguration config){
		try{
			if(null != config){
				String provider = config.getString("pool[@provider]", "c3p0");
				return "boneCp".equals(provider);
			}
		} catch(Exception ignore){}
		return false;
	}
}
