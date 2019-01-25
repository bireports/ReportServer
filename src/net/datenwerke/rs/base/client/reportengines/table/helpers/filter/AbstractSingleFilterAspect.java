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

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Component;

/**
 * 
 *
 */
public abstract class AbstractSingleFilterAspect extends AbstractFilterAspect<StringBaseModel> {

	public AbstractSingleFilterAspect(String titleString, TableReportDto report,
			ColumnDto column, FilterType type, String executeToken) {
		super(titleString, report, column, type, executeToken);
	}

	@Override
	public void storeConfiguration() {
		ListStore<StringBaseModel> store = this.selectionPanel.getStore();

		List<String> newValues = new ArrayList<String>();
		for(StringBaseModel m : store.getAll())
			newValues.add(filterService.getStringValue(m.getValue(), column.getType()));
		
		setValues(column.getFilter(), newValues);
	}
	
	abstract protected void setValues(FilterDto filter, List<String> newValues);

	abstract protected List<String> getValues(FilterDto filter);

	@Override
	public void loadConfiguration(FilterDto filter) {
		/* stop updates */
		selectionPanel.setFireUpdates(false);
		
		/* insert data into selection panel */
		ListStore<StringBaseModel> store = this.selectionPanel.getStore();
		for(String v : getValues(filter)){
			StringBaseModel value = new StringBaseModel();
			value.setValue(v);
			store.add(value);
		}

		/* resume updates */
		selectionPanel.setFireUpdates(true);
	}
	
}
