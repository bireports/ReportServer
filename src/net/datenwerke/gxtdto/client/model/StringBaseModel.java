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
 
 
package net.datenwerke.gxtdto.client.model;

import net.datenwerke.gxtdto.client.dtomanager.HasValueProviderByPath;
import net.datenwerke.gxtdto.client.model.pa.StringBaseModelPa;

import com.sencha.gxt.core.client.ValueProvider;




/**
 * A wrapper object to wrap Strings in a {@link BaseModel} e.g. for adding them to a store.
 *
 */
public class StringBaseModel implements DwModel, HasValueProviderByPath<StringBaseModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1483262026820046726L;

	private String value;
	
	public StringBaseModel() {
	}
	
	public StringBaseModel(String value) {
		setValue(value);
	}

	public void setValue(String value){
		this.value = value; 
	}
	
	public String getValue(){
		return value; 
	}
	
	public ValueProvider<StringBaseModel, ?> getValueProviderByPath(String path) {
		if("value".equals(path)){
			return StringBaseModelPa.INSTANCE.value();
		}
		
		return null;
	}

}
