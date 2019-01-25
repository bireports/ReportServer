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
 
 
package net.datenwerke.gxtdto.client.model;


import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper object to wrap Strings in a {@link BaseModel} e.g. for adding them to a store.
 *
 */
public class ListStringBaseModel implements DwModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2590606516954006171L;
	
	private List<String> value;
	
	public ListStringBaseModel() {
	}
	
	public ListStringBaseModel(List<String> value) {
		this();
		setValue(value);
	}
	
	public ListStringBaseModel(Object[] row) {
		this();
		ArrayList<String> list = new ArrayList<String>();
		for(Object cell : row)
			list.add(null == cell ? null : cell.toString());
		setValue(list);
	}

	public void setValue(List<String> value){
		this.value = value; 
	}
	
	public List<String> getValue(){
		return value;
	}


}
