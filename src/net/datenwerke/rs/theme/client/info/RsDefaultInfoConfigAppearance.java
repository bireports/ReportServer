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
 
 
package net.datenwerke.rs.theme.client.info;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig.DefaultInfoConfigAppearance;

public class RsDefaultInfoConfigAppearance implements DefaultInfoConfigAppearance {

	  interface Template extends XTemplates {
	    @XTemplate("<div class='rs-info-title'>{title}</div><div class='rs-info-msg'>{message}</div>")
	    SafeHtml render(SafeHtml title, SafeHtml message);
	  }

	  private Template template;

	  public RsDefaultInfoConfigAppearance() {
	    template = GWT.create(Template.class);
	  }

	  @Override
	  public void render(SafeHtmlBuilder sb, SafeHtml title, SafeHtml message) {
	    sb.append(template.render(title, message));
	  }
}
