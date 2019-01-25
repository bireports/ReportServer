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

import net.datenwerke.rs.grideditor.client.grideditor.dto.MinLongValidatorDto;

import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator.MinNumberMessages;

/**
 * Dto Decorator for {@link MinLongValidatorDto}
 *
 */
public class MinLongValidatorDtoDec extends MinLongValidatorDto {


	private static final long serialVersionUID = 1L;

	public MinLongValidatorDtoDec() {
		super();
	}

	@Override
	public Validator<?> getValidator() {
		MinNumberValidator<Long> validator = new MinNumberValidator<Long>(getNumber());
		validator.setMessages(new MinNumberMessages() {
			@Override
			public String numberMinText(double min) {
				return getErrorMsg();
			}
		});
		return validator;
	}
}
