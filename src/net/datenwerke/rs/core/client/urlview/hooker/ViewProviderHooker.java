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
 
 
package net.datenwerke.rs.core.client.urlview.hooker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewSpecialViewHandler;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class ViewProviderHooker implements MainPanelViewProviderHook {

	private final Map<String, List<String[]>> config;
	private final Provider<LoginService> loginServiceProvider;
	private final HookHandlerService hookHandler;

	public ViewProviderHooker(HookHandlerService hookHandler, Provider<LoginService> loginServiceProvider, Map<String, List<String[]>> config){
		this.hookHandler = hookHandler;
		this.loginServiceProvider = loginServiceProvider;
		this.config = config;
	}
	
	@Override
	public List<MainPanelView> mainPanelViewProviderHook_getView(AbstractNodeDto node) {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		
		Class<?> type = node.getClass();
		while(null != type){
			if(config.containsKey(type.getName())){
				for(String[] conf : config.get(type.getName()))
					views.add(getView(conf));
			}
			type = type.getSuperclass();
		}
		
		return views;
	}

	private MainPanelView getView(final String[] conf) {
		return new MainPanelView() {
			
			@Override
			public Widget getViewComponent() {
				final DwContentPanel wrapper = DwContentPanel.newInlineInstance();
				
				String className = getSelectedNode().getClass().getName();
				String id = getSelectedNode().getId().toString();
				
				String url = conf[1].replace("${id}", id)
									.replace("${type}", className)
									.replace("${username}", loginServiceProvider.get().getCurrentUser().getUsername());
				
				boolean consumed = false;
				for(UrlViewSpecialViewHandler handler : hookHandler.getHookers(UrlViewSpecialViewHandler.class)){
					
					if(handler.consumes(url)){
						Widget widget = handler.getWidget(url);
						
						SimpleContainer container = new SimpleContainer();
						container.add(widget);
						wrapper.add(container);
						
						consumed = true;
						break;
					}
				}
				
				if(! consumed){
					wrapper.mask(BaseMessages.INSTANCE.loadingMsg());
					wrapper.setUrl(url, new LoadHandler() {
						
						@Override
						public void onLoad(LoadEvent event) {
							wrapper.unmask();
						}
					});
					
					wrapper.addStyleName("adminrepview-frame");
				}
				
				return wrapper;
			}
			
			@Override
			public String getComponentHeader() {
				return conf[0];
			}
			
			@Override
			public String getViewId() {
				return "adminrepview-view";
			}
		};
	}


}
