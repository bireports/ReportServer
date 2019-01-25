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
 
 
package net.datenwerke.rs.utils.properties;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.misc.PropertiesUtils;

/**
 * 
 *
 */
public class ApplicationPropertiesServiceImpl implements ApplicationPropertiesService {

	private final PropertiesUtils propertiesUtils;
	private final HookHandlerService hookHandlerService;
	
	private Supplier<Configuration> reportServerProperties;
	
	@Inject
	public ApplicationPropertiesServiceImpl(
		PropertiesUtils propertiesUtils, 
		HookHandlerService HookHandlerService
		){
		this.propertiesUtils = propertiesUtils;
		hookHandlerService = HookHandlerService;
		
		reportServerProperties = Suppliers.memoizeWithExpiration(new Supplier<Configuration>(){
			@Override
			public Configuration get() {
				return loadConfiguration();
			}
			
		}, 30, TimeUnit.SECONDS);
		
	}

	private Configuration loadConfiguration(){
		CompositeConfiguration cc = new CompositeConfiguration();

		/* Hooked properties */
		for(ApplicationPropertiesProviderHook p : hookHandlerService.getHookers(ApplicationPropertiesProviderHook.class)) {
			try{
				Configuration config = p.getConfig();
				if(null != config){
					cc.addConfiguration(config);
				}
			}catch(ConfigurationException e){
			}
		}

		try{ /* webapp override props */
			Configuration props = propertiesUtils.load(RS_PROPERTIES_OVERRIDE_FILE_NAME);
			cc.addConfiguration(props);
		} catch (ConfigurationException e) {
		}

		try{ /* webapp props */
			Configuration props = propertiesUtils.load(RS_PROPERTIES_FILE_NAME);
			cc.addConfiguration(props);
		} catch (ConfigurationException e) {
		}

		if(cc.getNumberOfConfigurations() == 0){
			throw new RuntimeException("Could not load application properties");
		}
		return cc;
	}
	
	@Override
	public Configuration getProperties() {
		return reportServerProperties.get();
	}
	
	@Override
	public String getString(String key){
		return getProperties().getString(key);
	}

	@Override
	public String getString(String key, String defaultValue){
		return getProperties().getString(key, defaultValue);
	}
	
	@Override
	public List<String> getList(String key){
		return (List)getProperties().getList(key);
	}
	
	@Override
	public Long getLong(String key) {
		return getProperties().getLong(key);
	}
	
	@Override
	public Long getLong(String key, Long defaultValue) {
		return getProperties().getLong(key, defaultValue);
	}

	@Override
	public Integer getInteger(String key) {
		return getProperties().getInt(key);
	}

	@Override
	public Integer getInteger(String key, int defaultValue) {
		return getProperties().getInt(key, defaultValue);
	}

	@Override
	public boolean getBoolean(String key) {
		return getProperties().getBoolean(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return getProperties().getBoolean(key, defaultValue);
	}

}
