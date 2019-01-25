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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.forms.locale.FormsMessages;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStringValidator;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;

public class SFFCStringValidatorImplementation implements SFFCStringValidator {


	private boolean allowBlank;

	@Override
	public Validator<String> getValidator() {
		return new Validator<String>() {
			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				
				if(allowBlank && (null == value || value.isEmpty()))
					return null;
				
				if(! value.matches("[a-zA-Z][_a-zA-Z0-9]*") ){
					List<EditorError> list = new ArrayList<EditorError>();
					list.add(new DefaultEditorError(editor, FormsMessages.INSTANCE.fieldMustBeAlphaNumeric(), value));
					return list;
				}
				return null;
			}
		};
	}

	@Override
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}
}