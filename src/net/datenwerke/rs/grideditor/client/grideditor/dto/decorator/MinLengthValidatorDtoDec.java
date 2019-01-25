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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.decorator;

import java.util.List;

import net.datenwerke.rs.grideditor.client.grideditor.dto.MinLengthValidatorDto;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator.MinLengthMessages;

/**
 * Dto Decorator for {@link MinLengthValidatorDto}
 *
 */
public class MinLengthValidatorDtoDec extends MinLengthValidatorDto {


	private static final long serialVersionUID = 1L;

	public MinLengthValidatorDtoDec() {
		super();
	}

	@Override
	public Validator<?> getValidator() {
		MinLengthValidator validator = new MinLengthValidator(getLength()){
			@Override
			public List<EditorError> validate(Editor<String> field, String value) {
				if(null == value)
					return createError(new DefaultEditorError(field, getMessages().minLengthText(minLength), value));
				return super.validate(field, value);
			}
		};
		validator.setMessages(new MinLengthMessages() {
			@Override
			public String minLengthText(int length) {
				return getErrorMsg();
			}
		});
		return validator;
	}
}
