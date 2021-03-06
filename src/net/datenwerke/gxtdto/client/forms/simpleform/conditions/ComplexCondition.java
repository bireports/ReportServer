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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.conditions;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 *
 */
public class ComplexCondition implements SimpleFormCondition {

	private final String fieldKey;
	private final SimpleFormCondition condition;
	
	public ComplexCondition(String fieldKey, SimpleFormCondition condition){
		this.fieldKey = fieldKey;
		this.condition = condition;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public SimpleFormCondition getCondition() {
		return condition;
	}

	@Override
	public boolean isMet(Widget formField,
			FormFieldProviderHook responsibleHook, SimpleForm form) {
		return getCondition().isMet(form.getField(fieldKey), form.getResponsibleHook(fieldKey), form);
	}
	
}
