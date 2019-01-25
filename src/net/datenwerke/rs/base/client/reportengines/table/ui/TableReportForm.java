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
 
 
package net.datenwerke.rs.base.client.reportengines.table.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.BeforeShowEvent;
import com.sencha.gxt.widget.core.client.event.BeforeShowEvent.BeforeShowHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.binding.FormBinding;
import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.base.client.datasources.BaseDatasourceDao;
import net.datenwerke.rs.base.client.datasources.config.DatabaseDatasourceConfigConfigurator.DatabaseSpecificFieldConfigExecution;
import net.datenwerke.rs.base.client.datasources.config.DatabaseDatasourceConfigConfigurator.DatabaseSpecificFieldConfigToolbar;
import net.datenwerke.rs.base.client.reportengines.table.TableReportUtilityDao;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.pa.TableReportDtoPA;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerProviderDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.rs.core.client.reportmanager.ui.forms.AbstractReportForm;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

/**
 * 
 *
 */
public class TableReportForm extends AbstractReportForm {
	
	private final TableReportUtilityDao tableReportDao;
	private final ToolbarService toolbarService;
	private final BaseDatasourceDao baseDatasourceDao;
	private final EnterpriseUiService enterpriseService;
	
	private DatasourceSelectionField metadataDatasourceFieldCreator;
	private CheckBox allowCubify;


	@Inject
	public TableReportForm(
		BaseDatasourceDao baseDatasourceDao,
		DatasourceUIService datasourceService,
		TableReportUtilityDao tableReportDao,
		ToolbarService toolbarService,
		EnterpriseUiService enterpriseService
		){
		super(datasourceService);
		this.baseDatasourceDao = baseDatasourceDao;
		this.tableReportDao = tableReportDao;
		this.toolbarService = toolbarService;
		this.enterpriseService = enterpriseService;
	}
	
	@Override
	protected void initializeForm(FormPanel form, VerticalLayoutContainer fieldWrapper) {
		super.initializeForm(form, fieldWrapper);
		
		/* metadata datasource */
		addMetadataDatasourceField(fieldWrapper, true);
		
		/* cubify */
		allowCubify = new CheckBox();

		if(enterpriseService.isEnterprise()){
			allowCubify.setBoxLabel(ReportmanagerMessages.INSTANCE.propertyAllowCubify());
			fieldWrapper.add(new FieldLabel(allowCubify, ReportmanagerMessages.INSTANCE.fieldLabelCubify()), new VerticalLayoutData(-1, -1, new Margins(0, 0, 5, 0)));
		}
	}
	
	@Override
	protected String getHeader() {
		return ReportmanagerMessages.INSTANCE.editTableReport() + " ("+getSelectedNode().getId()+")"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	


	@Override
	public void doInitFormBinding(FormBinding binding, final AbstractNodeDto model) {
		super.doInitFormBinding(binding, model);
		
		if(enterpriseService.isEnterprise())
			binding.addBinding(allowCubify, model, TableReportDtoPA.INSTANCE.allowCubification());
		
		metadataDatasourceFieldCreator.initFormBinding(new DatasourceContainerProviderDto() {
			
			TableReportDto tableReport = (TableReportDto) model;
			
			@Override
			public void setDatasourceContainer(DatasourceContainerDto datasourceContainer) {
				tableReport.setMetadataDatasourceContainer(datasourceContainer);
			}
			
			@Override
			public DatasourceContainerDto getDatasourceContainer() {
				return tableReport.getMetadataDatasourceContainer();
			}
		});
		
		
	}
		
	
	@Override
	protected void addDatasourceField(Container container,
			boolean displayConfigFields) {
		super.addDatasourceField(container, displayConfigFields);
		
		/* add specific field */
		datasourceFieldCreator.addSpecificDatasourceConfig(new DatabaseSpecificFieldConfigToolbar(){

			@Override
			public void enhance(ToolBar toolbar, final CodeMirrorPanel codeMirrorPanel){
				/* add parameter */
				DwTextButton selectParamBtn = toolbarService.createSmallButtonLeft(ReportmanagerMessages.INSTANCE.selectParamBtnLabel(), BaseIcon.TABLE_ADD);
				selectParamBtn.setArrowAlign(ButtonArrowAlign.RIGHT);
				toolbar.add(selectParamBtn);
				
				final Menu menu = new DwMenu();
				selectParamBtn.setMenu(menu);
				
				menu.addBeforeShowHandler(new BeforeShowHandler() {
					
					@Override
					public void onBeforeShow(BeforeShowEvent event) {
						menu.clear();
						ReportDto report = (ReportDto)getSelectedNode();
						for(ParameterDefinitionDto def : report.getParameterDefinitions()){
							final String key = def.getKey(); 
							MenuItem item = new DwMenuItem(def.getName());
							item.addSelectionHandler(new SelectionHandler<Item>() {
								@Override
								public void onSelection(SelectionEvent<Item> event) {
									String value = codeMirrorPanel.getTextArea().getValue();
									value = null == value ? "" : value;
									value += "${" + key + "}";
									codeMirrorPanel.getTextArea().setValue(value);
									datasourceFieldCreator.inheritChanges();
								}
							});
							menu.add(item);
						}
					}
				});
			}
		});
		
		datasourceFieldCreator.addSpecificDatasourceConfig(new DatabaseSpecificFieldConfigExecution(){

			@Override
			public void executeGetColumns(String query, final AsyncCallback<List<String>> callback) {
				tableReportDao.loadColumnDefinition((TableReportDto) getSelectedNode(), query, null, new RsAsyncCallback<List<ColumnDto>>(){
					@Override
					public void onSuccess(List<ColumnDto> result) {
						List<String> columns = new ArrayList<String>();
						for(ColumnDto col : result)
							columns.add(col.getName());
						callback.onSuccess(columns);
					}
					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
					
				});
			}
			
			@Override
			public void executeGetData(
					PagingLoadConfig loadConfig,
					String query,
					AsyncCallback<PagingLoadResult<ListStringBaseModel>> callback) {
				tableReportDao.loadData((TableReportDto) getSelectedNode(), loadConfig, query, callback);
			}
			
		});
	}
	
	private void addMetadataDatasourceField(Container fieldWrapper, boolean displayConfigFields) {
		metadataDatasourceFieldCreator = datasourceService.getSelectionField(fieldWrapper, displayConfigFields);
		
		metadataDatasourceFieldCreator.setLabel(ReportmanagerMessages.INSTANCE.metadataDataSource());
		metadataDatasourceFieldCreator.addSelectionField();
		metadataDatasourceFieldCreator.addDisplayDefaultButton();
		
		metadataDatasourceFieldCreator.addSpecificDatasourceConfig(new DatabaseSpecificFieldConfigExecution() {
			@Override
			public void executeGetColumns(String query,
					AsyncCallback<List<String>> callback) {
				baseDatasourceDao.loadColumnDefinition(metadataDatasourceFieldCreator.getDatasourceContainer(), query, callback);
			}

			@Override
			public void executeGetData(PagingLoadConfig loadConfig, String query,
					AsyncCallback<PagingLoadResult<ListStringBaseModel>> callback) {
				baseDatasourceDao.loadData(metadataDatasourceFieldCreator.getDatasourceContainer(), loadConfig, query, callback);
			}
		});
	}


	

}
