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
 
 
package net.datenwerke.rs.adminutils.client.datasourcetester;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.gxtdto.client.servercommunication.callback.ModalAsyncCallback;
import net.datenwerke.gxtdto.client.servercommunication.callback.TimeoutCallback;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.adminutils.client.datasourcetester.locale.DatasourceTesterMessages;
import net.datenwerke.rs.base.client.datasources.dto.DatabaseDatasourceDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class DatasourceTesterToolbarConfigurator implements MainPanelViewToolbarConfiguratorHook {

	DatasourceTesterMessages messages = GWT.create(DatasourceTesterMessages.class);

	private final DatasourceTesterDao datasourceTesterDao;
	private final ToolbarService toolbarUtils;

	@Inject
	public DatasourceTesterToolbarConfigurator(
			ToolbarService toolbarUtils,
			DatasourceTesterDao datasourceTestRPCService
			){

		this.toolbarUtils = toolbarUtils;
		this.datasourceTesterDao = datasourceTestRPCService;
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addLeft(MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		if(! (view instanceof SimpleFormView))
			return;
		if(! (selectedNode instanceof DatabaseDatasourceDto))
			return;

		final DatabaseDatasourceDto databaseDto = (DatabaseDatasourceDto) selectedNode;
		
		DwTextButton createPreviewBtn = toolbarUtils.createSmallButtonLeft(messages.testConnection(), BaseIcon.LINK); 
		createPreviewBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				ModalAsyncCallback<Boolean> callback = new ModalAsyncCallback<Boolean>(BaseMessages.INSTANCE.error(), messages.testFailed(), messages.success(), messages.testSuccess(),  messages.pleaseWait(), messages.testingTitle(),   messages.testingProgressMessage()){}; 
				Request request = datasourceTesterDao.testConnection(databaseDto, new TimeoutCallback<Boolean>(120000, callback));
				callback.setRequest(request);
			}
		});
		
		toolbar.add(createPreviewBtn);
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addRight(MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {

	}

}
