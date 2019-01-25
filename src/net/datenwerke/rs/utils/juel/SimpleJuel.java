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
 
 
package net.datenwerke.rs.utils.juel;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

/**
 * A simple template parser.
 * 
 *
 */
public class SimpleJuel {

	private final JuelService juelService;
	private final Map<String, VariableAssignment> replacementMap;
	
	@Inject
	public SimpleJuel(
		JuelService juelService
		){
		
		this.juelService = juelService;
		replacementMap = new HashMap<String, VariableAssignment>();
	}
	
	public String parse(String template){
		Object result = parseAsObject(template);
		return null == result ? null : result.toString();
	}
	
	public Object parseAsObject(String template){
		if(null == template)
			return null;
		
	    return juelService.evaluate(replacementMap, template);
	}
	
	public void addReplacement(String key, Object value) {
		replacementMap.put(key, new VariableAssignment(value));
	}
	
	public void addReplacement(String key, Object value, Class<?> type) {
		replacementMap.put(key, new VariableAssignment(value, type));
	}
	
}
