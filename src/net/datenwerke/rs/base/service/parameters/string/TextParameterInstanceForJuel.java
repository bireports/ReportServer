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
 
 
package net.datenwerke.rs.base.service.parameters.string;

import java.math.BigDecimal;

import net.datenwerke.rs.core.service.parameters.entities.Datatype;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstanceForJuel;

public class TextParameterInstanceForJuel extends
		ParameterInstanceForJuel {

	private String value;
	private Datatype returnType;

	public void setValue(String value) {
		this.value = value;
	}

	public Object getValue() {
		switch(returnType){
		case String:
			return value;
		case Integer:
			return Integer.parseInt(value);
		case Long:
			return Long.valueOf(value);
		case BigDecimal:
			return new BigDecimal(value);
		case Float:
			return Float.valueOf(value);
		case Double:
			return Double.valueOf(value);
		default:
			return value;
		}
	}
	
	public String asString(){
		return value;
	}
	
	public int asInteger(){
		return Integer.parseInt(value);
	}
	
	public double asDouble(){
		return Double.valueOf(value);
	}
	
	public float asFloat(){
		return Float.valueOf(value);
	}
	
	public BigDecimal asBigDecimal(){
		return new BigDecimal(value);
	}
	
	public Long asLong(){
		return Long.valueOf(value);
	}

	public void setReturnType(Datatype returnType) {
		this.returnType = returnType;
	}

	public Datatype getReturnType() {
		return returnType;
	}
	
	

}
