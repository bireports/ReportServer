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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.grideditor.client.grideditor.dto",
	generateDto2Poso=false,
	createDecorator=true
)
public class IntSelectionListEditor extends SelectionListEditor {

	@ExposeToClient
	private List<Integer> values = new ArrayList<Integer>();
	
	@ExposeToClient
	private Map<String,Integer> valueMap;
	
	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}
	
	public void addValue(Integer value){
		this.values.add(value);
	}

	public Map<String, Integer> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, Integer> valueMap) {
		this.valueMap = valueMap;
	}
	
	public void addEntry(String key, Integer value) {
		if(null== valueMap)
			valueMap = new TreeMap<String, Integer>();
		valueMap.put(key,value);
	}
	
	
	
}
