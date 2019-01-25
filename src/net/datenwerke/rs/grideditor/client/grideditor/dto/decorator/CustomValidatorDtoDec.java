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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.decorator;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.grideditor.client.grideditor.dto.CustomValidatorDto;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.form.validator.AbstractValidator;

/**
 * Dto Decorator for {@link CustomValidatorDto}
 *
 */
public class CustomValidatorDtoDec extends CustomValidatorDto {


	private static final long serialVersionUID = 1L;

	public CustomValidatorDtoDec() {
		super();
	}

	@Override
	public Validator<?> getValidator() {
		return new AbstractValidator<Object>() {

			@Override
			public List<EditorError> validate(Editor<Object> editor,
					Object value) {
				String val = getClientValidator();
				if(null == val || "".equals(val.trim()))
					return null;
				
				if(! doValidate(val, value) ){
					List<EditorError> errors = new ArrayList<EditorError>();
					errors.add(new DefaultEditorError(editor, getErrorMsg(), ""));
					return errors;
				}

				return null;
			}
		};
	}

	protected native boolean doValidate(String validator, Object value) /*-{
		var fun = Function("value", validator);
		return fun(value);
	}-*/;
}
