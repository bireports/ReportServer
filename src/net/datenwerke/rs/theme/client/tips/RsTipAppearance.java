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
 
 
package net.datenwerke.rs.theme.client.tips;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.theme.neptune.client.base.tips.Css3TipAppearance;

public class RsTipAppearance extends Css3TipAppearance {

	public interface Css3TipTemplate extends XTemplates {
		@XTemplate("<div class='{style.tipWrap} rs-tip-w'><div class='{style.tip} rs-tip'>" +
				"<div class='{style.tools} rs-tip-t'></div>" +
				"<div class='{style.headingWrap} rs-tip-h'><span class='{style.heading}'></span></div>" +
				"<div class='{style.textWrap} rs-tip-t'><span class='{style.text}'></span></div>" +
				"</div></div>")
		SafeHtml render(Css3TipStyle style);
	}

	private final Css3TipResources resources;
	private final Css3TipStyle style;
	private final Css3TipTemplate template = GWT.create(Css3TipTemplate.class);

	public RsTipAppearance() {
		this(GWT.<Css3TipResources>create(Css3TipResources.class));
	}

	public RsTipAppearance(Css3TipResources resources) {
		super(resources);
		this.resources = resources;
		style = resources.style();
		style.ensureInjected();
	}

	@Override
	public void render(SafeHtmlBuilder sb) {
		sb.append(template.render(style));
	}
}
