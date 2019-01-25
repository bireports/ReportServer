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

import net.datenwerke.rs.grideditor.client.grideditor.dto.MaxLengthValidatorDto;

import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator.MaxLengthMessages;

/**
 * Dto Decorator for {@link MaxLengthValidatorDto}
 *
 */
public class MaxLengthValidatorDtoDec extends MaxLengthValidatorDto {


	private static final long serialVersionUID = 1L;

	public MaxLengthValidatorDtoDec() {
		super();
	}


	@Override
	public Validator<?> getValidator() {
		MaxLengthValidator validator = new MaxLengthValidator(getLength());
		validator.setMessages(new MaxLengthMessages() {
			@Override
			public String maxLengthText(int length) {
				return getErrorMsg();
			}
		});
		return validator;
	}
}
