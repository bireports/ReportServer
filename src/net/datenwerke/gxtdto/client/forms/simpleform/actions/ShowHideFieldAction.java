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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.actions;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;

public class ShowHideFieldAction implements SimpleFormAction {

	private final String fieldKey;
	
	public ShowHideFieldAction(String fieldKey){
		this.fieldKey = fieldKey;
	}


	public void onSuccess(SimpleForm form) {
		Widget field = form.getDisplayedField(fieldKey);
		if(null == field)
			return;
		if(field instanceof Component)
			((Component)field).show();
		else
			field.setVisible(true);
		
		form.updateFormLayout();
	}
	
	public void onFailure(SimpleForm form) {
		Widget field = form.getDisplayedField(fieldKey);
		if(null == field)
			return;
		if(field instanceof Component)
			((Component)field).hide();
		else
			field.setVisible(false);
		
		form.updateFormLayout();
	}

	

}
