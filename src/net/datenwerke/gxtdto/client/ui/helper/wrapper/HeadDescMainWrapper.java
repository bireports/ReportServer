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
 
 
package net.datenwerke.gxtdto.client.ui.helper.wrapper;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.widgets.SeparatorTextLabel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class HeadDescMainWrapper extends DwContentPanel {

	public HeadDescMainWrapper(String headline, String description, Widget main){
		setHeaderVisible(false);
		
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		setWidget(container);
		
		container.add(createHeadline(headline), new VerticalLayoutData(1,-1));
		container.add(createDescription(description),new VerticalLayoutData(1,-1));
		container.add(main, new VerticalLayoutData(1,-1, new Margins(10,0,0,0)));
	}
	
	public HeadDescMainWrapper(String headline, String description, Widget main, Margins margins){
		setHeaderVisible(false);
		
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		setWidget(container);
		
		container.add(createHeadline(headline), new VerticalLayoutData(1,-1, margins));
		container.add(createDescription(description),new VerticalLayoutData(1,-1, margins));
		container.add(main, new VerticalLayoutData(1,-1, margins));
	}
	
	public HeadDescMainWrapper(String headline, String description, Widget main, VerticalLayoutData layoutData, Margins margins){
		setHeaderVisible(false);
		
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		setWidget(container);
		
		container.add(createHeadline(headline), new VerticalLayoutData(1,-1, margins));
		container.add(createDescription(description),new VerticalLayoutData(1,-1, margins));
		container.add(main, layoutData);
	}
	
	protected Widget createDescription(String description) {
		return SeparatorTextLabel.createText(description);
	}

	protected Widget createHeadline(String headline) {
		return SeparatorTextLabel.createHeadlineLarge(headline);
	}

}
