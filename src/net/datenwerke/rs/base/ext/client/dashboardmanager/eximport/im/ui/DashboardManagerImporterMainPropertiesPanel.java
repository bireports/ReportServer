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

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.simpleform.SFFCGenericTreeNode;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.im.dto.DashboardManagerImportConfigDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardFolderDto;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardTreeFolders;
import net.datenwerke.rs.eximport.client.eximport.im.exceptions.NotProperlyConfiguredException;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterMainPropertiesPanel;


public class DashboardManagerImporterMainPropertiesPanel extends
		ImporterMainPropertiesPanel<DashboardManagerImportConfigDto> {

	private final Provider<UITree> treeProvider;
	
	protected String parentKey;
	protected String defaultDatasource;
	
	@Inject
	public DashboardManagerImporterMainPropertiesPanel(
		@DashboardTreeFolders Provider<UITree> treeProvider
		) {

		/* store objects */
		this.treeProvider = treeProvider;
		
		/* init */
		initializeUI();
	}

	@Override
	public void populateConfig(DashboardManagerImportConfigDto config) throws NotProperlyConfiguredException {
		super.populateConfig(config);
		
		DashboardFolderDto parent = (DashboardFolderDto) form.getValue(parentKey);
		config.setParent(parent);
	}
	
	@Override
	public void validateConfig(DashboardManagerImportConfigDto config)
			throws NotProperlyConfiguredException {
		if(null == config.getParent() && ! config.getConfigs().isEmpty())
			throw new NotProperlyConfiguredException(DashboardMessages.INSTANCE.importConfigFailureNoParent());
	}

	@Override
	protected void configureForm() {
		super.configureForm();

		parentKey = form.addField(DashboardFolderDto.class, DashboardMessages.INSTANCE.importWhereTo(), new SFFCGenericTreeNode(){
			@Override
			public UITree getTreeForPopup() {
				return treeProvider.get();
			}
		});
		
	}

	@Override
	protected String getDescription() {
		return DashboardMessages.INSTANCE.importMainPropertiesDescription();
	}

	@Override
	protected String getHeadline() {
		return DashboardMessages.INSTANCE.importMainPropertiesHeadline();
	}
}
