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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto.decorator;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterRangeDto;

/**
 * Dto Decorator for {@link FilterRangeDto}
 *
 */
public class FilterRangeDtoDec extends FilterRangeDto implements IdedDto {


	private static final long serialVersionUID = 1L;

	public FilterRangeDtoDec() {
		super();
	}

	public static List<FilterRangeDto> clone(List<FilterRangeDto> ranges) {
		if(null == ranges)
			return null;
		
		
		List<FilterRangeDto> cloneList = new ArrayList<FilterRangeDto>();
	
		for(FilterRangeDto range : ranges){
			FilterRangeDto clone = new FilterRangeDtoDec();
			
			clone.setRangeFrom(range.getRangeFrom());
			clone.setRangeTo(range.getRangeTo());
			
			cloneList.add(clone);
		}
		
		return cloneList;
	}
}
