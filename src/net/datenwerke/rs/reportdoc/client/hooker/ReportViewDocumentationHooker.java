/*
 *  ReportServer
 *  Copyright (c) 2016 datenwerke Jan Albrecht
 *  http://reportserver.datenwerke.net
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
 
 
package net.datenwerke.rs.reportdoc.client.hooker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportExecutorViewToolbarHook;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorInformation;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanel;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterUIService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.reportdoc.client.ReportDocumentationUIModule;
import net.datenwerke.rs.reportdoc.client.locale.ReportDocumentationMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
public class ReportViewDocumentationHooker implements
		ReportExecutorViewToolbarHook {

	private final ToolbarService toolbarService;
	private final UtilsUIService utilsService;
	private final ReportExporterUIService exporterService;
	
	@Inject
	public ReportViewDocumentationHooker(
		ToolbarService toolbarService,
		UtilsUIService utilsService,
		ReportExporterUIService exporterService
		){
		
		this.toolbarService = toolbarService;
		this.utilsService = utilsService;
		this.exporterService = exporterService;
	}
	
	@Override
	public boolean reportPreviewViewToolbarHook_addLeft(ToolBar toolbar, final ReportDto report, ReportExecutorInformation info, ReportExecutorMainPanel mainPanel) {
		return false;
	}

	@Override
	public boolean reportPreviewViewToolbarHook_addRight(ToolBar toolbar, final ReportDto report, ReportExecutorInformation info, ReportExecutorMainPanel mainPanel) {
		DwTextButton berichtsdokuBtn = toolbarService.createSmallButtonLeft(ReportDocumentationMessages.INSTANCE.berichtsdokuHelpText(), BaseIcon.BOOK);
		berichtsdokuBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(! report.isModified())
					openDocumentation(report);
				else {
					ConfirmMessageBox cmb = new DwConfirmMessageBox(ReportDocumentationMessages.INSTANCE.reportChangedInfoHeader(), ReportDocumentationMessages.INSTANCE.reportChangedInfoMessage());
					cmb.addDialogHideHandler(new DialogHideHandler() {
						@Override
						public void onDialogHide(DialogHideEvent event) {
							if (event.getHideButton() == PredefinedButton.YES) 
								openDocumentation(report);
						}
					});
						
					cmb.show();
				}
			}
		});
		toolbar.add(berichtsdokuBtn);
		
		return true;
	}

	@Override
	public void reportPreviewViewToolbarHook_reportUpdated(ReportDto report,
			ReportExecutorInformation info) {
		
	}
	
	private void openDocumentation(final ReportDto report) {
		int nonce = Random.nextInt();
		String url = GWT.getModuleBaseURL() + ReportDocumentationUIModule.SERVLET + "?nonce=" + nonce + "&id=" + report.getId() + "&format=PDF&download=true";
		ClientDownloadHelper.triggerDownload(url);
	};


}
