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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.layout;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;

public class DwHorizontalFlowLayoutContainer extends FlowLayoutContainer {

	protected VerticalAlign verticalAlign = VerticalAlign.TOP;
	
	protected boolean nobreak = false;
	
	public DwHorizontalFlowLayoutContainer() {
		super();
	}
	
	@Override
	protected void onInsert(int index, Widget child) {
		super.onInsert(index, child);
		
		child.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		child.getElement().getStyle().setVerticalAlign(verticalAlign);
		if(nobreak)
			child.getElement().getStyle().setWhiteSpace(WhiteSpace.NOWRAP);
	}
	
	public void setVerticalAlign(VerticalAlign verticalAlign) {
		this.verticalAlign = verticalAlign;
	}
	
	public VerticalAlign getVerticalAlign() {
		return verticalAlign;
	}

	public boolean isNobreak() {
		return nobreak;
	}

	public void setNobreak(boolean nobreak) {
		this.nobreak = nobreak;
	}

	
}
