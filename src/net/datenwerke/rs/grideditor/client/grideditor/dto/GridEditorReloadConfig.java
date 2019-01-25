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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.datenwerke.gxtdto.client.model.DwModel;

import com.sencha.gxt.data.shared.SortInfo;

public class GridEditorReloadConfig implements DwModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4950625065910601657L;
	
	private int pagesize;
	private int pagenumber;
	private SortInfo sortInfo;
	
	private Map<String, String> filterMap = new HashMap<String, String>();
	private Set<String> csFilter = new HashSet<String>();
	
	public int getPagesize() {
		return pagesize;
	}
	
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	
	public int getPagenumber() {
		return pagenumber;
	}
	
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}
	
	public SortInfo getSortInfo() {
		return sortInfo;
	}
	
	public void setSortInfo(SortInfo sortInfo) {
		this.sortInfo = sortInfo;
	}
	
	public Map<String, String> getFilterMap() {
		return filterMap;
	}
	
	public void setFilterMap(Map<String, String> filterMap) {
		this.filterMap = filterMap;
	}
	
	public void clearFilters(){
		filterMap.clear();
		csFilter.clear();
	}

	public void addFilter(String col, String v, boolean caseSensitive) {
		filterMap.put(col, v);
		if(caseSensitive)
			csFilter.add(col);
	}
	
	public boolean isCaseSensitive(String col){
		return csFilter.contains(col);
	}
	

}
