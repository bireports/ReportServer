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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.helper.forms.simpleform;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.rs.base.client.datasources.config.DatabaseDatasourceConfigConfigurator.DatabaseSpecificFieldConfigExecution;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerProviderDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;

/**
 * 
 *
 */
public class DatasourceSimpleFormProvider extends FormFieldProviderHookImpl {

	private final DatasourceUIService datasourceService;
	
	private DatasourceSelectionField datasourceFieldCreator;
	
	@Inject
	public DatasourceSimpleFormProvider(
		DatasourceUIService datasourceService
		){
		
		/* store objects */
		this.datasourceService = datasourceService;
	}
	
	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return DatasourceContainerDto.class.equals(type);
	}

	public Widget createFormField() {
		Container wrapper = new VerticalLayoutContainer();
		
		datasourceFieldCreator = datasourceService.getSelectionField(wrapper, ! hasSupressConfigConfig());
		
		FieldLabel label = new FieldLabel();
		if(null != form.getSField(name).getFieldLayoutConfig().getLabelText())
			label.setText(form.getSField(name).getFieldLayoutConfig().getLabelText());
		if(null != form.getSField(name).getFieldLayoutConfig().getLabelAlign())
			label.setLabelAlign(form.getSField(name).getFieldLayoutConfig().getLabelAlign());
		datasourceFieldCreator.setFieldLabel(label);
		
		DatabaseSpecificFieldConfigExecution specificExecutionConfig = getSpecificExecutionConfig();
		if(null!=specificExecutionConfig)
			datasourceFieldCreator.addSpecificDatasourceConfig(specificExecutionConfig);
		
		datasourceFieldCreator.addSelectionField();
		if(! hasSupressDefaultConfig())
			datasourceFieldCreator.addDisplayDefaultButton();

		datasourceFieldCreator.addValueChangeHandler(new ValueChangeHandler<DatasourceContainerDto>() {
			@Override
			public void onValueChange(ValueChangeEvent<DatasourceContainerDto> event) {
				ValueChangeEvent.fire(DatasourceSimpleFormProvider.this, event.getValue());
			}
		});
		
		return wrapper;
	}
	
	private DatabaseSpecificFieldConfigExecution getSpecificExecutionConfig() {
		for(SimpleFormFieldConfiguration config : getConfigs())
			if(config instanceof SFFCDatasourceSpecificConfig)
				return ((SFFCDatasourceSpecificConfig)config).getConfigExecution();
		return null;
	}

	public void addFieldBindings(Object model, ValueProvider vp, Widget field) {
		/* get datasource container */
		DatasourceContainerProviderDto datasourceContainerProvider;
		
		SFFCDatasourceMultipleContainerConfig multipleContainerConfig = getMultipleContainerConfig();
		
		if(null != multipleContainerConfig)
			datasourceContainerProvider = multipleContainerConfig.getSpecialDatasourceContainer((DwModel)model);
		else
			datasourceContainerProvider = (DatasourceContainerProviderDto)model;

		/* ask creator to init form binding */
		datasourceFieldCreator.initFormBinding(datasourceContainerProvider);
	}
	
	
	
	private SFFCDatasourceMultipleContainerConfig getMultipleContainerConfig() {
		for(SimpleFormFieldConfiguration config : getConfigs())
			if(config instanceof SFFCDatasourceMultipleContainerConfig)
				return (SFFCDatasourceMultipleContainerConfig) config;
		return null;
	}

	
	private boolean hasSupressConfigConfig() {
		for(SimpleFormFieldConfiguration config : getConfigs())
			if(config instanceof SFFCDatasourceSuppressConfig)
				return true;
		return false;
	}
	
	private boolean hasSupressDefaultConfig() {
		for(SimpleFormFieldConfiguration config : getConfigs())
			if(config instanceof SFFCDatasourceSuppressDefault)
				return true;
		return false;
	}

	public Object getValue(Widget field){
		return datasourceFieldCreator.getDatasourceContainer();
	}

	public void removeFieldBindings(Object model, Widget field) {
		throw new RuntimeException("not yet implemented"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isDecorateable() {
		return false;
	}

}
