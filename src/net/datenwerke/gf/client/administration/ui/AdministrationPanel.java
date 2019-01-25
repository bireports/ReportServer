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
 
 
package net.datenwerke.gf.client.administration.ui;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwBorderContainer;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.core.client.util.Margins;

@Singleton
public class AdministrationPanel extends DwBorderContainer {

	@CssClassConstant
	public static final String CSS_NAME = "rs-admin";
	
	@CssClassConstant
	public static final String CSS_MAIN_NAME = "rs-admin-main";
	
	final private AdministrationNavPanel navigationPanel;
	
	@Inject
	public AdministrationPanel(
		AdministrationNavPanel navigationPanel
		){
		super();
		
		this.navigationPanel = navigationPanel;
		
		initializeUI();
		
		navigationPanel.setAdministrationPanel(this);
	}

	@Override
	public String getCssName() {
		return super.getCssName() + " " + CSS_NAME;
	}
	
	public String getCssMainName() {
		return CSS_MAIN_NAME;
	}


	private void initializeUI() {
		/* set layout */
		BorderLayoutData westData = new BorderLayoutData(200);
		westData.setSplit(true);
		westData.setCollapsible(true);
		westData.setMargins(new Margins(0,15,0,0));

		setWestWidget(navigationPanel, westData);
		setCenterWidget(DwContentPanel.newInlineInstance());
	}

	public void showAdminModule(AdminModule adminModule) {
		adminModule.notifyOfSelection();
		
		/* add module's widget */
		Widget widget = adminModule.getMainWidget();
		widget.getElement().addClassName(getCssMainName());
		setCenterWidget(widget);
		
		forceLayout();
	}
}
