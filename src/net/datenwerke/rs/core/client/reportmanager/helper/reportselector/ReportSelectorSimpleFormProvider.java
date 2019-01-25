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
 
 
package net.datenwerke.rs.core.client.reportmanager.helper.reportselector;

import java.util.ArrayList;
import java.util.Collection;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportContainerDto;
import net.datenwerke.rs.core.client.reportmanager.helper.reportselector.ReportSelectionDialog.RepositoryProviderConfig;
import net.datenwerke.rs.core.client.reportmanager.hookers.ReportCatalogRepositoryProvider;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class ReportSelectorSimpleFormProvider extends FormFieldProviderHookImpl {

	
	
	@Override
	public boolean doConsumes(Class<?> type,
			SimpleFormFieldConfiguration... configs) {
		return type == ReportSelectionDialog.class;
	}

	@Override
	public Widget createFormField() {
		ReportSelectionField field = new ReportSelectionField();
	
		final SFFCReportSelection config = getConfig();
		if(null != config){
			Collection<RepositoryProviderConfig> configs = new ArrayList<RepositoryProviderConfig>();
			if(null != config.getRepositoryConfigs())
				configs.addAll(config.getRepositoryConfigs());
			configs.add(new ReportCatalogRepositoryProvider.Config() {
				@Override
				public boolean includeVariants() {
					return config.showVariantsInCatalog();
				}
				
				@Override
				public boolean showCatalog() {
					return config.showCatalog();
				}
			});
			field.setRepositoryConfigs(configs.toArray(new RepositoryProviderConfig[]{}));
		}
		
		field.addValueChangeHandler(new ValueChangeHandler<ReportContainerDto>() {
			@Override
			public void onValueChange(ValueChangeEvent<ReportContainerDto> event) {
				ValueChangeEvent.fire(ReportSelectorSimpleFormProvider.this, event.getValue());
			}
		});
		
		return field;
	}
	
	
	private SFFCReportSelection getConfig() {
		for(SimpleFormFieldConfiguration conf : configs)
			if(conf instanceof SFFCReportSelection)
				return (SFFCReportSelection) conf;
		return null;
	}

	public Object getValue(Widget field){
		return ((ReportSelectionField)field).getValue();
	}
}
