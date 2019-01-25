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
 
 
package net.datenwerke.gxtdto.client.utilityservices.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.gxtdto.client.utilityservices.ext.HookableContainer;
import net.datenwerke.gxtdto.client.utilityservices.menu.hooks.MenuBaseHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;

public class DwHookableMenu extends Menu implements HookableContainer {
	
	private String menuName;

	@Inject
	private HookHandlerService hookHandler;
	
	private HashMap<String, String> hookConfig = new HashMap<String, String>();
	
	@Override
	public String getContainerName() {
		return menuName;
	}
	
	public void addFill(){
		add(new FillToolItem());
	}
	
	@Override
	public HashMap<String, String> getHookConfig() {
		return hookConfig;
	}

	@Override
	public void setHookConfig(HashMap<String, String> hookConfig) {
		if(null == hookConfig)
			hookConfig = new HashMap<String, String>();
		this.hookConfig = hookConfig;
	}

	@Override
	public void setContainerName(String name) {
		this.menuName = name;
	}

	public void addSeparatorAndBaseHookers(){
		addBaseHookers(true);
	}
	
	public void addBaseHookers(boolean addSeparator){
		for(MenuBaseHook hooker : getBaseHookers())
			addSeparator = hooker.attachTo(this, addSeparator);
	}
	
	private List<MenuBaseHook> getBaseHookers() {
		List<MenuBaseHook> baseHookers = new ArrayList<MenuBaseHook>();
		for(MenuBaseHook hooker : hookHandler.getHookers(MenuBaseHook.class))
			if(hooker.consumes(this))
				baseHookers.add(hooker);
		
		return baseHookers;
	}
}
