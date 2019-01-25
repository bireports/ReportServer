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
 
 
package net.datenwerke.gxtdto.client.codemirror;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel.ToolBarEnhancer;
import net.datenwerke.gxtdto.client.locale.BaseMessages;

public class CodeMirrorPopup extends DwWindow {
	
	private final Resources resources = GWT.create(Resources.class);
	
	interface Resources extends ClientBundle {
		@Source("codemirror.css")
		Style css();
	}

	interface Style extends CssResource {
		  @ClassName("popup-codemirror")
		  String rsPopupCodeMirror();
	}

	private final ToolBarEnhancer enhancer;
	
	private CodeMirrorPanel panel;
	private CodeMirror parentMirror;

	
	public CodeMirrorPopup(CodeMirror mirror, CodeMirrorConfig cmc, ToolBarEnhancer enhancer) {
		this.parentMirror = mirror;
		this.enhancer = enhancer;
		
		resources.css().ensureInjected();
		
		init(cmc);
	}
	
	private void init(CodeMirrorConfig cmc){
		cmc.setLineNumbersVisible(true);
		
		setSize(800, 600);
		panel = new CodeMirrorPanel(cmc, new ToolBarEnhancer() {
			@Override
			public void enhance(ToolBar toolbar, CodeMirrorPanel codeMirrorPanel) {
				enhancer.enhance(toolbar, codeMirrorPanel);
			}
			
			@Override
			public boolean allowPopup() {
				return false;
			}
		});
		
		if(null != parentMirror)
			panel.setValue(parentMirror.getValue());
		
		VerticalLayoutContainer cont = new VerticalLayoutContainer();
		cont.add(panel, new VerticalLayoutData(1, 1));
		add(cont);
		
		DwTextButton closeButton = new DwTextButton(BaseMessages.INSTANCE.submit());
		closeButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				hide();
				if(null != parentMirror){
					parentMirror.getCodeMirrorTextArea().setValue(panel.getValue(), true);
				}
			}
		});

		this.getButtonBar().add(closeButton);
	}
	
}
