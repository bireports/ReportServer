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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.hooks;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.json.SimpleFormFieldJson;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;

public interface FormFieldProviderHook extends Hook, HasValueChangeHandlers {

	public boolean consumes(Class<?> type, SimpleFormFieldConfiguration... configs);
	
	/**
	 * Used to construct fields from a text based (e.g., json) config.
	 * 
	 * @param type
	 * @param configs
	 * @return
	 */
	public boolean consumes(String type, SimpleFormFieldJson config);

	public void init(String name, SimpleForm form);
	
	public Widget createFormField();
	
	public void addFieldBindings(Object model, ValueProvider vp, Widget field);
	
	public void removeFieldBindings(Object model, Widget field);

	public Object getValue(Widget field);
	
	public String getStringValue(Widget field);
	
	public Widget reload(Widget field);

	public void setValue(Widget field, Object value);

	public boolean isDecorateable();

	

	
	
}
