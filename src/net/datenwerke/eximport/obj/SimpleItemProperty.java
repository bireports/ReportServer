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
 
 
package net.datenwerke.eximport.obj;

import net.datenwerke.eximport.ExImportHelperService;
import nu.xom.Element;


/**
 * 
 *
 */
public class SimpleItemProperty extends ItemProperty {

	protected final String value;
	
	public SimpleItemProperty(String name, Class<?> type, String value, Element el) {
		super(name, type, el);
		
		/* store objects */
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public Boolean getBoolean(){
		return ExImportHelperService.XML_TRUE.equals(value);
	}
	
	public Integer getInteger(){
		return Integer.parseInt(value);
	}
	
	public Double getDouble(){
		return Double.valueOf(value);
	}
	
	public Long getLong(){
		return Long.valueOf(value);
	}
	
	
}
