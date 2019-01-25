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

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnFormatDto;

/**
 * Dto Decorator for {@link ColumnFormatDto}
 *
 */
public abstract class ColumnFormatDtoDec extends ColumnFormatDto implements IdedDto {


	private static final long serialVersionUID = 1L;

	public ColumnFormatDtoDec() {
		super();
	}

	public static ColumnFormatDto clone(ColumnFormatDto format) {
		if(null == format)
			return null;
		
		return ((ColumnFormatDtoDec)format).cloneFormat();
	}

	public abstract ColumnFormatDto cloneFormat();

}
