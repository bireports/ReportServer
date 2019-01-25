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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.ServletContext;

public class ConfigDirServiceImpl implements ConfigDirService {
	
	public final static String SYSTEM_PROPERTY_NAME = "rs.configdir";
	
	private String configPath;
	private ServletContext servletContext;
	
	
	@Inject
	public ConfigDirServiceImpl(ServletContext servletContext) {
		this.servletContext = servletContext;
		
		this.configPath = getConfigPath();
	}
	
	private String getConfigPath(){
		String ctxpath = null;
		if(null != servletContext)
			ctxpath = servletContext.getContextPath().replaceAll("\\W", ".");
		String prop = System.getProperty(SYSTEM_PROPERTY_NAME + "." + ctxpath);
		
		if(null == prop){
			prop = System.getProperty(SYSTEM_PROPERTY_NAME);
		}
		
		return prop;
	}
	
	@Override
	public File getConfigDir(){
		if(!isEnabled())
			return null;
		
		return new File(configPath);
	}

	@Override
	public boolean isEnabled() {
		return null != configPath;
	}

}
