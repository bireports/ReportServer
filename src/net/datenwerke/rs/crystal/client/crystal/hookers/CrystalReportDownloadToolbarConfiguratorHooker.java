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
 
 
package net.datenwerke.rs.crystal.client.crystal.hookers;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.FormView;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;
import net.datenwerke.rs.crystal.client.crystal.locale.CrystalMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class CrystalReportDownloadToolbarConfiguratorHooker implements MainPanelViewToolbarConfiguratorHook {

	private final ToolbarService toolbarUtils;
	private final UtilsUIService utilsUIService;
	
	@Inject
	public CrystalReportDownloadToolbarConfiguratorHooker(
		ToolbarService toolbarUtils,
		UtilsUIService utilsUIService
		){
		
		/* store objects */
		this.toolbarUtils = toolbarUtils;
		this.utilsUIService = utilsUIService;
	}
	
	
	public void mainPanelViewToolbarConfiguratorHook_addLeft(
			MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		if(! (selectedNode instanceof CrystalReportDto))
			return;
		if(! (view instanceof FormView))
			return;
		
		final CrystalReportDto report = (CrystalReportDto) selectedNode;
		
		/* add parameter */
		DwTextButton createPreviewBtn = toolbarUtils.createSmallButtonLeft(CrystalMessages.INSTANCE.downloadReportToolbarButtonText(), BaseIcon.FILE_PICTURE_O);
		createPreviewBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				String id = String.valueOf(report.getId());
				String url = GWT.getModuleBaseURL() + "CrystalRptDownload?id=" + id; //$NON-NLS-1$
				ClientDownloadHelper.triggerDownload(url);
			}
		});
		
		toolbar.add(createPreviewBtn);
	}

	public void mainPanelViewToolbarConfiguratorHook_addRight(
			MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		
	}

}
