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
 
 
package net.datenwerke.rs.search.client.search;

import javax.inject.Provider;

import net.datenwerke.gf.client.homepage.DwMainViewportUiService;
import net.datenwerke.gf.client.homepage.modules.ClientTempModule;
import net.datenwerke.rs.search.client.search.locale.SearchMessages;
import net.datenwerke.rs.search.client.search.module.SearchAreaComponent;
import net.datenwerke.rs.search.client.search.module.SearchAreaModule;

import com.google.inject.Inject;

public class SearchUiServiceImpl implements SearchUiService {

	private final DwMainViewportUiService viewportService;
	private final Provider<SearchAreaComponent> searchAreaProvider;
	private final Provider<SearchAreaModule> searchModuleProvider;
	
	
	@Inject
	public SearchUiServiceImpl(
		Provider<SearchAreaComponent> searchAreaProvider,
		Provider<SearchAreaModule> searchModuleProvider,
		DwMainViewportUiService viewportService) {
		this.searchAreaProvider = searchAreaProvider;
		this.searchModuleProvider = searchModuleProvider;
		this.viewportService = viewportService;
	}

	@Override
	public void runSearch(String searchStr){
		runSearch(searchStr, true);
	}
	
	private void runSearch(String searchStr, boolean serverCall){
		/* get display component, add it to module and display module */
		SearchAreaComponent displayComponent = searchAreaProvider.get();
		
		SearchAreaModule module = null;
		for(ClientTempModule m : viewportService.getTempModules()){
			if(m instanceof SearchAreaModule){
				module = (SearchAreaModule) m;
				break;
			}
		}
		if(null == module)
			module = searchModuleProvider.get();
		module.addSearchComponent(searchStr, displayComponent);
		viewportService.addTempModule(module);
		
		/* run search */
		if(serverCall)
			displayComponent.runSearch(searchStr);
	}

	@Override
	public void displaySearchModule() {
		runSearch(SearchMessages.INSTANCE.emptySearchTabHeader(), false);
	}

	@Override
	public String enhanceQuery(String query) {
		if(null == query || query.trim().length() < 1)
			return "";
		
		if(query.startsWith("="))
			query = query.substring(1);
		else
			query = "*" + query.toLowerCase().trim() + "*";

		return query;
	}

}
