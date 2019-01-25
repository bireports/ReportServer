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
 
 
package net.datenwerke.gf.client.upload;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Hidden;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FileUploadField;

public class HtmlUploadFieldContainer extends HtmlLayoutContainer {

	private FileUploadField baseField;
	private Hidden nameField;

	public HtmlUploadFieldContainer(SafeHtml html) {
		super(html);
	}

	public void setNameField(Hidden xhrNameField) {
		this.nameField=xhrNameField;
	}

	public void setBaseField(FileUploadField field) {
		this.baseField = field;
	}
	
	public String getUploadFileName(){
		String name = nameField.getValue();
		if(null == name || "".equals(name.trim()))
			name = baseField.getValue();
		return null != name && ! "".equals(name.trim()) ? name : null;
	}
	

}
