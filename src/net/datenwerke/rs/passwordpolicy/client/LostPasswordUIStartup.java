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
 
 
package net.datenwerke.rs.passwordpolicy.client;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.ModalAsyncCallback;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.authenticator.client.login.hooks.LoginMenuHook;
import net.datenwerke.rs.passwordpolicy.client.lostpassword.LostPasswordDao;
import net.datenwerke.security.ext.client.security.locale.SecurityMessages;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class LostPasswordUIStartup {

	@Inject
	public LostPasswordUIStartup(
			HookHandlerService hookHandler,
			final LostPasswordDao lostPasswordDao
	) {
		/* hook a menu item into the login dialog */
		Provider<MenuItem> provider = new Provider<MenuItem>() {

			@Override
			public MenuItem get() {
				MenuItem menuItem = new DwMenuItem(SecurityMessages.INSTANCE.lostPassword().replaceAll("'", "''"));
				
				menuItem.addSelectionHandler(new SelectionHandler<Item>() {
					@Override
					public void onSelection(SelectionEvent<Item> event) {
						final DwWindow window = new DwWindow();
						window.setHeadingText(SecurityMessages.INSTANCE.lostPassword());
						window.setWidth(350);
						window.setHeight(200);
						
						VerticalLayoutContainer container = new VerticalLayoutContainer();
						
						container.add(new Label(SecurityMessages.INSTANCE.lostPasswordMessageText()));
						
						final TextField field = new TextField();
						container.add(field, new VerticalLayoutData(1,30, new Margins(10)));
						
						DwTextButton cancel = new DwTextButton(BaseMessages.INSTANCE.cancel());
						cancel.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {
								window.hide();
							}
						});
						window.addButton(cancel);
						
						DwTextButton ok = new DwTextButton(BaseMessages.INSTANCE.submit());
						ok.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {
								window.hide();
								lostPasswordDao.requestNewPassword(field.getCurrentValue(), new ModalAsyncCallback<Void>(SecurityMessages.INSTANCE.lostPasswordSuccessNotam()) {});
							}
						});
						window.addButton(ok);
						
						window.add(container, new MarginData(10));
						
						window.show();
					}
				});
				
				return menuItem;
			}
		};

		hookHandler.attachHooker(LoginMenuHook.class, new LoginMenuHook(provider));	
		
	}
}
