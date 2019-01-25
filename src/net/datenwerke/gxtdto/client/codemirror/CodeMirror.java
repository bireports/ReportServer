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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.sencha.gxt.widget.core.client.event.BlurEvent;

public class CodeMirror {

	protected Object jsobject;
	protected CodeMirrorTextArea codeMirrorTextArea;

	protected static Map cmmap = new HashMap();

	protected CodeMirror(Object jsobject) {
		super();
		this.jsobject = jsobject;
	}

	public static CodeMirror fromJsObject(Object jsobject){
		if(cmmap.containsKey(jsobject)){
			return (CodeMirror) cmmap.get(jsobject);
		}

		return new CodeMirror(jsobject);
	}



	private static void fireOnBlurTextEditor(Object editor){
		if(cmmap.containsKey(editor)){
			if(cmmap.get(editor) instanceof CodeMirror){
				CodeMirror cmui = (CodeMirror) cmmap.get(editor);
				if(null != cmui.getCodeMirrorTextArea()){
					cmui.fireBlurAndChange();
				}
			}
		}
	}

	private static void fireKeyEvent(Object editor, NativeEvent event){
		if(cmmap.containsKey(editor)){
			if(cmmap.get(editor) instanceof CodeMirror){
				CodeMirror cmui = (CodeMirror) cmmap.get(editor);
				if(null != cmui.getCodeMirrorTextArea())
					DomEvent.fireNativeEvent(event, cmui.getCodeMirrorTextArea());
			}
		}
	}

	public void fireBlurAndChange() {
		ValueChangeEvent.fire(getCodeMirrorTextArea(), getCodeMirrorTextArea().getCurrentValue());
		getCodeMirrorTextArea().fireEvent(new BlurEvent());
	}

	public native void setValue(String text) /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		jsobject.setValue(text);
//		this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::fireBlurAndChange()();
	}-*/;

	public native String getValue() /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		var value = jsobject.getValue();
		return value;
	}-*/;

	public native void appendText(String text) /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		jsobject.replaceRange(text, jsobject.getCursor());
	}-*/;

	public native void appendText(String text, Object pos) /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		jsobject.replaceRange(text, pos);
	}-*/;

	public native int getCurrentLine() /*-{
	    var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		var choords = jsobject.cursorCoords(true);
		var result = jsobject.coordsChar(choords);
		return result.line;
	}-*/;

	public native String getCurrentTokenValue() /*-{
	    var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;

		var choords = jsobject.cursorCoords(true);
		var lineCh = jsobject.coordsChar(choords);
		var token = jsobject.getTokenAt(lineCh);

		return token.string;
	}-*/;


	public native String getLine(int n) /*-{
	    var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		return jsobject.getLine(n);
	}-*/;

	public native void setLine(int n, String text) /*-{
	    var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		return jsobject.setLine(n, text);
	}-*/;


	public native Object getCursorPos() /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		jsobject.getCursor();
	}-*/;


	public native String getOption(String option) /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		return jsobject.getOption(option);
	}-*/;

	public native void setOption(String option, String value) /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;
		jsobject.setOption(option, value);
	}-*/;

	public native void setSize(int width, int height) /*-{
		var jsobject = this.@net.datenwerke.gxtdto.client.codemirror.CodeMirror::jsobject;

		var valW = "width: " + width + "px;";
		var valH = "height: " + (height) + "px;";

		jsobject.getScrollerElement().setAttribute('style',valW + valH);

		jsobject.refresh();
	}-*/;


	public static CodeMirror fromTextArea(final CodeMirrorTextArea codeMirrorTextArea, final CodeMirrorConfig config){
		String id = codeMirrorTextArea.getTextAreaId();
		CodeMirror mirror = fromTextArea(id, config);

		if(codeMirrorTextArea instanceof CodeMirrorTextArea){
			mirror.codeMirrorTextArea = (CodeMirrorTextArea) codeMirrorTextArea;
		}

		cmmap.put(mirror.jsobject, mirror);

		return mirror;
	}

	private static native CodeMirror fromTextArea(String textAreaId, CodeMirrorConfig config) /*-{
		var area = $doc.getElementById(textAreaId); 

		var codeMirrorOptions = { 
			mode: config.@net.datenwerke.gxtdto.client.codemirror.CodeMirrorConfig::mode,
//			onKeyEvent: function(editor, event){@net.datenwerke.gxtdto.client.codemirror.CodeMirror::fireKeyEvent(Ljava/lang/Object;Lcom/google/gwt/dom/client/NativeEvent;)(editor, event);},
			lineNumbers: config.@net.datenwerke.gxtdto.client.codemirror.CodeMirrorConfig::lineNumbersVisible,
			matchBrackets: config.@net.datenwerke.gxtdto.client.codemirror.CodeMirrorConfig::matchBrackets
			};

		var cm = $wnd.CodeMirror.fromTextArea(area, codeMirrorOptions);
		cm.on("blur", function(editor){
			@net.datenwerke.gxtdto.client.codemirror.CodeMirror::fireOnBlurTextEditor(Ljava/lang/Object;)(editor);
		});
		
		return @net.datenwerke.gxtdto.client.codemirror.CodeMirror::new(Ljava/lang/Object;)(cm);
	}-*/;

	public CodeMirrorTextArea getCodeMirrorTextArea() {
		return codeMirrorTextArea;
	}
}
