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
 
 
package net.datenwerke.security.ext.client.security.ui.genericview;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.widgets.SeparatorTextLabel;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.ext.client.security.locale.SecurityMessages;
import net.datenwerke.security.ext.client.security.ui.aclview.GenericTargetACLView;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Singleton
public class GenericSecurityMainPanel extends DwContentPanel {

	private final Provider<GenericTargetACLView> aclViewProvider;
	
	@Inject
	public GenericSecurityMainPanel(
		Provider<GenericTargetACLView> aclViewProvider
		){
		/* store objects */
		this.aclViewProvider = aclViewProvider;
		
		initializeUI();
	}

	private void initializeUI() {
		setHeadingText(SecurityMessages.INSTANCE.securityManagement()); //$NON-NLS-1$
	}
	
	public void displayGenericRightsItem(final GenericSecurityViewDomainHook domain){
		/* clear panel */
		clear();
		
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		add(container);
		
		/* load */
		GenericTargetIdentifier genericTargetId = domain.genericSecurityViewDomainHook_getTargetId();
		
		GenericTargetACLView view = aclViewProvider.get();
		view.initialize(genericTargetId);
		
		/* description */
		view.addInfoText(domain.genericSecurityViewDomainHook_getDescription());
		
		/* panel */
		container.add(view, new VerticalLayoutData(1,-1, new Margins(10)));
		
		/* redo layout */
		forceLayout();
	}

}
