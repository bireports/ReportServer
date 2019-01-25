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
 
 
package net.datenwerke.rs.base.client.parameters.datasource.dto.decorator;

import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDataDto;

/**
 * Dto Decorator for {@link DatasourceParameterDataDto}
 *
 */
public class DatasourceParameterDataDtoDec extends DatasourceParameterDataDto implements IdedDto {


	private static final long serialVersionUID = 1L;

	public DatasourceParameterDataDtoDec() {
		super();
	}
	
	public static DatasourceParameterDataDto cloneDataObject(
			DatasourceParameterDataDto dataObject) {
		DatasourceParameterDataDtoDec clone = new DatasourceParameterDataDtoDec();
		clone.setKey(dataObject.getKey());
		clone.setValue(dataObject.getValue());
		return clone;
	}
	
	@Override
	public int hashCode() {
		if(null == getKey())
			return super.hashCode();
		return getKey().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof DatasourceParameterDataDto))
			return false;

		
		DatasourceParameterDataDto data = (DatasourceParameterDataDto) obj;
		
		if(null == getKey() && null != data.getKey())
			return false;
			
		if(null == getValue() && null != data.getValue())
			return false;
		
		if(null != getKey() && ! getKey().equals(data.getKey()))
			return false;
			
		if(null != getValue() && ! getValue().equals(data.getValue()))
			return false;
		
		return true;
	}



}
