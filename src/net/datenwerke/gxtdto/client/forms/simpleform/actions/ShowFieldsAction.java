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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.actions;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;

import com.google.gwt.user.client.ui.Widget;

public class ShowFieldsAction implements SimpleFormAction {

	
	private final String[] toHide;
	private final String[] toShow;

	public ShowFieldsAction(String[] toHide, String[] toShow){
		this.toHide = toHide;
		this.toShow = toShow;
	}


	public void onSuccess(SimpleForm form) {
		hideFields(form, toHide);
		showFields(form, toShow);
	}
	
	public void onFailure(SimpleForm form) {
	}

	protected void hideFields(SimpleForm form, String... allFields) {
		for(String key : allFields){
			Widget field = form.getDisplayedField(key);
			if(null == field)
				continue;
			
			field.setVisible(false);
		}
		
		form.updateFormLayout();
	}
	
	protected void showFields(SimpleForm form, String... allFields) {
		for(String key : allFields){
			Widget field = form.getDisplayedField(key);
			if(null == field)
				continue;

			field.setVisible(true);
		}
		
		form.updateFormLayout();
	}
	

}
