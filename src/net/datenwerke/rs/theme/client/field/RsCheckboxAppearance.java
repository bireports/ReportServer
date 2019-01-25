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

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.theme.neptune.client.base.field.Css3CheckBoxAppearance;

public class RsCheckboxAppearance extends Css3CheckBoxAppearance {

	@Override
	public void render(SafeHtmlBuilder sb, Boolean value, CheckBoxCell.CheckBoxCellOptions options) {
		String checkBoxId = XDOM.getUniqueId();

		String nameParam = options.getName() != null ? " name='" + options.getName() + "' " : "";
		String disabledParam = options.isDisabled() ? " disabled=true" : "";
		String readOnlyParam = options.isReadonly() ? " readonly" : "";
		String idParam = " id=" + checkBoxId;
		String typeParam = " type=" + type;
		String checkedParam = value ? " checked" : "";

		sb.appendHtmlConstant("<div class=\"" + style.wrap() + " rs-field-cb\">");
		sb.appendHtmlConstant("<input " + typeParam + nameParam + disabledParam + readOnlyParam + idParam + checkedParam + " />");
		sb.appendHtmlConstant("<label for=" + checkBoxId + " class=" + style.checkBoxLabel() + ">");
		if (options.getBoxLabel() != null) {
			sb.appendHtmlConstant(options.getBoxLabel());
		}
		sb.appendHtmlConstant("</label></div>");
	}

}
