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
 
 
package net.datenwerke.gf.service.theme;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private String type = "default";
	private String additionalCss = "";
	private Map<String, String> colorNames = new HashMap<String, String>();
	private Map<String, String> colorMap = new HashMap<String, String>();
	private Map<String, String> colorMapRefName = new HashMap<String, String>();
	
	public void addColorName(String name, String color){
		colorNames.put(name, color);
	}
	
	public void addColorMapByColor(String name, String color){
		colorMap.put(name, color);
	}
	
	public void addColorMapByReference(String name, String ref){
		if(colorNames.containsKey(ref))
			colorMap.put(name, colorNames.get(ref));
	}
	
	public void addColorMapByName(String name, String refName){
		colorMapRefName.put(name, refName);
	}
	
	public String getColorFor(String name){
		if(colorMap.containsKey(name))
			return colorMap.get(name);
		if(colorMapRefName.containsKey(name) && colorMap.containsKey(colorMapRefName.get(name)))
			return colorMap.get(colorMapRefName.get(name));
		
		logger.debug("Could not find color for " + name);
		
		return null;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public String getAdditionalCss() {
		return additionalCss;
	}
	
	public void setAdditionalCss(String additionalCss) {
		this.additionalCss = additionalCss;
	}
	
}
