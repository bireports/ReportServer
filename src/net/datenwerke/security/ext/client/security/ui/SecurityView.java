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
 
 
package net.datenwerke.security.ext.client.security.ui;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.dto.GrantAccessDto;
import net.datenwerke.security.client.treedb.dto.SecuredAbstractNodeDto;
import net.datenwerke.security.client.treedb.dto.decorator.SecuredAbstractNodeDtoDec;
import net.datenwerke.security.ext.client.security.locale.SecurityMessages;
import net.datenwerke.security.ext.client.security.ui.aclview.NodeACLView;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class SecurityView extends MainPanelView {
	
	public static final String VIEW_ID = "SecurityView";
	
	private final Provider<NodeACLView> aclViewProvider;
	
	@Inject
	public SecurityView(
		Provider<NodeACLView> aclViewProvider 
		){
		super();
		
		/* store objects */
		this.aclViewProvider = aclViewProvider;
	}
	
	@Override
	public String getViewId() {
		return VIEW_ID;
	}
	
	@Override
	public boolean isSticky() {
		return true;
	}

	@Override
	public boolean appliesTo(AbstractNodeDto item) {
		if(! (item instanceof SecuredAbstractNodeDto))
			return false;
		
		return ((SecuredAbstractNodeDtoDec)item).hasAccessRight(GrantAccessDto.class);
	}
	
	@Override
	public String getComponentHeader(){
		return SecurityMessages.INSTANCE.permissionManagement();
	}
	
	@Override
	public ImageResource getIcon() {
		return BaseIcon.LOCK.toImageResource();
	}
	
	@Override
	public Widget getViewComponent() {
		NodeACLView aclView = aclViewProvider.get();
		aclView.initialize((SecuredAbstractNodeDto) getSelectedNode());
		
		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		wrapper.setScrollMode(ScrollMode.AUTOY);
		wrapper.add(aclView, new VerticalLayoutData(1,-1, new Margins(10)));
		
		return wrapper;
	}

	

}
