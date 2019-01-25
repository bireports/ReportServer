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
 
 
package net.datenwerke.rs.base.client.parameters.headline;

import java.util.Collection;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.widgets.SeparatorTextLabel;
import net.datenwerke.rs.base.client.parameters.headline.dto.HeadlineParameterDefinitionDto;
import net.datenwerke.rs.base.client.parameters.headline.dto.HeadlineParameterInstanceDto;
import net.datenwerke.rs.base.client.parameters.headline.dto.pa.HeadlineParameterDefinitionDtoPA;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfiguratorImpl;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * 
 *
 */
@Singleton
public class HeadlineConfigurator extends ParameterConfiguratorImpl<HeadlineParameterDefinitionDto, HeadlineParameterInstanceDto> {

	public Widget getEditComponentForDefinition(HeadlineParameterDefinitionDto definition) {
		SimpleForm form = SimpleForm.getInlineInstance();
		
		form.addField(String.class, HeadlineParameterDefinitionDtoPA.INSTANCE.value(), RsMessages.INSTANCE.headline()); 
		
		/* bind definition */
		form.bind(definition);
		
		return form;
	}

	public String getName() {
		return RsMessages.INSTANCE.headline(); 
	}

	@Override
	protected HeadlineParameterDefinitionDto doGetNewDto() {
		return new HeadlineParameterDefinitionDto();
	}
	
	@Override
	public boolean consumes(Class<? extends ParameterDefinitionDto> type) {
		return HeadlineParameterDefinitionDto.class.equals(type);
	}

	public ImageResource getIcon() {
		return BaseIcon.HEADER.toImageResource();
	}

	@Override
	public ParameterType getType(){
		return ParameterType.Separator;
	}


	@Override
	protected Widget doGetEditComponentForInstance(
			HeadlineParameterInstanceDto instance,
			Collection<ParameterInstanceDto> relevantInstances, HeadlineParameterDefinitionDto definition, boolean initial, int labelWidth, String executeReportToken) {
		return SeparatorTextLabel.createHeadlineLarge(definition.getValue());
	}


}
