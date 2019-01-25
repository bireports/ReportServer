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
 
 
package net.datenwerke.rs.terminal.client.terminal.hookers;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.widgets.SeparatorTextLabel;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultExtensionDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CreMessageDto;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;

public class MessageCommandProcessor implements CommandResultProcessorHook {

	@Override
	public void process(CommandResultDto result) {
		if(null != result.getExtensions()){
			for(CommandResultExtensionDto ext : result.getExtensions()){
				if(ext instanceof CreMessageDto){
					CreMessageDto extension = (CreMessageDto) ext;
					
					final DwWindow window = new DwWindow();
					window.setModal(true);
					window.setSize(extension.getWidth() == 0 ? 500 : extension.getWidth(), extension.getHeight() == 0 ? 300 : extension.getHeight());
					window.setBodyBorder(true);
					if(null != extension.getWindowTitle())
						window.setHeadingText(extension.getWindowTitle());
					
					VerticalLayoutContainer lc = new VerticalLayoutContainer();
					lc.setScrollMode(ScrollMode.AUTOY);
					window.add(lc,new MarginData(5));
					if(null != extension.getTitle()){
						SeparatorTextLabel title = SeparatorTextLabel.createHeadlineLarge(extension.getTitle());
						title.addStyleName("rs-cre-message-title");
						lc.add(title);
					}
					
					Widget w;
					if(null != extension.getHtml())
						w = new HTML(extension.getHtml());
					else
						w = SeparatorTextLabel.createText(extension.getText());
					w.addStyleName("rs-cre-message-msg");
					lc.add(w);
					
					DwTextButton ok = new DwTextButton(BaseMessages.INSTANCE.ok());
					ok.addSelectHandler(new SelectHandler() {
						@Override
						public void onSelect(SelectEvent event) {
							window.hide();
						}
					});
					window.addButton(ok);
					
					window.show();
				}
			}
		}
	}

}
