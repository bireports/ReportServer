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
 
 
package net.datenwerke.rs.base.client.reportengines.table.previewenhancer;

import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.hooks.TableReportPreviewCellEnhancerHook;
import net.datenwerke.rs.base.client.reportengines.table.ui.TableReportPreviewView;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class GeoLocationEnhancer implements TableReportPreviewCellEnhancerHook {

	final private UtilsUIService utilsService;
	
	@Inject
	public GeoLocationEnhancer(
		UtilsUIService utilsService
		){
		
		/* store objects */
		this.utilsService = utilsService;
	}
	
	@Override
	public boolean consumes(TableReportDto report, ColumnDto column, String value, String rawValue) {
		return null != column.getSemanticType() && "geolocation".equals(column.getSemanticType().toLowerCase());
	}

	@Override
	public boolean enhanceMenu(TableReportPreviewView tableReportPreviewView, Menu menu, TableReportDto report, ColumnDto column, final String value, String rawValue) {
		MenuItem lookupItem = new DwMenuItem(ReportexecutorMessages.INSTANCE.geolocationLookupText());
		menu.add(lookupItem);
		lookupItem.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				utilsService.redirectInPopup("http://maps.google.de?q=" + value);
			}
		});
		
		return false;
	}

}
