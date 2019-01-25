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
 
 
package net.datenwerke.rs.core.client.parameters.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;

public interface ParametersMessages extends Messages {

	public final ParametersMessages INSTANCE = GWT.create(ParametersMessages.class);
	
	String parameter();
	String divider();
	
	String mainPanelView_headline();
	String parameterMoved();
	String parameterRemoved();
	String parameterChanged();
	String keyLabel();
	String specialProperties();
	String parameterProperties();
	String parameterAdded();
	String key();
	String dependsOn();
	String generalPoperties();
	String editParameter();
	String reallyRemoveAllText();
	
	String propertyHidden();
	String propertyEditable();
	
	String setDefaultValues();
	
	String parameterInstancesUpdated();
	String updateAllInstances();
	String updateInstances();
	
	String updateInstancesMsg();
	String updateInstancesMsgTitle();

	String parameterDuplicated();
	String propertyDisplayInline();

	String optionsHeader();
	
	String reallyRemoveParameterText();
	String reallyRemoveParametersText(int size);
	
	String noParametersSelected();
	String propertyMandatory();
	String mandatoryParameterNotSelected(String name);
	String propertyLabelWidth();


}
