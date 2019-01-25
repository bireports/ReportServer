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
 
 
package net.datenwerke.rs.base.client.reportengines.table.helpers.filter;

import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;

/**
 * 
 *
 */
public class SingleSelectionPanel extends SelectionPanel<StringBaseModel> {

	public SingleSelectionPanel(AbstractFilterAspect<StringBaseModel> filterAspect,
			ColumnDto column) {
		super(filterAspect, column);
	}

	
	@Override
	protected GridView<StringBaseModel> initializeGridView() {
		return new SingleColumnGridView(store, column, this);
	}

	@Override
	protected TextView<StringBaseModel> initializeTextView() {
		return new SingleColumnTextView(store, column, this, tabPanel);
	}
	
	@Override
	public void insertElement(StringBaseModel value){
		store.add(value);
	}

	@Override
	public void validate() {
		if(sqlTypes.isFloatingPoint(column.getType()) ){
			tryParseText();
			for(StringBaseModel item : store.getAll()){
				if(null != item && null != item.getValue() && ! item.getValue().trim().startsWith("${")){
					throw new IllegalArgumentException(FilterMessages.INSTANCE.noFloatEqualFilterWarning());
				}
			}
		}
	}
	
}
