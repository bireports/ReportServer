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
public class LongSelectionListEditor extends SelectionListEditor {

	@ExposeToClient
	private List<Long> values = new ArrayList<Long>();
	
	@ExposeToClient
	private Map<String,Long> valueMap;
	
	public List<Long> getValues() {
		return values;
	}

	public void setValues(List<Long> values) {
		this.values = values;
	}
	
	public void addValue(Long value){
		this.values.add(value);
	}

	public Map<String, Long> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, Long> valueMap) {
		this.valueMap = valueMap;
	}
	
	public void addEntry(String key, Long value) {
		if(null== valueMap)
			valueMap = new TreeMap<String, Long>();
		valueMap.put(key,value);
	}
	
	
	
}
