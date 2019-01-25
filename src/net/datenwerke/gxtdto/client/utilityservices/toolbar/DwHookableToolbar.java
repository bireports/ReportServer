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
 
 
package net.datenwerke.gxtdto.client.utilityservices.toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.gxtdto.client.utilityservices.ext.HookableContainer;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.hooks.ToolbarBaseHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;

public class DwHookableToolbar extends DwToolBar implements HookableContainer {

	private String toolbarName;

	@Inject
	private HookHandlerService hookHandler;
	
	private HashMap<String, String> hookConfig = new HashMap<String, String>();
	
	public DwHookableToolbar(){
		super();
	}
	
	@Override
	public String getContainerName() {
		return toolbarName;
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
		this.hookConfig = hookConfig;
	}

	@Override
	public void setContainerName(String toolbarName) {
		this.toolbarName = toolbarName;
	}

	public void addBaseHookersLeft(){
		for(ToolbarBaseHook hooker : getBaseHookers())
			hooker.attachToLeft(this);
	}
	
	public void addBaseHookersRight(){
		for(ToolbarBaseHook hooker : getBaseHookers())
			hooker.attachToRight(this);
	}
	
	private List<ToolbarBaseHook> getBaseHookers() {
		List<ToolbarBaseHook> baseHookers = new ArrayList<ToolbarBaseHook>();
		for(ToolbarBaseHook hooker : hookHandler.getHookers(ToolbarBaseHook.class))
			if(hooker.consumes(this))
				baseHookers.add(hooker);
		
		return baseHookers;
	}

	public void addFillAndBaseHookersRight(){
		addFill();
		addBaseHookersRight();
	}
}
