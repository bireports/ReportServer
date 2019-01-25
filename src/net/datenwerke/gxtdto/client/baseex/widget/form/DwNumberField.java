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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.form;

import net.datenwerke.gxtdto.client.theme.CssClassConstant;

import com.sencha.gxt.cell.core.client.form.NumberInputCell;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;

public class DwNumberField<N extends Number> extends NumberField<N> {

	@CssClassConstant
	public static final String CSS_NAME = "rs-number-field";
	
	public DwNumberField(NumberInputCell<N> cell, NumberPropertyEditor<N> editor) {
		super(cell, editor);
		initCss();
	}

	public DwNumberField(NumberPropertyEditor<N> editor) {
		super(editor);
		initCss();
	}

	private void initCss() {
		getElement().addClassName(CSS_NAME);
	}

	

}
