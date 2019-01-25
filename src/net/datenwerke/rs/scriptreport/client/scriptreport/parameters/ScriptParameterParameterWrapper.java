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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.parameters;

import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.helper.DefaultValueSetter;
import net.datenwerke.rs.core.client.parameters.helper.ParameterFieldWrapperForFrontend;

import com.sencha.gxt.widget.core.client.Component;

public class ScriptParameterParameterWrapper extends ParameterFieldWrapperForFrontend {

	private ScriptParameterClientEditComponent configurator;

	public ScriptParameterParameterWrapper(ParameterDefinitionDto definition, ParameterInstanceDto instance, Component component, int labelWidth ){
		super(definition, instance, component, labelWidth, null);
	}
	
	public ScriptParameterParameterWrapper(ParameterDefinitionDto definition, ParameterInstanceDto instance, Component component, int labelWidth, DefaultValueSetter defaultValueSetter){
		super(definition, instance, component, labelWidth, defaultValueSetter);
	}
	
	public void setScriptParameterConfigurator(
			ScriptParameterClientEditComponent scriptParameterClientEditComponent) {
		this.configurator = scriptParameterClientEditComponent;
	}

	public ScriptParameterClientEditComponent getConfigurator() {
		return configurator;
	}
}
