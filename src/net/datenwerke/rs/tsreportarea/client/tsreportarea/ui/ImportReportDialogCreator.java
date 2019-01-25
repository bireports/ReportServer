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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.ui;

import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportContainerDto;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.helper.reportselector.ReportSelectionDialog;
import net.datenwerke.rs.core.client.reportmanager.helper.reportselector.ReportSelectionDialog.ReportSelectionDialogEventHandler;
import net.datenwerke.rs.core.client.reportmanager.hookers.ReportCatalogRepositoryProvider;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportSelectionRepositoryProviderHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * 
 *
 */
public class ImportReportDialogCreator {

	private final Provider<ReportSelectionDialog> dialogProvider;
	
	@Inject
	public ImportReportDialogCreator(
		Provider<ReportSelectionDialog> dialogProvider
		){
		
		/* store obejcts */
		this.dialogProvider = dialogProvider;
	}
	
	public void displayDialog(final TsDiskMainComponent mainComponent){
		final ReportSelectionDialog window = dialogProvider.get();
		window.initRepositories(new ReportCatalogRepositoryProvider.Config() {
			@Override
			public boolean includeVariants() {
				return false;
			}
			
			@Override
			public boolean showCatalog() {
				return true;
			}
		});
		
		window.setHeadingText(TsFavoriteMessages.INSTANCE.importReportText());
		window.setHeaderIcon(BaseIcon.REPORT_ADD);
		window.setClosable(true);
		window.setOnEsc(true);
		
		window.setEventHandler(new ReportSelectionDialogEventHandler() {
			
			@Override
			public void handleDoubleClick(final ReportContainerDto report,
					ReportSelectionRepositoryProviderHook hooker, NativeEvent event, Object... info) {
				createMenu(report.getReport()).showAt(event.getClientX(), event.getClientY());
			}
			
			@Override
			public Menu getContextMenuFor(final ReportContainerDto report,
					ReportSelectionRepositoryProviderHook hooker, final Object... info) {
				return createMenu(report.getReport());
			}

			private Menu createMenu(final ReportDto report) {
				final Menu menu = new DwMenu();
				
				MenuItem addReference = new DwMenuItem(TsFavoriteMessages.INSTANCE.newReference(), BaseIcon.REPORT_LINK);
				menu.add(addReference);
				addReference.addSelectionHandler(new SelectionHandler<Item>() {
					@Override
					public void onSelection(SelectionEvent<Item> event) {
						mainComponent.importReport(report, false, true);
					}
				});
				
				if(report instanceof ReportVariantDto){
					MenuItem addCopy = new DwMenuItem(TsFavoriteMessages.INSTANCE.newCopy(), BaseIcon.REPORT_ADD);
					menu.add(addCopy);
					addCopy.addSelectionHandler(new SelectionHandler<Item>() {
						@Override
						public void onSelection(SelectionEvent<Item> event) {
							mainComponent.importReport(report, true, false);
						}
					});
				}
				
				return menu;
			}
		});
		
		window.show();
	}

}
