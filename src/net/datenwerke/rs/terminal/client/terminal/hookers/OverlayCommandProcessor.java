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
 
 
package net.datenwerke.rs.terminal.client.terminal.hookers;

import net.datenwerke.gxtdto.client.overlay.OverlayService;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultExtensionDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CreOverlayDto;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;

import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.XElement;

public class OverlayCommandProcessor implements CommandResultProcessorHook {

	private final OverlayService overlayService;
	
	@Inject
	public OverlayCommandProcessor(OverlayService overlayService) {
		this.overlayService = overlayService;
	}



	@Override
	public void process(CommandResultDto result) {
		if(null != result.getExtensions()){
			for(CommandResultExtensionDto ext : result.getExtensions()){
				if(ext instanceof CreOverlayDto){
					CreOverlayDto extension = (CreOverlayDto) ext;
					
					XElement overlay = null;
					if(null == extension.getName())
						overlay = overlayService.overlay(extension.getText());
					else {
						if(extension.isRemove()){
							overlayService.remove(extension.getName());
							continue;
						}
							
						overlay = overlayService.overlay(extension.getName(), extension.getText());
					}
					
					StringBuilder styles = new StringBuilder();
					for(Object key : extension.getCssProperties().keySet())
						styles.append(key).append(": ").append(extension.getCssProperties().get((String) key)).append("; ");
					overlay.applyStyles(styles.toString());
				}
			}
		}
	}

}
