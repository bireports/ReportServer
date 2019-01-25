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
 
 
package net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.ui;

import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.base.client.datasources.dto.CsvDatasourceDto;
import net.datenwerke.rs.base.client.datasources.dto.DatabaseDatasourceDto;
import net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.DatasourceManagerImportDao;
import net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.dto.DatasourceManagerImportConfigDto;
import net.datenwerke.rs.birt.client.datasources.dto.BirtReportDatasourceDefinitionDto;
import net.datenwerke.rs.dsbundle.client.dsbundle.dto.DatabaseBundleDto;
import net.datenwerke.rs.incubator.client.scriptdatasource.dto.ScriptDatasourceDto;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterItemsPanel;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.IconProvider;

public class DatasourceImporterItemsPanel extends ImporterItemsPanel<DatasourceManagerImportConfigDto> {

	private final DatasourceManagerImportDao dsImportDao;
	
	@Inject
	public DatasourceImporterItemsPanel(DatasourceManagerImportDao dsImportDao) {
		super();
		
		/* store objects */
		this.dsImportDao = dsImportDao;
		
		/* load data */
		loadData();
	}


	private void loadData() {
		dsImportDao.loadTree(new RsAsyncCallback<List<ImportTreeModel>>(){
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
				if(DatabaseDatasourceDto.class.getName().equals(model.getType()))
					return BaseIcon.DATABASE.toImageResource();
				if (MondrianDatasourceDto.class.getName().equals(model.getType()))
					return BaseIcon.CUBES.toImageResource();
				if (CsvDatasourceDto.class.getName().equals(model.getType()))
					return BaseIcon.FILE_TEXT_O.toImageResource();
				if (ScriptDatasourceDto.class.getName().equals(model.getType()))
					return BaseIcon.FILE_CODE_O.toImageResource();
				if (DatabaseBundleDto.class.getName().equals(model.getType()))
					return BaseIcon.RANDOM.toImageResource();
				if (BirtReportDatasourceDefinitionDto.class.getName().equals(model.getType()))
					return BaseIcon.FILE.toImageResource();
				return BaseIcon.FOLDER_O.toImageResource();
			}
		});
	}
}
