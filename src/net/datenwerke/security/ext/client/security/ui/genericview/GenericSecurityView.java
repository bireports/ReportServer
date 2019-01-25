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
 
 
package net.datenwerke.security.ext.client.security.ui.genericview;

import net.datenwerke.gxtdto.client.baseex.widget.layout.DwBorderContainer;

import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;

public class GenericSecurityView extends DwBorderContainer {

	private final GenericSecurityMainPanel mainPanel;
	private final GenericSecurityNavigationPanel navigationPanel;
	
	@Inject
	public GenericSecurityView(
		GenericSecurityMainPanel mainPanel,
		GenericSecurityNavigationPanel navigationPanel
		){
		
		/* store objects */
		this.mainPanel = mainPanel;
		this.navigationPanel = navigationPanel;
		
		initializeUI();
	}

	private void initializeUI() {
		/* set layout */
		BorderLayoutData westData = new BorderLayoutData(250);
		westData.setSplit(true);
		westData.setMargins(new Margins(0,1,0,0));
		
		setWestWidget(navigationPanel, westData);
		setCenterWidget(mainPanel);
	}
	
	
}
