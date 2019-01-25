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

import java.util.List;

import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;

/**
 * 
 *
 */
public class ExcludeValuesFilterAspect extends AbstractSingleFilterAspect{
	
	public ExcludeValuesFilterAspect(TableReportDto report, ColumnDto column, String executeToken) {
		super(FilterMessages.INSTANCE.excludeTitle(), report, column, FilterType.Exclude, executeToken); 
	}	
	
	@Override
	protected List<String> getValues(FilterDto filter) {
		return filter.getExcludeValues();
	}
	
	@Override
	protected SelectionPanel<StringBaseModel> createSelectionPanel(ColumnDto column) {
		return new SingleSelectionPanel(this, column);
	}

	@Override
	protected void setValues(FilterDto filter, List<String> newValues) {
		filter.setExcludeValues(newValues);
	}
}
