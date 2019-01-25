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
 
 
package net.datenwerke.rs.base.client.reportengines.table.hookers;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.locale.TableMessages;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportExecutorViewToolbarHook;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorInformation;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanel;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class CubifyHooker implements ReportExecutorViewToolbarHook {

	
	private final ToolbarService toolbarService;
	private final EnterpriseUiService enterpriseService;
	
	@Inject
	public CubifyHooker(
		ToolbarService toolbarService,
		EnterpriseUiService enterpriseService) {
		super();
		this.toolbarService = toolbarService;
		this.enterpriseService = enterpriseService;
	}

	@Override
	public boolean reportPreviewViewToolbarHook_addLeft(ToolBar toolbar,
			ReportDto report, ReportExecutorInformation info,
			final ReportExecutorMainPanel mainPanel) {
		if(! (report instanceof TableReportDto))
			return false;
		
		final TableReportDto list = (TableReportDto) report;
		if(! enterpriseService.isEnterprise() || list.isCube() || ! list.isAllowCubification())
			return false;
		
		/* add cubify button */
		DwTextButton cubifyBtn = toolbarService.createSmallButtonLeft(TableMessages.INSTANCE.cubifyLabel(), BaseIcon.CUBES);
		toolbar.add(cubifyBtn);
		
		cubifyBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				ConfirmMessageBox cmb = new DwConfirmMessageBox(TableMessages.INSTANCE.cubifyLabel(), TableMessages.INSTANCE.cubifyExplanation());
				cmb.addDialogHideHandler(new DialogHideHandler() {
					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton() == PredefinedButton.YES){
							list.setCube(true);
							mainPanel.reload();
						}
					}
				});
				cmb.show();
			}
		});
		
		
		return false;
	}

	@Override
	public boolean reportPreviewViewToolbarHook_addRight(ToolBar toolbar,
			ReportDto report, ReportExecutorInformation info,
			ReportExecutorMainPanel mainPanel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reportPreviewViewToolbarHook_reportUpdated(ReportDto report,
			ReportExecutorInformation info) {
		// TODO Auto-generated method stub

	}

}
