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
 
 
package net.datenwerke.rs.terminal.client.terminal;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.helper.DisplayHelper;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;
import net.datenwerke.rs.terminal.client.terminal.ui.TerminalWindow;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * 
 *
 */
public class TerminalUIServiceImpl implements TerminalUIService {

	private final HookHandlerService hookHandler;
	private final DisplayHelper displayHelper;
	private final Provider<TerminalWindow> terminalWindowProvider;
	
	private boolean initialized;
	
	@Inject
	public TerminalUIServiceImpl(
		HookHandlerService hookHandler,
		DisplayHelper displayHelper,
		Provider<TerminalWindow> terminalWindowProvider
		){
		
		/* store objects */
		this.hookHandler = hookHandler;
		this.displayHelper =displayHelper;
		this.terminalWindowProvider = terminalWindowProvider;
	}
	
	@Override
	public void initTerminal() {
		if(isInitialized())
			return;
		
		initialized = true;
		
		Event.addNativePreviewHandler(new NativePreviewHandler(){
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if(event.isCanceled() || event.isConsumed())
					return;
				
				if(event.getTypeInt() == Event.ONKEYUP){
					boolean ctrlKey = event.getNativeEvent().getCtrlKey();
					boolean altKey = event.getNativeEvent().getAltKey();
					if(ctrlKey && altKey){
						int keyCode = event.getNativeEvent().getKeyCode();
						if(keyCode == 'T' || keyCode == 't'){
							if(event.isConsumed())
								return;
							event.consume();
							displayTerminalWindow();
						}
					}
				}
			}
			
		}); 
	}
	
	@Override
	public boolean isInitialized(){
		return initialized;
	}

	@Override
	public void displayTerminalWindow() {
		if(! isInitialized())
			initTerminal();
		
		DwWindow window = terminalWindowProvider.get();
		window.show();
	}

	@Override
	public void processExternalResult(CommandResultDto result) {
		if(null == result)
			return;
		for(CommandResultProcessorHook processor : hookHandler.getHookers(CommandResultProcessorHook.class))
			processor.process(result);
	}

	@Override
	public void displayResult(CommandResultDto result) {
		DwWindow window = new DwWindow();
		window.setSize(640,480);
		window.setOnEsc(true);
		
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		container.setScrollMode(ScrollMode.AUTO);
		container.add(new HTML(displayHelper.format(result)));
		
		window.add(container);
		window.show();
	}

}
