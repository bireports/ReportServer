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
 
 
package net.datenwerke.rs.base.ext.client.reportmanager.eximport.im.ui;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.simpleform.SFFCGenericTreeNode;
import net.datenwerke.rs.base.ext.client.reportmanager.eximport.im.dto.ReportManagerImportConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.AbstractReportManagerNodeDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.rs.core.client.reportmanager.provider.annotations.ReportManagerTreeFoldersAndReports;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportItemConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.exceptions.NotProperlyConfiguredException;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportNodeConfigDto;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterMainPropertiesPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ReportImporterMainPropertiesPanel extends
		ImporterMainPropertiesPanel<ReportManagerImportConfigDto> {

	private final Provider<UITree> treeProvider;
	
	protected String parentKey;
	private String removeKeyFieldsKey;
	
	@Inject
	public ReportImporterMainPropertiesPanel(
		@ReportManagerTreeFoldersAndReports Provider<UITree> treeProvider
		) {
		
		/* store objects */
		this.treeProvider = treeProvider;
		
		/* init */
		initializeUI();
	}

	public void populateConfig(ReportManagerImportConfigDto config) throws NotProperlyConfiguredException {
		super.populateConfig(config);
		
		AbstractReportManagerNodeDto parent = (AbstractReportManagerNodeDto) form.getValue(parentKey);
		config.setParent(parent);
		
		boolean removeKeys = (Boolean) form.getValue(removeKeyFieldsKey);
		config.setRemoveKey(removeKeys);
	}
	
	@Override
	public void validateConfig(ReportManagerImportConfigDto config)
			throws NotProperlyConfiguredException {
		if(null == config.getParent() && ! config.getConfigs().isEmpty())
			throw new NotProperlyConfiguredException(ReportmanagerMessages.INSTANCE.importConfigFailureNoParent());
		
		if(null != config.getParent()){
			if(config.getParent() instanceof ReportVariantDto)
				throw new NotProperlyConfiguredException(ReportmanagerMessages.INSTANCE.importConfigFailureVariantParent());
			
			if(config.getParent() instanceof ReportDto){
				for(ImportItemConfigDto itemConfig : config.getConfigs()){
					TreeImportNodeConfigDto treeItemConfig = (TreeImportNodeConfigDto) itemConfig;
					if(ReportFolderDto.class.getName().equals(treeItemConfig.getModel().getType())){
						throw new NotProperlyConfiguredException(ReportmanagerMessages.INSTANCE.importConfigFailureFolderBeneathReport());
					}
				}
			}
		}
	}
	

	@Override
	protected void configureForm() {
		super.configureForm();

		parentKey = form.addField(AbstractReportManagerNodeDto.class, ReportmanagerMessages.INSTANCE.importWhereTo(), new SFFCGenericTreeNode(){
			@Override
			public UITree getTreeForPopup() {
				return treeProvider.get();
			}
		});
		
		removeKeyFieldsKey = form.addField(Boolean.class, ReportmanagerMessages.INSTANCE.importRemoveKeyFieldLabel());
	}
	
	@Override
	protected String getDescription() {
		return ReportmanagerMessages.INSTANCE.importMainPropertiesDescription();
	}

	@Override
	protected String getHeadline() {
		return ReportmanagerMessages.INSTANCE.importMainPropertiesHeadline();
	}
	

}
