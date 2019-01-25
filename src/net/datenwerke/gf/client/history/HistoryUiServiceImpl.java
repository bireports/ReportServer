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
 
 
package net.datenwerke.gf.client.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.gf.client.history.locale.HistoryMessages;
import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

@Singleton
public class HistoryUiServiceImpl implements HistoryUiService {

	private final LoginService loginService;
	
	private Map<String, List<HistoryCallback>> observer = new HashMap<String, List<HistoryCallback>>();

	private final HistoryDao historyDao;
	
	
	@Inject
	public HistoryUiServiceImpl(
		LoginService loginService,
		HistoryDao historyDao
		) 
		{
		
		this.loginService = loginService;
		this.historyDao = historyDao;
		
		/* register */
		History.addValueChangeHandler(this);
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		/* swallow event if not logged in */
		if(! loginService.isAuthenticated())
			return;
		
		/* notify observers */
		HistoryLocation location = HistoryLocation.fromString(event.getValue());
		
		if(observer.containsKey(location.getLocation())){
			for(HistoryCallback cb : observer.get(location.getLocation())){
				cb.locationChanged(location);
			}
		}
	}

	
	@Override
	public void addHistoryCallback(String location, HistoryCallback callback) {
		if(! observer.containsKey(location)){
			observer.put(location, new ArrayList<HistoryCallback>());
		}
		
		observer.get(location).add(callback);
	}

	@Override
	public MenuItem getJumpToMenuItem(final JumpToObjectCallback jumpToObjectCallback) {
		final MenuItem item = new DwMenuItem(HistoryMessages.INSTANCE.jumpTo(), BaseIcon.EXTERNAL_LINK);
		
		final Menu subMenu = new DwMenu();
		item.setSubMenu(subMenu);
		
		final MenuItem loading = new DwMenuItem(BaseMessages.INSTANCE.loadingMsg(), BaseIcon.REFRESH);
		subMenu.add(loading);
		
		subMenu.addShowHandler(new ShowHandler() {
			@Override
			public void onShow(ShowEvent event) {
				if(jumpToObjectCallback.haveToUpdate()){
					subMenu.clear();
					subMenu.setWidth(400);
					subMenu.add(loading);
					
					jumpToObjectCallback.getDtoTarget(new JumpToObjectResultCallback() {
						@Override
						public void setResult(Dto dto) {
							if(null == dto)
								return;
							historyDao.getLinksFor(dto, new RsAsyncCallback<List<HistoryLinkDto>>(){
								@Override
								public void onSuccess(List<HistoryLinkDto> result) {
									subMenu.remove(loading);
									for(final HistoryLinkDto link : result){
										MenuItem item = new DwMenuItem(link.getHistoryLinkBuilderName());
										item.addSelectionHandler(new SelectionHandler<Item>() {
											@Override
											public void onSelection(SelectionEvent<Item> event) {
												fire(link);
											}
										});
										subMenu.add(item);
									}

									item.expandMenu();
								}
							});
													
						}
					});
				}
			}
		});
		
		return item;
	}
	
	@Override
	public void fire(HistoryLinkDto link) {
		if(link.getHistoryToken().equals(History.getToken()))
			History.fireCurrentHistoryState();
		else
			History.newItem(link.getHistoryToken(), true);
	}

	@Override
	public void jumpTo(Dto dto) {
		if(null == dto)
			return;
		
		final ProgressMessageBox box = new ProgressMessageBox(BaseMessages.INSTANCE.loadingMsg());
		box.show();
		
		historyDao.getLinksFor(dto, new RsAsyncCallback<List<HistoryLinkDto>>(){
			@Override
			public void onSuccess(List<HistoryLinkDto> result) {
				if(null == result || result.isEmpty())
					return;
				
				box.hide();
				fire(result.get(0));
			}
		});
	}
	
	
}
