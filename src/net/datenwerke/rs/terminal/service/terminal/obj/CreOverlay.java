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
 
 
package net.datenwerke.rs.terminal.service.terminal.obj;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.terminal.client.terminal.dto"
)
public class CreOverlay extends CommandResultExtension {

	@ExposeToClient
	private String name;
	
	@ExposeToClient
	private String text;
	
	@ExposeToClient
	private Map<String, String> cssProperties = new HashMap<String, String>();
	
	@ExposeToClient
	private boolean remove = false;

	public CreOverlay(){
	}

	public CreOverlay(String name, boolean remove){
		this.name = name;
		this.setRemove(remove);
	}
	
	public CreOverlay(String name, String text){
		this.name = name;
		this.text = text;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getCssProperties() {
		return cssProperties;
	}

	public void setCssProperties(Map<String, String> cssProperties) {
		this.cssProperties = cssProperties;
	}
	
	public void addProperty(String key, String value){
		cssProperties.put(key, value);
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public boolean isRemove() {
		return remove;
	}
	
}
