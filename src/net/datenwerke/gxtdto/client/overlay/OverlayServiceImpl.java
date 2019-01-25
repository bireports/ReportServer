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
 
 
package net.datenwerke.gxtdto.client.overlay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.dom.XElement;

public class OverlayServiceImpl implements OverlayService {

	private Set<XElement> unnamedOverlays = new HashSet<XElement>();
	private Map<String, XElement> overlays = new HashMap<String, XElement>();
	
	@Override
	public XElement overlay(String text){
		return overlay(null, text);
	}
	
	@Override
	public XElement overlay(String name, String text){
		return overlay(null, text, null, null);
	}
	
	@Override
	public XElement overlay(String name, String text, String top, String left){
		SafeHtmlBuilder sb = new SafeHtmlBuilder().appendHtmlConstant("<div>").appendHtmlConstant(text).appendHtmlConstant("</div>");
		
		XElement overlay = XDOM.create(sb.toSafeHtml());
		
		if(null != top)
			overlay.setAttribute("top", top);
		if(null != left)
			overlay.setAttribute("left", left);
		overlay.setAttribute("z-index", "1000");
		overlay.setAttribute("position", "absolute");
		
		RootPanel.get().getElement().appendChild(overlay);
		
		if(null == name)
			unnamedOverlays.add(overlay);
		else {
			if(overlays.containsKey(name))
				remove(name);
			overlays.put(name, overlay);
		}
		
		return overlay;
	}
	
	@Override
	public void remove(String name) {
		if(overlays.containsKey(name)){
			XElement overlay = overlays.remove(name);
			RootPanel.get().getElement().removeChild(overlay);
		}
	}

	@Override
	public void remove(XElement overlay){
		unnamedOverlays.remove(overlay);
		if(overlay.isConnected())
			RootPanel.get().getElement().removeChild(overlay);
	}

}
