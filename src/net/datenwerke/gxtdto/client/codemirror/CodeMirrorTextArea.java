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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.sencha.gxt.cell.core.client.form.TextAreaInputCell;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;

import net.datenwerke.gxtdto.client.codemirror.hooks.CodeMirrorKeyboardHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

public class CodeMirrorTextArea extends TextArea {

	static class CodeMirrorInputCell extends TextAreaInputCell {
		private CodeMirrorTextArea textArea;
		@Override
		public String getText(XElement parent) {
			return textArea.getValue();
		}

		public void setTextArea(CodeMirrorTextArea codeMirrorTextArea) {
			this.textArea = codeMirrorTextArea;
		}
	}
	
	private static final String EMPTY_STRING = "";

	private CodeMirror codeMirror;

	private CodeMirrorConfig cmc;
	
	private boolean isActive = false;

	private List<CodeMirrorKeyboardHook> keyboardHooks = new ArrayList<CodeMirrorKeyboardHook>();

	private String changedValue;

	private boolean inChangeValue;

	private boolean returnOldValueForEventCheck;
	
	public CodeMirrorTextArea(final CodeMirrorConfig cmc) {
		super(new CodeMirrorInputCell());
		this.cmc = cmc;
		
		addAttachHandler( new Handler() {
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				if(!isActive)
					activate();
			}
		});
		
		addShowHandler(new ShowHandler() {
			
			@Override
			public void onShow(ShowEvent event) {
				try{
					adjustSize();
				}catch(NullPointerException ex){}
			}
		});
		
		addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				try{
					adjustSize();
				}catch(NullPointerException ex){}
			}
		});
		
		
		
		addDomHandler(new KeyPressHandler(){

			@Override
			public void onKeyPress(KeyPressEvent event) {
				for(CodeMirrorKeyboardHook hook : keyboardHooks)
					hook.handleKeyEvent(event, CodeMirrorTextArea.this);
			}
			
		}, KeyPressEvent.getType());
		
		((CodeMirrorInputCell)getCell()).setTextArea(this);
	}
	
	public void adjustSize() {
		/* Adjust codemirrors size to the size of the original textarea */
		int w = Util.parseInt(width, getOffsetWidth());
		int h = Util.parseInt(height, getOffsetHeight());
		adjustSize(w, h);
	}
	
	public void adjustSize(int w, int h) {
		if(null != codeMirror)
			codeMirror.setSize(w, h);
	}

	public void enableCodeMirrorPlugins(HookHandlerService hookHandler){
		for(CodeMirrorKeyboardHook keyboardHook : hookHandler.getHookers(CodeMirrorKeyboardHook.class))
			if(keyboardHook.consumes(this))
				keyboardHooks.add(keyboardHook);
	}
	
	public CodeMirrorTextArea(final String mode) {
		this(new CodeMirrorConfig(mode));
	}
	
	private void activate(){
		codeMirror = CodeMirror.fromTextArea(CodeMirrorTextArea.this, this.cmc);
		String value = super.getValue();
		codeMirror.setValue((null == value) ? EMPTY_STRING : value);

		/* Adjust codemirrors size to the size of the original textarea */
		int w = CodeMirrorTextArea.this.getStyleElement().getOffsetWidth();
		int h = CodeMirrorTextArea.this.getStyleElement().getOffsetHeight();
		codeMirror.setSize(w, h);
		
		isActive = true;
	}

	public CodeMirrorTextArea() {
		this(new CodeMirrorConfig());
	}


	@Override
	public void setValue(String value) {
		this.setValue(value, false);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		this.setValue(value, fireEvents, false);
	}
	
	@Override
	public void setValue(String value, boolean fireEvents, boolean redraw) {
 		/* remember that we are in set and to return old value once */
		inChangeValue = true;
		returnOldValueForEventCheck = true;
		changedValue = value;
		super.setValue(value, fireEvents, false); // no redraw
		changedValue = null;
		returnOldValueForEventCheck=false;
		inChangeValue = false;
		
		if(null != codeMirror)
			codeMirror.setValue((null == value)?EMPTY_STRING:value);
	}
	
	@Override
	public String getCurrentValue() {
		return getValue();
	}
	
	@Override
	public String getValue() {
		if(! returnOldValueForEventCheck && inChangeValue)
			return changedValue;
		else if(inChangeValue)
			returnOldValueForEventCheck = false;
			
		
		if(null != codeMirror)
			return codeMirror.getValue();

		return super.getValue();
	}
	
	public CodeMirror getCodeMirror() {
		return codeMirror;
	}

	public CodeMirrorConfig getCodeMirrorConfig() {
		return cmc;
	}

	
	private String getValueX() {
		if (!isRendered()) {
			return super.getValue();
		}
		String v = super.getCurrentValue();
		if (v == null || v.equals(getEmptyText())) {
			v = EMPTY_STRING;
		}

		if (getEmptyText() != null && v.equals(getEmptyText())) {
			return null;
		}

		if (v == null || v.equals(EMPTY_STRING)) {
			return null;
		}
		try {
			return getPropertyEditor().parse(v);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void blur() {
		super.blur();
	}

	public void addText(String text) {
		getCodeMirror().appendText(text);
	}
	
	public void addText(String text, Object pos) {
		getCodeMirror().appendText(text, pos);
	}
	
	public Object getCodeMirrorCursorPos() {
		return getCodeMirror().getCursorPos();
	}

	public void appendText(String text) {
		String value = getValue();
		value = null == value ? EMPTY_STRING : value;
		value += text;
		setValue(value);
	}

	public String getTextAreaId() {
		XElement ta = getElement().selectNode("textarea");
		
		if(null != ta)
			return ta.getId();
		
		throw new IllegalStateException("expected to find a text area");
	}

	
}
