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
 
 
package net.datenwerke.rs.incubator.client.managerhelpersearch.hookers;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.gf.client.managerhelper.hooks.ManagerHelperTreeToolbarEnhancerHook;
import net.datenwerke.gf.client.managerhelper.ui.AbstractTreeNavigationPanel;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.gxtdto.client.utils.labelprovider.DisplayTitleLabelProvider;
import net.datenwerke.gxtdto.client.utils.loadconfig.SearchLoadConfig;
import net.datenwerke.gxtdto.client.utils.loadconfig.SearchLoadConfigBean;
import net.datenwerke.gxtdto.client.xtemplates.NullSafeFormatter;
import net.datenwerke.rs.incubator.client.globalsearch.locale.GlobalSearchMessages;
import net.datenwerke.rs.search.client.search.SearchDao;
import net.datenwerke.rs.search.client.search.SearchUiService;
import net.datenwerke.rs.search.client.search.dto.EmptySearchResultDto;
import net.datenwerke.rs.search.client.search.dto.SearchResultEntryDto;
import net.datenwerke.rs.search.client.search.dto.SearchResultListDto;
import net.datenwerke.rs.search.client.search.dto.pa.SearchResultEntryDtoPA;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.XTemplates.FormatterFactoryMethod;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ManagerHelperSearchBar implements
		ManagerHelperTreeToolbarEnhancerHook {
	
	
	@FormatterFactories(@FormatterFactory(factory=NullSafeFormatter.class,methods=@FormatterFactoryMethod(name="nullsafe")))
	public interface SearchTemplates extends XTemplates {
		@XTemplate("<div class=\"rs-search-li\">" +
				"<div class=\"rs-search-li-icon\"><i class=\"{icon}\"></i></div>" +
				"<div class=\"rs-search-li-info\">" +
				"<div class=\"rs-search-li-title\">{entry.title:nullsafe}" +
				"<tpl for=\"entry.links\">" +
				"<tpl if=\"# == 1\">" +
				" ({historyLinkBuilderName})" +
				"</tpl>" +
				"</tpl>" +
				"</div>" +
				"</div>" +
				"<div class=\"rs-search-li-done\"></div>" +
				"</div>")
	    public SafeHtml render(SearchResultEntryDto entry, String icon); 
	}
	
	private final SearchDao searcher;
	private final ToolbarService toolbarService;
	private SearchUiService searchService;
	
	@Inject
	public ManagerHelperSearchBar(
		SearchDao searcher,
		SearchUiService searchService,
		ToolbarService toolbarService
		){
		
		/* store objects */
		this.searcher = searcher;
		this.searchService = searchService;
		this.toolbarService = toolbarService;
	}
	
	@Override
	public void treeNavigationToolbarEnhancerHook_addLeft(ToolBar toolbar, UITree tree, final AbstractTreeNavigationPanel abstractTreeNavigationPanel) {
		RpcProxy<SearchLoadConfig, SearchResultListDto> proxy = new RpcProxy<SearchLoadConfig, SearchResultListDto>() {
			@Override
			public void load(SearchLoadConfig loadConfig, AsyncCallback<SearchResultListDto> callback) {
				String query = loadConfig.getQuery();
				if(null != query && query.length() > 1){
					TreeDbManagerDao treeManager = abstractTreeNavigationPanel.getTreeDbManager();
					searcher.find(treeManager.getBaseNodeMapper(), searchService.enhanceQuery(query), callback);
				}
			}
		};

		DataReader<PagingLoadResult<SearchResultEntryDto>, SearchResultListDto> reader = new DataReader<PagingLoadResult<SearchResultEntryDto>, SearchResultListDto>() {
			@Override
			public PagingLoadResult<SearchResultEntryDto> read(Object loadConfig, SearchResultListDto data) {
				if( data.getTotalLength() > 0)
					return new PagingLoadResultBean<SearchResultEntryDto>(data.getData(), data.getTotalLength(), data.getOffset());
				List<SearchResultEntryDto> emptyList = new ArrayList<SearchResultEntryDto>();
				emptyList.add(new EmptySearchResultDto(GlobalSearchMessages.INSTANCE.noResultTitle(), GlobalSearchMessages.INSTANCE.noResultDesc(), BaseIcon.EXCLAMATION.toString()));
				return new PagingLoadResultBean<SearchResultEntryDto>(emptyList, data.getTotalLength(), data.getOffset());
			}
		};


		PagingLoader<SearchLoadConfig, PagingLoadResult<SearchResultEntryDto>> listLoader = new PagingLoader<SearchLoadConfig, PagingLoadResult<SearchResultEntryDto>>(proxy, reader);

		ListStore<SearchResultEntryDto> store = new ListStore<SearchResultEntryDto>(SearchResultEntryDtoPA.INSTANCE.dtoId());
		listLoader.addLoadHandler(new LoadResultListStoreBinding<SearchLoadConfig, SearchResultEntryDto, PagingLoadResult<SearchResultEntryDto>>(store));

		final SearchTemplates template = GWT.create(SearchTemplates.class);
		
		ListView<SearchResultEntryDto, SearchResultEntryDto> view = new ListView<SearchResultEntryDto, SearchResultEntryDto>(store, new IdentityValueProvider<SearchResultEntryDto>());
		view.setCell(new AbstractCell<SearchResultEntryDto>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					SearchResultEntryDto value, SafeHtmlBuilder sb) {
				sb.append(template.render(value, BaseIcon.from(value.getIconSmall()).getCssName()));
			}
		});
		
		final ComboBox<SearchResultEntryDto> combo = new ComboBox<SearchResultEntryDto>(new ComboBoxCell<SearchResultEntryDto>(store, new DisplayTitleLabelProvider<SearchResultEntryDto>(), view){

			/* gxt workarround
			 * http://www.sencha.com/forum/showthread.php?185967
			 * */
			@Override
			protected PagingLoadConfig getParams(String query) {
				SearchLoadConfig config = null;
				if (loader.isReuseLoadConfig()) {
					config = (SearchLoadConfig) loader.getLastLoadConfig();
				} else {
					config = new SearchLoadConfigBean();
				}
				config.setLimit(pageSize);
				config.setOffset(0);
				config.setQuery(query);

				return config;
			}
		});
	    combo.setWidth(150);  
	    combo.setMinListWidth(400);
	    combo.setLoader(listLoader);
	    combo.setHideTrigger(true);  
	    
	    
	    combo.addSelectionHandler(new SelectionHandler<SearchResultEntryDto>() {
			
			@Override
			public void onSelection(SelectionEvent<SearchResultEntryDto> event) {
				SearchResultEntryDto selection = event.getSelectedItem();
				if(null == selection)
					return;

				for(HistoryLinkDto link : selection.getLinks()){
					if(link.getHistoryToken().equals(History.getToken()))
						History.fireCurrentHistoryState();
					else
						History.newItem(link.getHistoryToken(), true);
					break;
				}
			}
		});
		
	    toolbarService.addPlainToolbarItem(toolbar, BaseIcon.SEARCH);
	    toolbar.add(combo);
	}

	@Override
	public void treeNavigationToolbarEnhancerHook_addRight(ToolBar toolbar,
			UITree tree, AbstractTreeNavigationPanel abstractTreeNavigationPanel) {

	}

}
