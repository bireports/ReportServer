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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.decorator;

import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordEntryDto;

/**
 * Dto Decorator for {@link GridEditorRecordEntryDto}
 *
 */
public class GridEditorRecordEntryDtoDec extends GridEditorRecordEntryDto {


	private static final long serialVersionUID = 1L;

	public GridEditorRecordEntryDtoDec() {
		super();
	}

	public GridEditorRecordEntryDto copy() {
		GridEditorRecordEntryDto copy = new GridEditorRecordEntryDtoDec();
		
		for(PropertyAccessor pa : getPropertyAccessors())
			pa.setValue(copy, pa.getValue(this));

		return copy;
	}

	public Object getValue(){
		if(isEntryNull())
			return null;
		
		switch(getType()){
		case SqlTypes.BIT:
		case SqlTypes.BOOLEAN:
			return isBooleanValue();
		case SqlTypes.CHAR:
    	case SqlTypes.CLOB:
    	case SqlTypes.LONGVARCHAR:
    	case SqlTypes.VARCHAR:
    	case SqlTypes.NVARCHAR:
    	case SqlTypes.NCLOB:
    	case SqlTypes.NCHAR:
    	case SqlTypes.LONGNVARCHAR:
    	case SqlTypes.ROWID:
    	case SqlTypes.SQLXML:
    	case SqlTypes.OTHER:
    		return getStringValue();
    		
    	case SqlTypes.BIGINT:
    		return getLongValue();
    	case SqlTypes.INTEGER:
    	case SqlTypes.SMALLINT:
    	case SqlTypes.TINYINT:
    		return getIntValue();
    	case SqlTypes.NUMERIC:
    	case SqlTypes.DECIMAL:
    		return getDecimalValue();
    		
    	case SqlTypes.DATE:
    	case  SqlTypes.TIME:
    	case  SqlTypes.TIMESTAMP:
    		return getDateValue();
    		
    	case SqlTypes.DOUBLE:
    		return getDoubleValue();
    	case SqlTypes.FLOAT:
    		return getFloatValue();
    		
    	case SqlTypes.BLOB:
    	case SqlTypes.LONGVARBINARY:
    		break;
	
		}
		
		return null;
	}
}
