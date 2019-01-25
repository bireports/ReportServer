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
 
 
package net.datenwerke.gxtdto.client.codemirror;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.HasValue;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class CodeMirrorPanel extends VerticalLayoutContainer implements HasValue<String>, HasValueChangeHandlers<String>, ValueChangeHandler<String>{

	public interface ToolBarEnhancer{
		void enhance(ToolBar toolbar, CodeMirrorPanel codeMirrorPanel);
		boolean allowPopup();
	}
	
	private final Resources resources = GWT.create(Resources.class);
	
	interface Resources extends ClientBundle {
		@Source("codemirror.css")
		Style css();
	}

	interface Style extends CssResource {
		  @ClassName("popup-codemirror")
		  String rsPopupCodeMirror();
	}
	
	private TextArea textArea;
	private DwToolBar toolbar;
	
	private final CodeMirrorConfig cmc;
	private ToolBarEnhancer enhancer;

	public CodeMirrorPanel(final String mode) {
		this(new CodeMirrorConfig(mode));
	}

	public CodeMirrorPanel(String mode, ToolBarEnhancer enhancer) {
		this(new CodeMirrorConfig(mode), enhancer);
	}
	
	public CodeMirrorPanel(CodeMirrorConfig cmc){
		this(cmc,null);
	}
	
	public CodeMirrorPanel(CodeMirrorConfig cmc, ToolBarEnhancer enhancer){
		super();
		getElement().addClassName("rs-codemirror");
		
		resources.css().ensureInjected();
		
		this.cmc = cmc;
		this.enhancer = enhancer;
		if(null == enhancer){
			this.enhancer =  new ToolBarEnhancer() {
				
				@Override
				public void enhance(ToolBar toolbar, CodeMirrorPanel codeMirrorPanel) {
				}
				
				@Override
				public boolean allowPopup() {
					return true;
				}
			};
		}
		
		if(GXT.isIE6() || GXT.isIE7())
			textArea = new TextArea();
		else
			textArea = new CodeMirrorTextArea(cmc);
		
		/* listen to change events */
		textArea.addValueChangeHandler(this);
		
		
		/* toolbar */
		toolbar = new DwToolBar();
		this.enhancer.enhance(toolbar, this);
		
		if(this.enhancer.allowPopup())
			addPopup();
		
		if(toolbar.getWidgetCount() > 0)
			add(toolbar, new VerticalLayoutData(1,-1));
		
		add(textArea, new VerticalLayoutData(1,1));
		
		addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				TextArea ta = getTextArea();
				if((ta instanceof CodeMirrorTextArea))
					((CodeMirrorTextArea)ta).adjustSize();
			}
		});
	}
	
	private void addPopup() {
		if(!(textArea instanceof CodeMirrorTextArea))
			return;
		
		toolbar.add(new FillToolItem());
		
		DwTextButton button = new DwTextButton(BaseIcon.ARROWS_OUT);
		toolbar.add(button);
		button.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				CodeMirrorPopup popup = new CodeMirrorPopup(((CodeMirrorTextArea) textArea).getCodeMirror(), cmc, enhancer);
				popup.show();
			}
		});
	}

	public ToolBarEnhancer getEnhancer() {
		return enhancer;
	}


	public TextArea getTextArea() {
		return textArea;
	}

	@Override
	public String getValue() {
		return textArea.getValue();
	}

	public String getCurrentValue(){
		return textArea.getCurrentValue();
	}
	
	@Override
	public void setValue(String value) {
		textArea.setValue(value);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		textArea.setValue(value, fireEvents);		
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		ValueChangeEvent.fire(this, event.getValue());
	}

	public void setNoOpacity() {
		addStyleName(resources.css().rsPopupCodeMirror());
	}

}
