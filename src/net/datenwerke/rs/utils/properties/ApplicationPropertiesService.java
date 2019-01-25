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

import org.apache.commons.configuration.Configuration;

public interface ApplicationPropertiesService {

	public final static String RS_PROPERTIES_FILE_NAME = "reportserver.properties";
	public final static String RS_PROPERTIES_OVERRIDE_FILE_NAME = "reportserver.overrides.properties";
	
	public Configuration getProperties();
	
	public String getString(String key);

	String getString(String key, String defaultValue);

	List<String> getList(String key);

	public Long getLong(String propertyExecuteUserId);

	public Integer getInteger(String key);
	public Integer getInteger(String key, int defaultValue);

	public boolean getBoolean(String key);
	public boolean getBoolean(String key, boolean defaultValue);

	public Long getLong(String key, Long defaultValue);
}
