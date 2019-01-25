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
 
 
package net.datenwerke.rs.scripting.service.scripting.extensions;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.scripting.client.scripting.dto"
)
public class DisplayCondition {

	@ExposeToClient
	private String propertyName;
	
	@ExposeToClient
	private String value;
	
	@ExposeToClient
	private DisplayConditionType type;

	public DisplayCondition() {
	}
	
	public DisplayCondition(String propertyName, String value) {
		this.propertyName = propertyName;
		this.value = value;
		this.type = DisplayConditionType.EQUALS;
	}
	
	public DisplayCondition(String propertyName, String value, DisplayConditionType type) {
		this.propertyName = propertyName;
		this.value = value;
		this.type = type;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DisplayConditionType getType() {
		return type;
	}

	public void setType(DisplayConditionType type) {
		this.type = type;
	}
	
	
}
