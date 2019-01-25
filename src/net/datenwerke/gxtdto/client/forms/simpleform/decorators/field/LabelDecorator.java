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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.decorators.field;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.gxtdto.client.forms.layout.FormFieldLayoutConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.form.FieldLabel;


/**
 * 
 *
 */
public class LabelDecorator implements SimpleFormFieldDecorator {

	public static final String DECORATOR_ID = "LabelDecorator";
	
	protected final Map<String, FormFieldLayoutConfiguration> labelLookup = new HashMap<String, FormFieldLayoutConfiguration>();

	@Override
	public String getDecoratorId() {
		return DECORATOR_ID;
	}
	
	@Override
	public void configureFieldAfterLayout(SimpleForm simpleForm, Widget widget, String key) {
	}
	
	@Override
	public void configureFieldOnLoad(SimpleForm form, Widget field, String key) {
		
	}
	
	public void addLabel(String key, FormFieldLayoutConfiguration config){
		labelLookup.put(key, config);
	}

	public FormFieldLayoutConfiguration getConfigFor(String key) {
		return labelLookup.get(key);
	}

	@Override
	public Widget adjustFieldForDisplay(SimpleForm simpleForm,
			Widget formField, String fieldKey) {
		FormFieldLayoutConfiguration config = labelLookup.get(fieldKey);
		if(null != config && config.isHasLabel() && ((null != config.getLabelText() && ! "".equals(config.getLabelText())) || null != config.getLabelHtml())){
			FieldLabel fieldLabel = new FieldLabel(formField, config.getLabelText());
			
			if( null != config.getLabelHtml() )
				fieldLabel.setHTML(config.getLabelHtml());
			fieldLabel.setLabelAlign(config.getLabelAlign());
			fieldLabel.setLabelWidth(config.getLabelWidth());
			
			return fieldLabel;
		}
		
		return formField;
	}

}
