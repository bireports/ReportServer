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
 
 
package net.datenwerke.rs.fileserver.client.fileserver;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel.ToolBarEnhancer;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorTextArea;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;

public class FileServerUiServiceImpl implements FileSeverUiService {

	private final FileServerDao fileDao;
	private final HookHandlerService hookHandlerService;

	@Inject
	public FileServerUiServiceImpl(
		FileServerDao fileDao,
		HookHandlerService hookHandlerService
		){
		this.fileDao = fileDao;
		this.hookHandlerService = hookHandlerService;
	}
	
	@Override
	public void editFileDirectly(final FileServerFileDto file){
		fileDao.loadFileDataAsString(file, new RsAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				editFileDirectly(file, result);
			}
		});
	}

	@Override
	public void editFileDirectly(final FileServerFileDto file, String data) {
		final DwWindow window = new DwWindow();
		window.setSize(800, 600);
		window.setHeadingText(file.getName());
		window.setOnEsc(false);
		window.setMaximizable(true);
		
		String mode = "none";
		if(file.getName().endsWith(".rs") || file.getName().endsWith(".groovy")){
			mode = "text/x-groovy";
		}
		if(file.getName().endsWith(".xml")){
			mode = "xml";
		}
		if(file.getName().endsWith(".cf")){
			mode = "xml";
		}
		
		final CodeMirrorPanel panel = new CodeMirrorPanel(mode, new ToolBarEnhancer() {
			
			@Override
			public void enhance(ToolBar toolbar, CodeMirrorPanel codeMirrorPanel) {
			}
			
			@Override
			public boolean allowPopup() {
				return false;
			}
		});
		
		TextArea ta = panel.getTextArea();
		if(ta instanceof CodeMirrorTextArea){
			CodeMirrorTextArea cmta = (CodeMirrorTextArea) ta;
			cmta.enableCodeMirrorPlugins(hookHandlerService);
			cmta.getCodeMirrorConfig().setLineNumbersVisible(true);
		}
		
		if(null != data)
			panel.getTextArea().setValue(data);
		
		panel.setNoOpacity();
		
		window.add(panel);

		DwTextButton cancel = new DwTextButton(BaseMessages.INSTANCE.cancel());
		cancel.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				window.hide();	
			}
		});
		window.addButton(cancel);
		
		DwTextButton submit = new DwTextButton(BaseMessages.INSTANCE.submit());
		submit.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				fileDao.updateFile(file, panel.getTextArea().getValue(), new RsAsyncCallback<Void>());
				window.hide();
			}
		});

		window.addButton(submit);
		
		window.show();		
	}
}
