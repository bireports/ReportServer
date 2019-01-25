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
 
 
package net.datenwerke.rs.base.service.parameters.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.datenwerke.rs.core.service.parameters.entities.ParameterInstanceForJuel;

public class DateTimeParameterInstanceForJuel extends ParameterInstanceForJuel {
	
	private Date value;

	public void setValue(Date value) {
		this.value = value;
	}

	public Date getValue() {
		return value;
	}

	public String format(){
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //$NON-NLS-1$
		return format.format(value);
	}
	
	public String format(String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try{
			return format.format(value);
		} catch(Exception e){
			throw new RuntimeException("Could not format date " + value + " with pattern: " + pattern, e);
		}
		
	}

}
