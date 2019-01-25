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
 
 
package net.datenwerke.rs.theme.client.fieldset;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.theme.neptune.client.base.fieldset.Css3FieldSetAppearance;

public class RsFieldSetAppearance extends Css3FieldSetAppearance {

	public interface Template extends XTemplates {
		@XTemplate(source = "RsFieldSet.html")
		SafeHtml render(FieldSetStyle style, boolean isGecko);
	}

	private Css3FieldSetStyle style;
	private Template template;

	public RsFieldSetAppearance() {
		this(GWT.<Css3FieldSetResources>create(Css3FieldSetResources.class));
	}

	public RsFieldSetAppearance(Css3FieldSetResources resources) {
		super(resources);
		
		this.style = resources.css();
		   
	    this.template = GWT.create(Template.class);
	}

	@Override
	public void render(SafeHtmlBuilder sb) {
		sb.append(template.render(style, GXT.isGecko()));
	}
}
