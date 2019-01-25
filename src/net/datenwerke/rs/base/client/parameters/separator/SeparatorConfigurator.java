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
 
 
package net.datenwerke.rs.base.client.parameters.separator;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.base.client.parameters.separator.dto.SeparatorParameterDefinitionDto;
import net.datenwerke.rs.base.client.parameters.separator.dto.SeparatorParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfiguratorImpl;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
@Singleton
public class SeparatorConfigurator extends ParameterConfiguratorImpl<SeparatorParameterDefinitionDto, SeparatorParameterInstanceDto> {

	private final Resources resources = GWT.create(Resources.class);
	
	interface Resources extends ClientBundle {
		@Source("separator.css")
		Style css();    
	}

	interface Style extends CssResource {
		@ClassName("rs-parameter-horizontal-separator")
		String horizontalSeparator();
	}	
	
	public SeparatorConfigurator(){
		super();
		
		resources.css().ensureInjected();
	}	
	
	@Override
	public Widget getEditComponentForDefinition(SeparatorParameterDefinitionDto definition, ReportDto report) {
		SimpleForm form = SimpleForm.getInlineInstance();
		
		/* bind definition */
		form.bind(definition);
		
		return form;
	}


	@Override
	public Widget doGetEditComponentForInstance(SeparatorParameterInstanceDto instance, Collection<ParameterInstanceDto> relevantInstances, final SeparatorParameterDefinitionDto definition, boolean initial, int labelWidth, String executeReportToken) {
		Label dummy = new Label();
		dummy.setStyleName(resources.css().horizontalSeparator());
		return dummy;
	}

	public String getName() {
		return RsMessages.INSTANCE.separator();
	}

	@Override
	protected SeparatorParameterDefinitionDto doGetNewDto() {
		return new SeparatorParameterDefinitionDto();
	}

	@Override
	public boolean consumes(Class<? extends ParameterDefinitionDto> type) {
		return SeparatorParameterDefinitionDto.class.equals(type);
	}
	
	public ImageResource getIcon() {
		return BaseIcon.DISCONNECT.toImageResource();
	}

	@Override
	public ParameterType getType(){
		return ParameterType.Separator;
	}

}
