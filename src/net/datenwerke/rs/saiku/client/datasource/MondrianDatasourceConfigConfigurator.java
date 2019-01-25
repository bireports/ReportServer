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
 
 
package net.datenwerke.rs.saiku.client.datasource;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.lists.SFFCSimpleDynamicList;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.rs.core.client.datasourcemanager.config.DatasourceDefinitionConfigConfigurator;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerProviderDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceConfigDto;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.saiku.SaikuDao;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadEvent;
import com.sencha.gxt.data.shared.loader.LoadHandler;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class MondrianDatasourceConfigConfigurator implements DatasourceDefinitionConfigConfigurator {
	
	private final SaikuDao saikuDao;

	private ComboBox<String> dropDown;

	private ListStore<String> store;

	@Inject
	public MondrianDatasourceConfigConfigurator(SaikuDao saikuDao){
		this.saikuDao = saikuDao;
	}
	
	@Override
	public Iterable<Widget> getFormfields(DatasourceDefinitionConfigDto config,
			final DatasourceDefinitionDto datasourceDefinitionDto,
			final DatasourceSelectionField datasourceSelectionField) {
		RpcProxy<ListLoadConfig, ListLoadResult<String>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<String>>() {
			@Override
			public void load(ListLoadConfig loadConfig, final AsyncCallback<ListLoadResult<String>> callback) {
				saikuDao.loadCubesFor((MondrianDatasourceDto)datasourceDefinitionDto, callback);	
			}
		};
		ListLoader<ListLoadConfig, ListLoadResult<String>> loader = new ListLoader<ListLoadConfig, ListLoadResult<String>>(proxy);
		store = new ListStore<String>(new BasicObjectModelKeyProvider<String>());
		
		loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, String, ListLoadResult<String>>(store));
		loader.addLoadHandler(new LoadHandler<ListLoadConfig, ListLoadResult<String>>(){
			@Override
			public void onLoad(
					LoadEvent<ListLoadConfig, ListLoadResult<String>> event) {
				boolean found = false;
				if(null != event.getLoadResult()){
					String currentValue = dropDown.getCurrentValue();
					if(null != currentValue && ! "".equals(currentValue.trim())){
						for(String cube : event.getLoadResult().getData()){
							if(cube.equals(currentValue)){
								found = true;
								break;
							}
						}
					}
				}
				if(! found)
					dropDown.clear();
			}
		});
		loader.load();
		
		dropDown = (ComboBox<String>) SimpleForm.createFormlessField(List.class, new SFFCSimpleDynamicList<String>(){

			@Override
			public Boolean isMultiselect() {
				return false;
			}

			@Override
			public ListStore<String> getStore() {
				return store;
			}

			@Override
			public ValueProvider<?, String> getStoreKeyForDisplay() {
				return new IdentityValueProvider<String>();
			}
			
		});
		
		dropDown.setWidth(227);
		
		if(null != ((MondrianDatasourceConfigDto)config).getCube())
			dropDown.setValue(((MondrianDatasourceConfigDto)config).getCube());
		
		dropDown.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				datasourceSelectionField.updateDatasourceConfig();
			}
		});
				
		return Arrays.asList(new Widget[]{
			new FieldLabel(dropDown, SaikuMessages.INSTANCE.cubeLabel())
		});
	}

	@Override
	public void inheritChanges(DatasourceDefinitionConfigDto config,
			DatasourceDefinitionDto datasourceDefinitionDto) {

		/* are we rendered */
		if(null == dropDown)
			((MondrianDatasourceConfigDto)config).setCube(null);
		else
			((MondrianDatasourceConfigDto)config).setCube(dropDown.getCurrentValue());
	}

	@Override
	public DatasourceDefinitionConfigDto createConfigObject(
			DatasourceDefinitionDto datasourceDefinitionDto,
			DatasourceContainerProviderDto datasourceContainerProvider) {

		return new MondrianDatasourceConfigDto();
	}

	@Override
	public boolean consumes(DatasourceDefinitionDto datasourceDefinitionDto, DatasourceDefinitionConfigDto datasourceConfig) {
		return null != datasourceConfig && MondrianDatasourceConfigDto.class.equals(datasourceConfig.getClass());
	}

	@Override
	public boolean isValid(DatasourceContainerDto datasourceContainer) {
		if(! consumes(datasourceContainer.getDatasource(), datasourceContainer.getDatasourceConfig()))
			return false;
		return true;
	}

	@Override
	public boolean isReloadOnDatasourceChange() {
		return true;
	}

}
