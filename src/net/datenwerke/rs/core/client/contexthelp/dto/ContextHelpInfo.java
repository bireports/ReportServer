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
 
 
package net.datenwerke.rs.core.client.contexthelp.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ContextHelpInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5137130641578808198L;

	private String id;
	
	private String title;
	
	private Map<String, ContextHelpData<? extends Object>> dataMap = new HashMap<String, ContextHelpData<? extends Object>>();
	
	public ContextHelpInfo(){}
	
	public ContextHelpInfo(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setDataMap(Map<String, ContextHelpData<? extends Object>> dataMap) {
		this.dataMap = dataMap;
	}
	
	public Map<String, ContextHelpData<? extends Object>> getDataMap() {
		return dataMap;
	}
	
	public void addData(String key, String data){
		this.dataMap.put(key, new ContextHelpStringData(data));
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
