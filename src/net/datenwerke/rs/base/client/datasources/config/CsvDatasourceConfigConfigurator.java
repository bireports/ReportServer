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
 
 
package net.datenwerke.rs.base.client.datasources.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel.ToolBarEnhancer;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCodeMirror;
import net.datenwerke.rs.base.client.datasources.dto.CsvDatasourceConfigDto;
import net.datenwerke.rs.base.client.datasources.dto.CsvDatasourceDto;
import net.datenwerke.rs.base.client.datasources.dto.DatasourceConnectorDto;
import net.datenwerke.rs.base.client.datasources.dto.FormatBasedDatasourceConfigDto;
import net.datenwerke.rs.base.client.datasources.dto.FormatBasedDatasourceDefinitionDto;
import net.datenwerke.rs.base.client.datasources.dto.decorator.DatasourceConnectorDtoDec;
import net.datenwerke.rs.base.client.datasources.locale.BaseDatasourceMessages;
import net.datenwerke.rs.core.client.datasourcemanager.config.DatasourceDefinitionConfigConfigurator;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerProviderDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.form.FieldLabel;


public class CsvDatasourceConfigConfigurator implements
		DatasourceDefinitionConfigConfigurator {
	
	private Map<String, Object> datamap = new HashMap<String, Object>();
	private CodeMirrorPanel wrapperArea;

	@Override
	public List<Widget> getFormfields(DatasourceDefinitionConfigDto config, DatasourceDefinitionDto datasourceDefinitionDto, final DatasourceSelectionField datasourceSelectionField) {
		ArrayList<Widget> widgets = new ArrayList<Widget>();
		if(datasourceDefinitionDto instanceof CsvDatasourceDto){
			DatasourceConnectorDto connector = ((CsvDatasourceDto)datasourceDefinitionDto).getConnector();
			if(connector instanceof DatasourceConnectorDtoDec){
				((DatasourceConnectorDtoDec) connector).addConnectorSpecificFormFields(widgets, config, datasourceDefinitionDto, datasourceSelectionField, this);
			}
			
			wrapperArea = (CodeMirrorPanel) SimpleForm.createFormlessField(String.class, new SFFCCodeMirror(){
				@Override
				public int getHeight() {
					return 150;
				}
				@Override
				public ToolBarEnhancer getEnhancer() {
					return null;
				}
				@Override
				public String getLanguage() {
					return "text/x-sql";
				}
				@Override
				public int getWidth() {
					return -1;
				}
				@Override
				public boolean lineNumbersVisible() {
					return true;
				}
			});
			
			if(null != ((CsvDatasourceConfigDto)config).getQueryWrapper())
				wrapperArea.setValue(((CsvDatasourceConfigDto)config).getQueryWrapper());
			
			((HasValueChangeHandlers<String>)wrapperArea).addValueChangeHandler(new ValueChangeHandler<String>() {
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					datasourceSelectionField.updateDatasourceConfig();
				}
			});

			widgets.add(new FieldLabel(wrapperArea, BaseDatasourceMessages.INSTANCE.csvQueryWrapperLabel()));
		}
		return widgets;
	}

	@Override
	public void inheritChanges(DatasourceDefinitionConfigDto config, DatasourceDefinitionDto datasourceDefinitionDto) {
		if(datasourceDefinitionDto instanceof CsvDatasourceDto){
			DatasourceConnectorDto connector = ((CsvDatasourceDto)datasourceDefinitionDto).getConnector();
			if(connector instanceof DatasourceConnectorDtoDec){
				((DatasourceConnectorDtoDec) connector).inheritConnectorSpecificChanges(config, datasourceDefinitionDto, this);
			}
			
			if(null == wrapperArea)
				((CsvDatasourceConfigDto)config).setQueryWrapper(null);
			else
				((CsvDatasourceConfigDto)config).setQueryWrapper(wrapperArea.getCurrentValue());
		}
	}

	@Override
	public DatasourceDefinitionConfigDto createConfigObject(DatasourceDefinitionDto datasourceDefinitionDto, DatasourceContainerProviderDto datasourceContainerProvider) {
		return new CsvDatasourceConfigDto();
	}
	
	@Override
	public boolean isValid(DatasourceContainerDto container) {
		if(! consumes(container.getDatasource(), container.getDatasourceConfig()))
			return false;
		return true;
	}

	@Override
	public boolean consumes(DatasourceDefinitionDto datasourceDefinitionDto, DatasourceDefinitionConfigDto datasourceConfig) {
		return null != datasourceConfig && datasourceConfig instanceof FormatBasedDatasourceConfigDto && datasourceDefinitionDto instanceof FormatBasedDatasourceDefinitionDto;
	}
	
	public Map<String, Object> getDatamap() {
		return datamap;
	}
	
	@Override
	public boolean isReloadOnDatasourceChange() {
		return false;
	}

}
