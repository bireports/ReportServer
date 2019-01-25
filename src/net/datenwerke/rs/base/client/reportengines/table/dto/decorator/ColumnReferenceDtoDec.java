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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto.decorator;

import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnReferenceDto;

/**
 * Dto Decorator for {@link ColumnReferenceDto}
 *
 */
public class ColumnReferenceDtoDec extends ColumnReferenceDto {


	private static final long serialVersionUID = 1L;

	public ColumnReferenceDtoDec() {
		super();
	}

	public ColumnDto cloneColumnForSelection() {
		ColumnReferenceDtoDec clone = new ColumnReferenceDtoDec();
		
		copyPropertiesTo(clone);
		
		return clone;
	}

	public void copyPropertiesTo(ColumnDto clone) {
		super.copyPropertiesTo(clone);
		((ColumnReferenceDtoDec)clone).setReference(getReference());
	}
	

	@Override
	public Integer getType() {
		if(null != getReference())
			return getReference().getType();
		return SqlTypes.VARCHAR;
	}
	
	@Override
	public String getName() {
		if(null == getReference())
			return "";
		return getReference().getName();
	}
	
	@Override
	public String getDescription() {
		if(null == getReference())
			return "";
		return getReference().getDescription();
	}
}
