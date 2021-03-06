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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.mb;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

public class DwPromptMessageBox extends PromptMessageBox {

	public DwPromptMessageBox(String titleHtml, String messageHtml) {
		this(SafeHtmlUtils.fromString(titleHtml), SafeHtmlUtils.fromString(messageHtml));
	}
	
	public DwPromptMessageBox(SafeHtml titleHtml, SafeHtml messageHtml) {
		super(titleHtml.asString(), messageHtml.asString());
		 setPredefinedButtons(PredefinedButton.CANCEL, PredefinedButton.OK);
		getTextField().setWidth(110);
		initCss();
	}
	
	protected void initCss() {
		getElement().addClassName(DwWindow.CSS_NAME);
		getAppearance().getHeaderElem(getElement()).addClassName(DwWindow.CSS_HEADER_NAME);
		getAppearance().getBodyWrap(getElement()).addClassName(DwWindow.CSS_BODY_NAME);
		getAppearance().getContentElem(getElement()).addClassName(DwWindow.CSS_CONTENT_NAME);
		
		getButtonBar().getElement().addClassName(DwWindow.CSS_BBAR);
	}
	
	@Override
	protected void createButtons() {
		Widget focusWidget = getFocusWidget();

		boolean focus = focusWidget == null || (focusWidget != null && getButtonBar().getWidgetIndex(focusWidget) != -1);

		getButtonBar().clear();

		SelectHandler handler = new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				onButtonPressed((TextButton) event.getSource());
			}
		};
		
		for (int i = 0; i < getPredefinedButtons().size(); i++) {
			PredefinedButton b = getPredefinedButtons().get(i);
			TextButton tb = new DwTextButton(getText(b)); // only line that needed changin
			tb.setItemId(b.name());
			tb.addSelectHandler(handler);
			if (i == 0 && focus) {
				setFocusWidget(tb);
			}
			addButton(tb);
		}
	}

}
