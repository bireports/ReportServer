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
 
 
package net.datenwerke.rs.core.client.urlview.hooker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectPreviewTabProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewObjectInfoPostProcessorHook;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewSpecialViewHandler;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;


public class ObjectInfoProvider implements ObjectPreviewTabProviderHook {

	private final HookHandlerService hookHandler;
	private final Map<String, List<String[]>> config;
	private final Provider<LoginService> loginServiceProvider;

	public ObjectInfoProvider(
			HookHandlerService hookHandler, 
			Provider<LoginService> loginServiceProvider, Map<String, List<String[]>> config){
		this.hookHandler = hookHandler;
		this.loginServiceProvider = loginServiceProvider;
		this.config = config;
	}

	@Override
	public boolean consumes(Object object) {
		if(null == object)
			return false;
		
		Class<?> type = object.getClass();
		while(null != type){
			if(config.containsKey(type.getName()))
				return true;
			type = type.getSuperclass();
		}
		
		return false;
	}

	@Override
	public List<PreviewComponent> getInfoComponents(Object object) {
		List<PreviewComponent> list = new ArrayList<ObjectPreviewTabProviderHook.PreviewComponent>();
		
		Class<?> type = object.getClass();
		while(null != type){
			if(config.containsKey(type.getName()))
				for(String[] conf : config.get(type.getName()))
					list.add(addInfoFor(conf, object));
			type = type.getSuperclass();
		}
		
		return list;
		
	}
	
	

	private PreviewComponent addInfoFor(final String[] conf, Object object) {
		return new PreviewComponent() {
			
			@Override
			public String getInfoName() {
				return conf[0];
			}
			
			@Override
			public Component getInfoComponent(Object object) {
				
				String className = object.getClass().getName();
				String id = "";
				if(object instanceof IdedDto)
					id = ((IdedDto)object).getDtoId().toString();

				String url = conf[1].replace("${id}", id)
									.replace("${type}", className)
									.replace("${username}", loginServiceProvider.get().getCurrentUser().getUsername());

				for(UrlViewObjectInfoPostProcessorHook hooker : hookHandler.getHookers(UrlViewObjectInfoPostProcessorHook.class)){
					String newUrl = hooker.postProcess(conf, url, object);
					if(null != newUrl)
						url = newUrl;
				}
				
				final DwContentPanel wrapper = DwContentPanel.newInlineInstance();

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
					wrapper.setUrl(url);
					wrapper.addStyleName("adminrepview-frame");
				}
			
				return wrapper;
			}
		};
	}


	
	

}
