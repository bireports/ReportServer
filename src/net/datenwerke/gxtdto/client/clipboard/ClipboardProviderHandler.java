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
 
 
package net.datenwerke.gxtdto.client.clipboard;

import net.datenwerke.gxtdto.client.clipboard.processor.ClipboardCopyProcessor;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.ui.Widget;

public class ClipboardProviderHandler extends ClipboardHandler {

	private final ClipboardUiService clipboardService;
	private final ClipboardCopyProcessor itemProvider;

	public ClipboardProviderHandler(ClipboardUiService clipboardService, Widget target, ClipboardCopyProcessor itemProvider){
		super(target);
		this.clipboardService = clipboardService;
		this.itemProvider = itemProvider;
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		 int code = event.getNativeKeyCode();

		 if((67 == code || 99 == code) && event.isControlKeyDown() && (event.isAltKeyDown() || event.isShiftKeyDown()) )
			clipboardService.setClipboardItem(itemProvider.getItem());
	}
}
