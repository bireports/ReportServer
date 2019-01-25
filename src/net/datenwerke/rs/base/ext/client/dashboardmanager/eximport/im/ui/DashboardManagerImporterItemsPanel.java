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
 
 
package net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.im.ui;

import java.util.List;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.IconProvider;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.im.DashboardManagerImportDao;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.im.dto.DashboardManagerImportConfigDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardNodeDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterItemsPanel;


public class DashboardManagerImporterItemsPanel extends ImporterItemsPanel<DashboardManagerImportConfigDto> {

	private final DashboardManagerImportDao dashboardImportDao;
	
	@Inject
	public DashboardManagerImporterItemsPanel(DashboardManagerImportDao dsImportDao) {
		super();
		
		/* store objects */
		this.dashboardImportDao = dsImportDao;
		
		/* load data */
		loadData();
	}


	private void loadData() {
		dashboardImportDao.loadTree(new RsAsyncCallback<List<ImportTreeModel>>(){
			@Override
			public void onSuccess(List<ImportTreeModel> roots) {
				buildTree(roots);
			}
		});
	}
	
	protected void configureTree() {
		super.configureTree();
		
		tree.setIconProvider(new IconProvider<ImportTreeModel>() {
			@Override
			public ImageResource getIcon(ImportTreeModel model) {
				if(DadgetNodeDto.class.getName().equals(model.getType()))
					return BaseIcon.MAP_O.toImageResource();
				if(DashboardNodeDto.class.getName().equals(model.getType()))
					return BaseIcon.ITEMS_DETAIL.toImageResource();
				return BaseIcon.FOLDER_O.toImageResource();
			}
		});
	}
}
