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
 
 
package net.datenwerke.gxtdto.client.statusbar;

import net.datenwerke.gxtdto.client.statusbar.ui.StatusBarWidget;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * 
 *
 */
public class StatusBarUIServiceImpl implements StatusBarUIService {

	final private Provider<StatusBarWidget> statusBarWidgetProvider;
	
	@Inject
	public StatusBarUIServiceImpl(
		Provider<StatusBarWidget> statusBarWidgetProvider
		){
		
		this.statusBarWidgetProvider = statusBarWidgetProvider;
	}
	
	@Override
	public StatusBarWidget getStatusBarWidget(){
		return statusBarWidgetProvider.get();
	}
	
	@Override
	public void clearLeft() {
		getStatusBarWidget().clearLeft();
	}
	
	@Override
	public void clearRight() {
		getStatusBarWidget().clearRight();
	}
	
	@Override
	public void addRight(String text, String icon){
		getStatusBarWidget().addRight(text, icon);
	}
	
	@Override
	public void addLeft(String text, String icon){
		getStatusBarWidget().addLeft(text, icon);
	}
	
	@Override
	public void addRight(Widget comp){
		getStatusBarWidget().addRight(comp);
	}
	
	@Override
	public void addLeft(Widget comp){
		getStatusBarWidget().addLeft(comp);
	}
	
	@Override
	public void removeRight(Widget comp){
		getStatusBarWidget().removeRight(comp);
	}
	
	@Override
	public void removeLeft(Widget comp){
		getStatusBarWidget().removeLeft(comp);
	}

	@Override
	public void setContainer(Viewport container) {
		getStatusBarWidget().setContainer(container);
	}

}
