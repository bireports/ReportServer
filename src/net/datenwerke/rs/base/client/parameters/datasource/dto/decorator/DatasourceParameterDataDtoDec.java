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

}
