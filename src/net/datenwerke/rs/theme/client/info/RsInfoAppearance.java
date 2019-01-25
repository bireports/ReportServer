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
 
 
package net.datenwerke.rs.theme.client.info;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.widget.core.client.info.Info.InfoAppearance;

public class RsInfoAppearance implements InfoAppearance {

	interface Template extends XTemplates {
		@XTemplate("<div class='rs-info-w'><div class='rs-info'></div></div>")
		SafeHtml render();
	}

	private final Template template;

	public RsInfoAppearance() {
		this.template = GWT.create(Template.class);
	}

	@Override
	public XElement getContentElement(XElement parent) {
		return parent.getFirstChildElement().getFirstChildElement().cast();
	}

	@Override
	public void render(SafeHtmlBuilder sb) {
		sb.append(template.render());
	}

}
