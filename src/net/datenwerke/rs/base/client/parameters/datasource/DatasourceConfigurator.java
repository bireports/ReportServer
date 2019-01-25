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
 
 
package net.datenwerke.rs.base.client.parameters.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;

import net.datenwerke.gxtdto.client.dialog.error.SimpleErrorDialog;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.ShowHideFieldAction;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.SimpleFormAction;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.SimpleFormSuccessAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.ComplexCondition;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.CompositeAndCondition;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.CompositeOrCondition;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.FieldChanged;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.FieldEquals;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCStaticDropdownList;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.lists.SFFCDynamicListInPopup;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.lists.SFFCEditableDropDown;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.rs.base.client.parameters.datasource.dto.BoxLayoutModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.BoxLayoutPackModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDataDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDefinitionDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterInstanceDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.ModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.MultiSelectionModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.SingleSelectionModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.pa.DatasourceParameterDataDtoPA;
import net.datenwerke.rs.base.client.parameters.datasource.dto.pa.DatasourceParameterDefinitionDtoPA;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfiguratorImpl;
import net.datenwerke.rs.core.client.parameters.dto.DatatypeDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterProposalDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
public class DatasourceConfigurator extends ParameterConfiguratorImpl<DatasourceParameterDefinitionDto, DatasourceParameterInstanceDto> {

	private final DatasourceParameterDao datasourceParamDao;
	private final Provider<DatasourceEditComponentForInstance> editComponentForInstanceProvider;
	private final DatasourceParameterUiService dsParamService;
	private String singleDefaultValuesKey;
	private String multiDefaultValuesKey;
	private ListStore<DatasourceParameterDataDto> parameterDataStore;
	private DatasourceEditComponentForInstance editComponentConfigurator;
	private ScheduledCommand reloadCommand;
	private EnterpriseUiService entpriseService;
	
	
	@Inject
	public DatasourceConfigurator(
			DatasourceParameterDao rpcService,
		Provider<DatasourceEditComponentForInstance> editComponentForInstanceProvider,
		DatasourceParameterUiService dsParamService,
		EnterpriseUiService entpriseService
		){
		
		/* store objects */
		this.datasourceParamDao = rpcService;
		this.editComponentForInstanceProvider = editComponentForInstanceProvider;
		this.dsParamService = dsParamService;
		this.entpriseService = entpriseService;
	}
	

	public String getName() {
		return RsMessages.INSTANCE.datasourceParameter(); 
	}

	@Override
	protected DatasourceParameterDefinitionDto doGetNewDto() {
		DatasourceParameterDefinitionDto par = new DatasourceParameterDefinitionDto();
		par.setHeight(1);
		par.setWidth(550);
		return par;
	}
	
	@Override
	public boolean consumes(Class<? extends ParameterDefinitionDto> type) {
		return DatasourceParameterDefinitionDto.class.equals(type);
	}

	public ImageResource getIcon() {
		return BaseIcon.DATABASE.toImageResource();
	}

	public Widget getEditComponentForDefinition(final DatasourceParameterDefinitionDto definition) {
		final SimpleForm form = SimpleForm.getInlineInstance();

		/* data source */
		String dataOriginDatasourceKey = form.addField(
			DatasourceContainerDto.class,
			DatasourceParameterDefinitionDtoPA.INSTANCE.datasourceContainer(), RsMessages.INSTANCE.datasource() 
		);
		
		String postProcessKey = "";
		if(dsParamService.isAllowPostProcessing()){
			form.setLabelAlign(LabelAlign.TOP);
			form.setLabelWidth(100);
			postProcessKey = form.addField(String.class, DatasourceParameterDefinitionDtoPA.INSTANCE.postProcess(), RsMessages.INSTANCE.datasourceParameterPostProcess(), new SFFCTextAreaImpl(){
				@Override
				public int getHeight() {
					return 75;
				}
			});
		}
		
		form.setLabelAlign(LabelAlign.TOP);
		form.beginFloatRow();
		form.setFieldWidth(210);
		form.addField(Integer.class, DatasourceParameterDefinitionDtoPA.INSTANCE.width(), BaseMessages.INSTANCE.width());
		form.addField(Integer.class, DatasourceParameterDefinitionDtoPA.INSTANCE.height(), BaseMessages.INSTANCE.height());
		form.endRow();
		
		/* add mode */
		form.beginFloatRow();
		form.setFieldWidth(210);
		final String modeKey = form.addField(
			List.class, DatasourceParameterDefinitionDtoPA.INSTANCE.mode(), RsMessages.INSTANCE.seletionMode(), 
			new SFFCStaticDropdownList<ModeDto>() {
				public Map<String, ModeDto> getValues() {
					Map<String, ModeDto> map = new HashMap<String, ModeDto>();
					
					map.put(RsMessages.INSTANCE.singleSelect(), ModeDto.Single);
					map.put(RsMessages.INSTANCE.multiSelect(), ModeDto.Multi);
					
					return map;
				}
		});
		
		final String singleSelectionModeKey = form.addField(
				List.class, DatasourceParameterDefinitionDtoPA.INSTANCE.singleSelectionMode(), RsMessages.INSTANCE.singleSeletionMode(), 
				new SFFCStaticDropdownList<SingleSelectionModeDto>() {
					public Map<String, SingleSelectionModeDto> getValues() {
						Map<String, SingleSelectionModeDto> map = new HashMap<String, SingleSelectionModeDto>();
						
						map.put(RsMessages.INSTANCE.dropdown(), SingleSelectionModeDto.Dropdown);
						map.put(RsMessages.INSTANCE.popup(), SingleSelectionModeDto.Popup);
						map.put(RsMessages.INSTANCE.radio(), SingleSelectionModeDto.Radio);
						map.put(RsMessages.INSTANCE.listbox(), SingleSelectionModeDto.Listbox);
						
						return map;
					}
			});
		
		final String multiSelectionModeKey = form.addField(
				List.class, DatasourceParameterDefinitionDtoPA.INSTANCE.multiSelectionMode(), RsMessages.INSTANCE.multiSeletionMode(), 
				new SFFCStaticDropdownList<MultiSelectionModeDto>() {
					public Map<String, MultiSelectionModeDto> getValues() {
						Map<String, MultiSelectionModeDto> map = new HashMap<String, MultiSelectionModeDto>();
						
						map.put(RsMessages.INSTANCE.popup(), MultiSelectionModeDto.Popup);
						map.put(RsMessages.INSTANCE.checkbox(), MultiSelectionModeDto.Checkbox);
						map.put(RsMessages.INSTANCE.listbox(), MultiSelectionModeDto.Listbox);
						
						return map;
					}
			});
		form.endRow();
		
		form.beginFloatRow();
		
		
		final String boxModeKey = form.addField(
				List.class, DatasourceParameterDefinitionDtoPA.INSTANCE.boxLayoutMode(), RsMessages.INSTANCE.boxLayoutMode(), 
				new SFFCStaticDropdownList<BoxLayoutModeDto>() {
					public Map<String, BoxLayoutModeDto> getValues() {
						Map<String, BoxLayoutModeDto> map = new HashMap<String, BoxLayoutModeDto>();
						
						map.put(RsMessages.INSTANCE.boxLayoutLrTd(), BoxLayoutModeDto.LeftRightTopDown);
						map.put(RsMessages.INSTANCE.boxLayoutTdLr(), BoxLayoutModeDto.TopDownLeftRight);
						
						return map;
					}
			});
		
		form.setFieldWidth(100);
		final String boxPackModeKey = form.addField(
				List.class, DatasourceParameterDefinitionDtoPA.INSTANCE.boxLayoutPackMode(), RsMessages.INSTANCE.boxLayoutPackMode(), 
				new SFFCStaticDropdownList<BoxLayoutPackModeDto>() {
					public Map<String, BoxLayoutPackModeDto> getValues() {
						Map<String, BoxLayoutPackModeDto> map = new HashMap<String, BoxLayoutPackModeDto>();
						
						map.put(RsMessages.INSTANCE.columns(), BoxLayoutPackModeDto.Columns);
						map.put(RsMessages.INSTANCE.packages(), BoxLayoutPackModeDto.Packages);
						
						return map;
					}
			});
		
		final String boxPackColSizeKey = form.addField(
				Integer.class, DatasourceParameterDefinitionDtoPA.INSTANCE.boxLayoutPackColSize(), RsMessages.INSTANCE.boxLayoutPackColSize() 
		);
		
		form.endRow();
		
		
		/* add type */
		form.setFieldWidth(210);
		final String typeKey = form.addField(
			List.class, DatasourceParameterDefinitionDtoPA.INSTANCE.returnType(), RsMessages.INSTANCE.returnType(), 
			new SFFCStaticDropdownList<DatatypeDto>() {
				@Override
				public Map<String, DatatypeDto> getValues() {
					Map<String, DatatypeDto> map = new TreeMap<String, DatatypeDto>();
					
					map.put("java.lang.String", DatatypeDto.String);
					map.put("java.lang.Integer", DatatypeDto.Integer);
					map.put("java.lang.Long", DatatypeDto.Long);
					map.put("java.lang.Double", DatatypeDto.Double);
					map.put("java.lang.Float", DatatypeDto.Float);
					map.put("java.lang.Boolean", DatatypeDto.Boolean);
					map.put("java.math.BigDecimal", DatatypeDto.BigDecimal);
					map.put("java.util.Date", DatatypeDto.Date);

					return map;
				}
		});
		
		form.setFieldWidth(1);
		String formatKey = form.addField(String.class, DatasourceParameterDefinitionDtoPA.INSTANCE.format(), RsMessages.INSTANCE.format());
		
		form.setFieldWidth(210);
		/* default value */
		singleDefaultValuesKey = form.addField(
			List.class, 
			DatasourceParameterDefinitionDtoPA.INSTANCE.singleDefaultValueSimpleData(),
			RsMessages.INSTANCE.defaultValues(),
			new SFFCDynamicListInPopup<DatasourceParameterDataDto>() {
				public ListStore<DatasourceParameterDataDto> getStore() {
					return getParameterDataStore(definition);
				}
				
				public String getPropertyLabel() {
					return RsMessages.INSTANCE.defaultValues();
				};
				
				public ValueProvider getStoreKeyForDisplay() {
					return DatasourceParameterDataDtoPA.INSTANCE.key();
				}

				public Boolean isMultiselect() {
					return false;
				}
			},new SFFCEditableDropDown(){}
		);
		
		form.setFieldWidth(1);
		multiDefaultValuesKey = form.addField(
				List.class, 
				DatasourceParameterDefinitionDtoPA.INSTANCE.multiDefaultValueSimpleData(),
				RsMessages.INSTANCE.defaultValues(),
				new SFFCDynamicListInPopup<DatasourceParameterDataDto>() {
					public ListStore<DatasourceParameterDataDto> getStore() {
						return getParameterDataStore(definition);
					}
					
					public String getPropertyLabel() {
						return RsMessages.INSTANCE.defaultValues();
					};
					
					public ValueProvider getStoreKeyForDisplay() {
						return DatasourceParameterDataDtoPA.INSTANCE.key();
					}

					public Boolean isMultiselect() {
						return true;
					}
				} 
			);
			
		
		/* add dependency */
		form.addCondition(dataOriginDatasourceKey, new FieldChanged(), new SimpleFormSuccessAction() {
			@Override
			public void onSuccess(SimpleForm form) {
				reloadDefault(modeKey, multiDefaultValuesKey, singleDefaultValuesKey, form);
			}
		});
		form.addCondition(modeKey, new FieldChanged(), new SimpleFormSuccessAction() {
			@Override
			public void onSuccess(SimpleForm form) {
				reloadDefault(modeKey, multiDefaultValuesKey, singleDefaultValuesKey, form);
			}
		});
		if(dsParamService.isAllowPostProcessing()){
			form.addCondition(postProcessKey, new FieldChanged(), new SimpleFormSuccessAction() {
				@Override
				public void onSuccess(SimpleForm form) {
					reloadDefault(modeKey, multiDefaultValuesKey, singleDefaultValuesKey, form);
				}
			});
		}
		
		
		CompositeOrCondition radioCheckCond = new CompositeOrCondition(
			new CompositeAndCondition(
				new ComplexCondition(modeKey, new FieldEquals(ModeDto.Single)),
				new ComplexCondition(singleSelectionModeKey, new FieldEquals(SingleSelectionModeDto.Radio))
			),
			new CompositeAndCondition(
				new ComplexCondition(modeKey, new FieldEquals(ModeDto.Multi)),
				new ComplexCondition(multiSelectionModeKey, new FieldEquals(MultiSelectionModeDto.Checkbox))
			)
		);
		
		form.addCondition(modeKey, radioCheckCond, new ShowHideFieldAction(boxModeKey));
		form.addCondition(modeKey, radioCheckCond, new ShowHideFieldAction(boxPackModeKey));
		form.addCondition(modeKey, radioCheckCond, new ShowHideFieldAction(boxPackColSizeKey));
		
		form.addCondition(singleSelectionModeKey, radioCheckCond, new ShowHideFieldAction(boxModeKey));
		form.addCondition(singleSelectionModeKey, radioCheckCond, new ShowHideFieldAction(boxPackModeKey));
		form.addCondition(singleSelectionModeKey, radioCheckCond, new ShowHideFieldAction(boxPackColSizeKey));
		
		form.addCondition(multiSelectionModeKey, radioCheckCond, new ShowHideFieldAction(boxModeKey));
		form.addCondition(multiSelectionModeKey, radioCheckCond, new ShowHideFieldAction(boxPackModeKey));
		form.addCondition(multiSelectionModeKey, radioCheckCond, new ShowHideFieldAction(boxPackColSizeKey));
		
		form.addCondition(modeKey, new FieldEquals(ModeDto.Single), new ShowHideFieldAction(singleSelectionModeKey));
		
		
		form.addCondition(modeKey, new FieldEquals(ModeDto.Single), new ShowHideFieldAction(singleSelectionModeKey));
		form.addCondition(modeKey, new FieldEquals(ModeDto.Multi), new ShowHideFieldAction(multiSelectionModeKey));
		form.addCondition(typeKey, new FieldEquals(DatatypeDto.Date), new ShowHideFieldAction(formatKey));

		form.addCondition(modeKey, new FieldEquals(ModeDto.Single), new ShowHideFieldAction(singleDefaultValuesKey));
		form.addCondition(modeKey, new FieldEquals(ModeDto.Multi), new ShowHideFieldAction(multiDefaultValuesKey));

		
		form.addCondition(modeKey, new FieldChanged(), new SimpleFormAction() {
			
			@Override
			public void onSuccess(SimpleForm form) {
				ModeDto mode = (ModeDto) form.getValue(modeKey);
				if(ModeDto.Multi.equals(mode)){
					if(1 == definition.getHeight())
						definition.setHeight(300);
				} else {
					definition.setHeight(1);
				}
			}
			
			@Override
			public void onFailure(SimpleForm form) {
			}
		});

		/* bind definition */
		form.bind(definition);
		
		/* return form */
		return form;
	}
	
	protected ListStore<DatasourceParameterDataDto> getParameterDataStore(final DatasourceParameterDefinitionDto definition) {
		if(null != parameterDataStore)
			return parameterDataStore;
		
		RpcProxy<ListLoadConfig, ListLoadResult<DatasourceParameterDataDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<DatasourceParameterDataDto>>() {
			
			public void load(ListLoadConfig loadConfig, final AsyncCallback<ListLoadResult<DatasourceParameterDataDto>> callback) {
				AsyncCallback<ListLoadResult<DatasourceParameterDataDto>> cb = new AsyncCallback<ListLoadResult<DatasourceParameterDataDto>>() {
					
					@Override
					public void onSuccess(ListLoadResult<DatasourceParameterDataDto> result) {
						callback.onSuccess(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						new SimpleErrorDialog(caught.getMessage()).show();
					}
				};
				
				if((null == definition.getDatasourceContainer() || null == definition.getDatasourceContainer().getDatasource()) &&
					(! dsParamService.isAllowPostProcessing() || null == definition.getPostProcess() || "".equals(definition.getPostProcess().trim())))
					cb.onSuccess(new ListLoadResultBean<DatasourceParameterDataDto>(new ArrayList<DatasourceParameterDataDto>()));
				else
					datasourceParamDao.loadDatasourceParameterData((DatasourceParameterDefinitionDto) definition, null, true, cb);
			}
		};
		
		ListLoader<ListLoadConfig, ListLoadResult<DatasourceParameterDataDto>> listLoader = new ListLoader<ListLoadConfig, ListLoadResult<DatasourceParameterDataDto>>(proxy);
		ListStore<DatasourceParameterDataDto> store = new LoadableListStore<ListLoadConfig, DatasourceParameterDataDto, ListLoadResult<DatasourceParameterDataDto>>(new BasicObjectModelKeyProvider(), listLoader);
		
		this.parameterDataStore = store;
		
		return store;
	}


	protected void reloadDefault(final String modeKey,
			final String multiDefaultValuesKey, final String singleDefaultValuesKey, final SimpleForm form) {
		if(null == reloadCommand){
			reloadCommand = new ScheduledCommand() {
				@Override
				public void execute() {
					reloadCommand = null;
					ModeDto mode = (ModeDto) form.getValue(modeKey);
					if(! form.isVisible() || ! form.isFieldsLoaded())
						return;
					if(ModeDto.Multi.equals(mode))
						form.reloadField(multiDefaultValuesKey);
					else
						form.reloadField(singleDefaultValuesKey);
				}
			};
			Scheduler.get().scheduleFinally(reloadCommand);
		}
	}


	@Override
	public Widget doGetEditComponentForInstance(DatasourceParameterInstanceDto instance, Collection<ParameterInstanceDto> relevantInstances, DatasourceParameterDefinitionDto definition, boolean initial, int labelWidth, String executeReportToken) {
		/* store configurator for later use -- when a parameter is redrawn due to a change for example in dependent parameters this will be overwritten */
		editComponentConfigurator = editComponentForInstanceProvider.get();
		return editComponentConfigurator.getEditComponent(definition, instance, relevantInstances, labelWidth, initial);
	}
	
	@Override
	public boolean canHandle(ParameterProposalDto proposal) {
		String type = proposal.getType();
		if(List.class.getName().equals(type))
			return true;
		if(Boolean.class.getName().equals(type))
			return true;

		return false;
	}

	@Override
	public ParameterDefinitionDto getNewDto(ParameterProposalDto proposal, ReportDto report) {
		DatasourceParameterDefinitionDto definition = (DatasourceParameterDefinitionDto) getNewDto(report);

		if(Boolean.class.getName().equals(proposal.getType())){
			definition.setMode(ModeDto.Single);
			definition.setSingleSelectionMode(SingleSelectionModeDto.Radio);
			definition.setReturnType(DatatypeDto.Boolean);
			definition.setBoxLayoutMode(BoxLayoutModeDto.LeftRightTopDown);
			definition.setBoxLayoutPackMode(BoxLayoutPackModeDto.Columns);
			definition.setBoxLayoutPackColSize(1);
			
			if(entpriseService.isEnterprise())
				definition.setPostProcess("[['true',true],['false',false]]");
		}
			
		
		return definition;
	}

	@Override
	public void dependeeInstanceChanged(DatasourceParameterInstanceDto instance,
			DatasourceParameterDefinitionDto aDefinition,
			Collection<ParameterInstanceDto> relevantInstances) {
		super.dependeeInstanceChanged(instance, aDefinition, relevantInstances);
		
		boolean silent = instance.isSilenceEvents();
		instance.silenceEvents(true);
		
		instance.setMultiValue(new ArrayList<DatasourceParameterDataDto>());
		instance.setSingleValue(null);
		
		instance.silenceEvents(silent);
	}
	
	@Override
	public boolean canDependOnParameters(){
		return true;
	}
	
	@Override
	public List<String> validateParameter(
			DatasourceParameterDefinitionDto definition,
			DatasourceParameterInstanceDto instance, Widget widget) {
		if(null == editComponentConfigurator)
			throw new IllegalStateException("Edit component should have been initialized");
		
		return editComponentConfigurator.validateParameter(definition, instance, widget);
	}
	
}
