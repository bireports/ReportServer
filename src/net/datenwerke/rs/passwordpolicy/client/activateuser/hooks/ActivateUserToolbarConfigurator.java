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
 
 
package net.datenwerke.rs.passwordpolicy.client.activateuser.hooks;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.servercommunication.callback.ModalAsyncCallback;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.passwordpolicy.client.activateuser.rpc.ActivateUserServiceDao;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.passwordpolicy.locale.PasswordPolicyMessages;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ActivateUserToolbarConfigurator implements MainPanelViewToolbarConfiguratorHook{

	private final ActivateUserServiceDao activateUserService;
	private final ToolbarService toolbarService;
	
	@Inject
	public ActivateUserToolbarConfigurator(
		ActivateUserServiceDao activateUserService,
		ToolbarService toolbarService
		) {
		this.activateUserService = activateUserService;
		this.toolbarService = toolbarService;
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addLeft(MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addRight(MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		if(! (view instanceof SimpleFormView))
			return;
		if(! (selectedNode instanceof UserDto))
			return;
		
		final UserDto userDto = (UserDto) selectedNode;
		
		DwTextButton activateBtn = toolbarService.createSmallButtonLeft( PasswordPolicyMessages.INSTANCE.activateButtonLabel(), BaseIcon.ENVELOPE_O);
		activateBtn.setToolTip(PasswordPolicyMessages.INSTANCE.activateButtonTooltip());
		activateBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				activateUserService.activateAccount(userDto, true, new ModalAsyncCallback<Void>("OK") {});
			}
		});
		
		toolbar.add(activateBtn);
	}


}
