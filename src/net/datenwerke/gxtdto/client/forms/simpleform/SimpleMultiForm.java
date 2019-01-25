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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.MarginData;


public class SimpleMultiForm extends SimpleForm {

	private final List<SimpleForm> subFormList = new ArrayList<SimpleForm>();
	
	public void addSubForm(SimpleForm form) {
		subFormList.add(form);
		
		Widget container = form.getWidget();
		container.setLayoutData(new MarginData(0));
		fieldWrapper.add(container);
		form.setOldWidget(container);
	}
	
	@Override
	public SimpleFormSubmissionCallback getCompositeSubmissionCallback() {
		List<SimpleFormSubmissionCallback> callbacks = new ArrayList<SimpleFormSubmissionCallback>();
		callbacks.add(super.getCompositeSubmissionCallback());
		for(SimpleForm sf : subFormList){
			callbacks.add(sf.getCompositeSubmissionCallback());
		}
		
		if(callbacks.size() == 1)
			return callbacks.get(0);
		
		return new ChainedCallbackWrapper(callbacks, this);
	}
	
	@Override
	protected void onAfterFirstAttach() {
		boolean afterFirst = isAfterInitialLayout;
		
		super.onAfterFirstAttach();

		if(afterFirst)
			return;
		
		/* method is not called on subforms */
		for(SimpleForm subForm : subFormList){
			subForm.onAfterFirstAttach();
		}
	}

	public static SimpleMultiForm createInlineInstance() {
		SimpleMultiForm form = new SimpleMultiForm();
		
		form.getButtonBar().clear();
		form.setHeaderVisible(false);
		form.setBodyBorder(false);
		form.setBorders(false);

		return form;
	}

	
}
