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
 
 
package net.datenwerke.rs.scripting.client.scripting.codemirror.plugin;

import java.util.List;

import net.datenwerke.gxtdto.client.codemirror.CodeMirrorTextArea;
import net.datenwerke.gxtdto.client.codemirror.hooks.CodeMirrorKeyboardHook;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.scripting.client.scripting.ScriptingDao;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.inject.Inject;

public class GroovyImportCompleter implements CodeMirrorKeyboardHook {

	@Inject private ScriptingDao sDao;
	
	@Override
	public boolean consumes(CodeMirrorTextArea codeMirrorTextArea) {
		return "text/x-groovy".equals(codeMirrorTextArea.getCodeMirrorConfig().getMode());
	}

	@Override
	public void handleKeyEvent(KeyPressEvent be, final CodeMirrorTextArea textArea) {
		if(be.isAnyModifierKeyDown() && be.getCharCode() == 'O' &&  be.isShiftKeyDown()){
			final int n = textArea.getCodeMirror().getCurrentLine();
			String text = textArea.getCodeMirror().getLine(n);
			String token = textArea.getCodeMirror().getCurrentTokenValue();
			if(null != text && text.trim().startsWith("import") && ! token.contains(".")){
				textArea.mask();
				sDao.getImportPathFor(token, new RsAsyncCallback<List<String>>(){
					@Override
					public void onSuccess(List<String> result) {
						textArea.unmask();
						if(null != result && ! result.isEmpty())
							textArea.getCodeMirror().setLine(n, "import " + result.get(0) + ";");
					}
					@Override
					public void onFailure(Throwable caught) {
						textArea.unmask();
					}
				});
			}
		}

	}

}
