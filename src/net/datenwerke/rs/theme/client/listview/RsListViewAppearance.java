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
 
 
package net.datenwerke.rs.theme.client.listview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.theme.neptune.client.base.listview.Css3ListViewAppearance;

public class RsListViewAppearance<M> extends Css3ListViewAppearance<M> {

	public interface Css3ListViewTemplates extends XTemplates {
		@XTemplate("<div class='{view} rs-lview'></div>")
		SafeHtml renderBody(Css3ListViewStyle style);
		@XTemplate("<div class='{style.item} rs-lview-item'>{content}</div>")
		SafeHtml renderItem(Css3ListViewStyle style, SafeHtml content);
	}

	private final Css3ListViewTemplates template;
	private final Css3ListViewStyle style;

	public RsListViewAppearance() {
		this(GWT.<Css3ListViewResources>create(Css3ListViewResources.class));
	}
	public RsListViewAppearance(Css3ListViewResources resources) {
		super(resources);
		this.style = resources.css();
		this.style.ensureInjected();
		this.template = GWT.create(Css3ListViewTemplates.class);
	}

	@Override
	public void render(SafeHtmlBuilder builder) {
		builder.append(template.renderBody(style));
	}

	@Override
	public void renderItem(SafeHtmlBuilder sb, SafeHtml content) {
		sb.append(template.renderItem(style, content));
	}

	@Override
	public void onOver(XElement item, boolean over) {
		item.setClassName(style.over(), over);
		item.setClassName("rs-lview-over", over);
	}
}
