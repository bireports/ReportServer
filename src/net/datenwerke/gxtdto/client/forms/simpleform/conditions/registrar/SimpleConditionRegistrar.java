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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.conditions.registrar;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.SimpleFormAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.SimpleFormCondition;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class SimpleConditionRegistrar implements ConditionRegistrar {

	private final String fieldKey;
	private final SimpleFormCondition condition;
	private final SimpleFormAction action;
	private final SimpleForm form;
	
	public SimpleConditionRegistrar(String fieldKey,
			SimpleFormCondition condition, SimpleFormAction action,
			SimpleForm form) {
		super();
		this.fieldKey = fieldKey;
		this.condition = condition;
		this.action = action;
		this.form = form;
	}



	@SuppressWarnings("unchecked")
	public void registerCondition() {
		/* get field */
		final Widget formField = form.getField(fieldKey);
		final FormFieldProviderHook responsibleHook = form.getResponsibleHook(fieldKey);

		/* add a selection listener */
		responsibleHook.addValueChangeHandler(new ValueChangeHandler() {
			@Override
			public void onValueChange(ValueChangeEvent event) {
				checkCondition(formField, responsibleHook);
			}
		});
		
		/* check condition */
		checkCondition(formField, responsibleHook);
	}
	
	protected void checkCondition(Widget field, FormFieldProviderHook responsibleHook){
		/* ask condition if it is met */
		if(condition.isMet(field, responsibleHook, form))
			action.onSuccess(form);
		else
			action.onFailure(form);
	}

}
