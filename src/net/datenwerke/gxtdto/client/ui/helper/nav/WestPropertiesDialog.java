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
 
 
package net.datenwerke.gxtdto.client.ui.helper.nav;

import java.util.HashSet;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwBorderContainer;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.TreeSelectionModel;

public class WestPropertiesDialog extends DwWindow {

	private TreeStore<NavigationModelData<Widget>> navigationStore;
	private Tree<NavigationModelData<Widget>, String> navigationTree;
	
	private DwContentPanel navigationPanel;
	private CardLayoutContainer mainContainer;

	private int cardId = 0;
	private HashSet<Integer> toAdd = new HashSet<Integer>();

	public WestPropertiesDialog() {
		this(840, 620, 230);
	}
	
	public WestPropertiesDialog(int width, int height, int widthNav) {
		/* create window */
		final DwWindow window = this;
		window.setSize(width, height);
		window.setBodyBorder(true);
		
		window.setModal(true);
		
		/* create two column layout */
		DwBorderContainer borderContainer = new DwBorderContainer();
		setWidget(borderContainer);
		
		/* main panel */
		mainContainer = new CardLayoutContainer();
		
		DwContentPanel mainPartWrapper = DwContentPanel.newInlineInstance();
		mainPartWrapper.addClassName("rs-westprop-main");
		mainPartWrapper.add(mainContainer);
		
		borderContainer.setCenterWidget(mainPartWrapper);

		navigationPanel = DwContentPanel.newInlineInstance();
		navigationPanel.addClassName("rs-westprop-nav");
		borderContainer.setWestWidget(navigationPanel, new BorderLayoutData(widthNav));

		navigationStore = new TreeStore<NavigationModelData<Widget>>(new BasicObjectModelKeyProvider<NavigationModelData<Widget>>());

		/* build tree */
		final Tree<NavigationModelData<Widget>,String> navigationTree = createNavigationTreePanel();
		navigationPanel.add(navigationTree);
	}
	
	private Tree<NavigationModelData<Widget>,String> createNavigationTreePanel() {
		navigationTree = new Tree<NavigationModelData<Widget>, String>(navigationStore, NavigationModelData.nameValueProvider);

		/* icons */
		navigationTree.setIconProvider(new IconProvider<NavigationModelData<Widget>>() {
			@Override
			public ImageResource getIcon(NavigationModelData<Widget> model) {
				return model.getIcon();
			}
		});

		/* selection model */
		TreeSelectionModel<NavigationModelData<Widget>> sModel = new TreeSelectionModel<NavigationModelData<Widget>>();
		sModel.setSelectionMode(SelectionMode.SINGLE);
		sModel.addSelectionChangedHandler(new SelectionChangedHandler<NavigationModelData<Widget>>() {
			@Override
			public void onSelectionChanged(
					SelectionChangedEvent<NavigationModelData<Widget>> event) {
				if(null == event.getSelection() || event.getSelection().isEmpty())
					return;
				
				NavigationModelData<Widget> data = event.getSelection().get(0);
				int id = data.getId();
				if(toAdd.contains(id)){
					mainContainer.add(data.getModel());
					toAdd.remove(id);
				}
				
				mainContainer.setActiveWidget(data.getModel());
				mainContainer.forceLayout();
			}
		});
		navigationTree.setSelectionModel(sModel);

		return navigationTree;
	}

	public void addCard(String name, BaseIcon icon, Widget component) {
		addCard(name, icon.toImageResource(), component);
	}

	public void addCard(String name, ImageResource icon, Widget component) {
		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		
//		VerticalLayoutContainer scrollWrapper = new VerticalLayoutContainer();
//		scrollWrapper.setScrollMode(ScrollMode.AUTOY);
//		scrollWrapper.add(component, new VerticalLayoutData(1, -1,new Margins(0,0,0,10)));
		FlowLayoutContainer sWrapper = new DwFlowContainer();
		sWrapper.add(component, new MarginData(10));
		sWrapper.setScrollMode(ScrollMode.AUTOY);
		wrapper.add(sWrapper, new VerticalLayoutData(1, 1));
		
		int id = cardId++;
		navigationStore.add(new NavigationModelData<Widget>(id, name, icon, wrapper));
		
		/* add first, and queue rest */
		if(id == 0)
			mainContainer.add(wrapper);
		else
			toAdd.add(id);
	}

	
	@Override
	public void show() {
		super.show();
		
		navigationTree.getSelectionModel().select(navigationStore.getAll().get(0), false);
	}

}
