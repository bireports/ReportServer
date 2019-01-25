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
 
 
package net.datenwerke.rs.theme.client.field;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.theme.base.client.field.FileUploadDefaultAppearance;

public class RsUploadFieldAppearance extends FileUploadDefaultAppearance {

	public interface FileUploadTemplate extends XTemplates {
		@XTemplate("<div class='{style.wrap} rs-upl-field'></div>")
		SafeHtml render(FileUploadStyle style);
	}

	private final FileUploadResources resources;
	private final FileUploadStyle style;
	private final FileUploadTemplate template;

	public RsUploadFieldAppearance() {
		this(GWT.<FileUploadResources> create(FileUploadResources.class));
	}

	public RsUploadFieldAppearance(FileUploadResources resources) {
		this.resources = resources;
		this.style = this.resources.css();

		StyleInjectorHelper.ensureInjected(this.style, true);

		this.template = GWT.create(FileUploadTemplate.class);
	}

	@Override
	public void render(SafeHtmlBuilder sb) {
		sb.append(template.render(style));
	}
}
