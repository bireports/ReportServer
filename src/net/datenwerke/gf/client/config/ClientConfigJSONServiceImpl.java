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
 
 
package net.datenwerke.gf.client.config;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Singleton;

public class ClientConfigJSONServiceImpl implements ClientConfigJSONService {

	private JSONObject config;

	@Override
	public void setJSONConfig(String json) {
		try{
			config = (JSONObject)JSONParser.parseStrict(json);
		} catch(Exception e){
			GWT.log(e.getMessage());
		}
	}
	
	@Override
	public boolean getBoolean(String property, boolean defaultValue){
		if(null == config)
			return defaultValue;
		
		try{
			JSONObject obj = getBaseObject(property);
			if(property.contains("."))
				property = property.substring(property.lastIndexOf(".")+1);
			JSONValue value = obj.get(property);
			if(value instanceof JSONBoolean)
				return ((JSONBoolean)value).booleanValue();
			if(value instanceof JSONString)
				return ((JSONString)value).stringValue().toLowerCase().equals("true");
			return defaultValue;
		} catch(Exception e){
			return defaultValue;
		}
	}

	@Override
	public String getString(String property, String defaultValue) {
		if(null == config)
			return defaultValue;
		
		try{
			JSONObject obj = getBaseObject(property);
			if(property.contains("."))
				property = property.substring(property.lastIndexOf(".")+1);
			JSONValue value = obj.get(property);
			if(value instanceof JSONString)
				return ((JSONString)value).stringValue();
			return defaultValue;
		} catch(Exception e){
			return defaultValue;
		}
	}
	
	private JSONObject getBaseObject(String property) {
		property = "configuration." + property;
		
		JSONObject obj = config;
		while(property.contains(".")){
			int index = property.indexOf(".");
			String key = property.substring(0,index);
			property = property.substring(index+1);
			
			obj = (JSONObject) obj.get(key);
		}
		
		return obj;
	}
}
