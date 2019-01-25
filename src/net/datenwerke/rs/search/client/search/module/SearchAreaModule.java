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
 
 
package net.datenwerke.rs.search.client.search.module;

import javax.inject.Provider;

import net.datenwerke.gf.client.homepage.modules.ClientTempModuleImpl;
import net.datenwerke.rs.search.client.search.locale.SearchMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Component;

public class SearchAreaModule extends ClientTempModuleImpl {

	private final Provider<SearchAreaMainWidget> mainWidgetProvider;
	private SearchAreaMainWidget mainWidget;
	
	@Inject
	public SearchAreaModule(
			Provider<SearchAreaMainWidget> mainWidgetProvider) {
		this.mainWidgetProvider = mainWidgetProvider;
	}

	@Override
	public String getModuleName() {
		return SearchMessages.INSTANCE.searchAreaModule();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.SEARCH.toImageResource();
	}

	@Override
	public SearchAreaMainWidget getMainWidget() {
		if(null == mainWidget){
			mainWidget = mainWidgetProvider.get();
			mainWidget.setModule(this);
		}
		return mainWidget;
	}

	public void addSearchComponent(String search, Component displayComponent) {
		getMainWidget().addSearchComponent(search, displayComponent);
	}

	public void closeCurrent() {
		getMainWidget().close();
	}
	
	public void removeModule(){
		viewport.removeTempModule(this);
	}
	
	@Override
	public void onMouseOver(MouseEvent be) {
		
	}
	
	@Override
	public boolean isRecyclable() {
		return false;
	}
}
