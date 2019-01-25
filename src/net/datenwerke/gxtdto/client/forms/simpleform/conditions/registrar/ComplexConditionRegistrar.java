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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.conditions.registrar;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.SimpleFormAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.ComplexCondition;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 *
 */
public class ComplexConditionRegistrar implements ConditionRegistrar {

	private final SimpleFormAction action;
	private final ComplexCondition[] conditions;
	private final SimpleForm form;
	
	public ComplexConditionRegistrar(SimpleFormAction action,
			ComplexCondition[] conditions, SimpleForm form) {
		super();
		this.action = action;
		this.conditions = conditions;
		this.form = form;
	}

	@SuppressWarnings("unchecked")
	public void registerCondition() {
		if(null == conditions)
			return;
		
		/* attach listneres */
		for(ComplexCondition condition : conditions){
			FormFieldProviderHook responsibleHook = form.getResponsibleHook(condition.getFieldKey());
	
			/* add listener */
			responsibleHook.addValueChangeHandler(new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					checkCondition();
				}
			});
		}
		
		/* first time check */
		checkCondition();
	}
	
	protected void checkCondition(){
		/* create flag to store if a condition failed */
		boolean conditionsFailed = false;
		
		/* test each condition */
		for(ComplexCondition condition : conditions){
			/* get formfield and hook */
			Widget myFormField = form.getField(condition.getFieldKey());
			FormFieldProviderHook myResponsibleHook = form.getResponsibleHook(condition.getFieldKey());
			
			/* test condition */
			if(! condition.getCondition().isMet(myFormField, myResponsibleHook, form)){
				conditionsFailed = true;
				break;
			}
		}
		
		if(! conditionsFailed)
			action.onSuccess(form);
		else
			action.onFailure(form);
	}

}
