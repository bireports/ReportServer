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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import net.datenwerke.rs.computedcolumns.service.computedcolumns.annotations.AllowedFunctions;
import net.datenwerke.rs.utils.config.ConfigService;

public class ComputedColumnsModule extends AbstractModule {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	public static final String CONFIG_FILE = "dynamiclists/computedcolumn.cf";
	public static final String ALLOWED_FUNCTIONS_PROPERTIES = "functions.function";
	
	@Override
	protected void configure() {
		bind(ComputedColumnsStartup.class).asEagerSingleton();
	}

	@Inject @Provides @AllowedFunctions
	public List<String> providerAllowedFunctions(ConfigService configService){
		HierarchicalConfiguration config = null; 
		try {
			config = (HierarchicalConfiguration) configService.getConfig(ComputedColumnsModule.CONFIG_FILE);
			if(null == config)
				return new ArrayList<String>();
		} catch(Exception e) {
			logger.warn( e.getMessage(), e);
			return new ArrayList<String>();
		}
		
		List<Object> list = config.getList(ComputedColumnsModule.ALLOWED_FUNCTIONS_PROPERTIES);
		List<String> strList = new ArrayList<String>();
		
		for(Object o : list){
			if(o instanceof String){
				strList.add((String) o);
			}
		}
		
		return strList;
	}
}
