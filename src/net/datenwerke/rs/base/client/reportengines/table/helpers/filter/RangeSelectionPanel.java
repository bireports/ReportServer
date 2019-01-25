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
 
 
package net.datenwerke.rs.base.client.reportengines.table.helpers.filter;

import java.util.List;

import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterRangeDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.FilterRangeDtoDec;

/**
 * 
 *
 */
public class RangeSelectionPanel extends SelectionPanel<FilterRangeDto> {

	public RangeSelectionPanel(AbstractFilterAspect<FilterRangeDto> filterAspect,
			ColumnDto column) {
		super(filterAspect, column);
	}
	
	@Override
	protected GridView<FilterRangeDto> initializeGridView() {
		return new TwoColumnGridView(store, column, this);
	}

	@Override
	protected TextView<FilterRangeDto> initializeTextView() {
		return new TwoColumnTextView(store, column, this, tabPanel);
	}
	
	@Override
	public void insertElement(StringBaseModel value){
		List<FilterRangeDto> storedModels = store.getAll();
		FilterRangeDto last = null;
		if(storedModels.size() > 0)
			last = (FilterRangeDto) storedModels.get(storedModels.size() - 1);
		if(null != last && null == last.getRangeTo()){
			last.setRangeTo(filterService.getStringValue(value.getValue(), column.getType()));
			store.update(last);
		}else{
			FilterRangeDto range = new FilterRangeDtoDec();
			range.setRangeFrom(filterService.getStringValue(value.getValue(), column.getType()));
			store.add(range);
		}
	}

}
