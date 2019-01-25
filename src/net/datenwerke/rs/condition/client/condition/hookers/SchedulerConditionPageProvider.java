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
 
 
package net.datenwerke.rs.condition.client.condition.hookers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwSplitButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.ShowHideFieldAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.FieldEquals;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTextArea;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCStaticDropdownList;
import net.datenwerke.gxtdto.client.forms.wizard.WizardResizer;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.rs.condition.client.condition.ConditionDao;
import net.datenwerke.rs.condition.client.condition.dto.ConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ConditionFailureStrategy;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionList;
import net.datenwerke.rs.condition.client.condition.dto.pa.ConditionDtoPA;
import net.datenwerke.rs.condition.client.condition.dto.pa.ScheduleConditionDtoPA;
import net.datenwerke.rs.condition.client.condition.locale.ConditionMessages;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleConfigWizardPageProviderHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.RetryTimeUnitDto;

public class SchedulerConditionPageProvider implements ScheduleConfigWizardPageProviderHook {

	class MyDwContentPanel extends DwContentPanel implements WizardResizer{

		@Override
		public int getPageHeight() {
			return 500;
		}
		
	}
	
	private final ConditionDao conditionDao;
	private final ToolbarService toolbarService;
	
	private DwContentPanel page;
	private ListStore<ScheduleConditionDto> store = new ListStore<ScheduleConditionDto>(new BasicObjectModelKeyProvider<ScheduleConditionDto>());
	
	private Grid<ScheduleConditionDto> grid;

	private SimpleForm form;

	private String failureStrategyKey;

	private String retryStrategyUnitKey;

	private String retryStrategyAmountKey;
	
	@Inject
	public SchedulerConditionPageProvider(
		ConditionDao conditionDao,
		ToolbarService toolbarService
		) {
		
		/* store objects */
		this.conditionDao = conditionDao;
		this.toolbarService = toolbarService;
	}

	@Override
	public boolean isAdvanced() {
		return true;
	}

	@Override
	public Widget getPage(ReportDto report, ReportScheduleDefinition definition) {
		if(null == page)
			initPage(report, definition);
		return page;
	}

	protected void initPage(ReportDto report, ReportScheduleDefinition definition) {
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		
		/* init store */
		if(null != definition && null != definition.getAdditionalInfo(ScheduleConditionList.class))
			store.addAll(definition.getAdditionalInfo(ScheduleConditionList.class).getConditionList());
		
		ToolBar toolbar = createToolbar();
		container.add(toolbar, new VerticalLayoutData(1,-1));
		
		grid = createGrid(definition);
		container.add(grid, new VerticalLayoutData(1, -1));
		
		form = createForm(definition);
		container.add(form,  new VerticalLayoutData(1, 120, new Margins(10,0,10,0)));
		
		page = new MyDwContentPanel();
		
		page.setHeaderVisible(false);
				
		final DwContentPanel wrapper = new DwContentPanel();
		wrapper.setLightHeader();
		wrapper.setHeadingText(ConditionMessages.INSTANCE.schedulerPageHeadline());
		wrapper.setInfoText(ConditionMessages.INSTANCE.schedulerPageDescription());
		wrapper.add(container);
		
		/* sencha window positioning bug */
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				page.add(wrapper, new MarginData(10));
			}
		});
	}

	private SimpleForm createForm(ReportScheduleDefinition definition) {
		SimpleForm form = SimpleForm.getInlineInstance();
		form.setFieldWidth(200);
		form.beginFloatRow();
		
		failureStrategyKey = form.addField(List.class, ConditionMessages.INSTANCE.failureStrategyLabel(), new SFFCStaticDropdownList<ConditionFailureStrategy>() {

			@Override
			public Map<String, ConditionFailureStrategy> getValues() {
				Map<String, ConditionFailureStrategy> map = new TreeMap<String, ConditionFailureStrategy>();
				map.put(ConditionMessages.INSTANCE.skipLabel(), ConditionFailureStrategy.SKIP);
				map.put(ConditionMessages.INSTANCE.retryLabel(), ConditionFailureStrategy.RETRY);
				return map;
			}
			
		});
		
		form.setFieldWidth(100);
		retryStrategyUnitKey = form.addField(List.class, ConditionMessages.INSTANCE.failureRetryUnitLabel(), new SFFCStaticDropdownList<RetryTimeUnitDto>() {

			@Override
			public Map<String, RetryTimeUnitDto> getValues() {
				Map<String, RetryTimeUnitDto> map = new TreeMap<String, RetryTimeUnitDto>();
				map.put(ConditionMessages.INSTANCE.minutesLabel(), RetryTimeUnitDto.MINUTES);
				map.put(ConditionMessages.INSTANCE.hoursLabel(), RetryTimeUnitDto.HOURS);
				map.put(ConditionMessages.INSTANCE.daysLabel(), RetryTimeUnitDto.DAYS);
				map.put(ConditionMessages.INSTANCE.weeksLabel(), RetryTimeUnitDto.WEEKS);
				map.put(ConditionMessages.INSTANCE.monthsLabel(), RetryTimeUnitDto.MONTHS);
				map.put(ConditionMessages.INSTANCE.yearsLabel(), RetryTimeUnitDto.YEARS);
				return map;
			}
			
		});
		
		retryStrategyAmountKey = form.addField(Integer.class, ConditionMessages.INSTANCE.failureRetryAmount());
		
		form.addCondition(failureStrategyKey, new FieldEquals(ConditionFailureStrategy.RETRY), new ShowHideFieldAction(retryStrategyUnitKey));
		form.addCondition(failureStrategyKey, new FieldEquals(ConditionFailureStrategy.RETRY), new ShowHideFieldAction(retryStrategyAmountKey));
		
		
		form.setValue(failureStrategyKey, ConditionFailureStrategy.SKIP);
		form.setValue(retryStrategyUnitKey, RetryTimeUnitDto.HOURS);
		form.setValue(retryStrategyAmountKey, 1);
		
		if(null != definition && null != definition.getAdditionalInfo(ScheduleConditionList.class)){
			ScheduleConditionList list = definition.getAdditionalInfo(ScheduleConditionList.class);
			
			if(null != list.getFailureStrategy())
				form.setValue(failureStrategyKey, list.getFailureStrategy());
			if(null != list.getRetryStrategyUnit())
				form.setValue(retryStrategyUnitKey, list.getRetryStrategyUnit());
			if(0 != list.getRetryStrategyAmount())
				form.setValue(retryStrategyAmountKey, list.getRetryStrategyAmount());
		}
		
		form.loadFields();
		
		return form;
	}

	private Grid<ScheduleConditionDto> createGrid(ReportScheduleDefinition definition) {
		List<ColumnConfig<ScheduleConditionDto,?>> columnConfig = new ArrayList<ColumnConfig<ScheduleConditionDto,?>>();
		
		ScheduleConditionDtoPA pa = GWT.create(ScheduleConditionDtoPA.class);
		
		ColumnConfig<ScheduleConditionDto,String> ccName = new ColumnConfig<ScheduleConditionDto,String>(pa.name(), 200, BaseMessages.INSTANCE.propertyName());
		ccName.setMenuDisabled(true);
		columnConfig.add(ccName);

		ColumnConfig<ScheduleConditionDto,String> ccExpr = new ColumnConfig<ScheduleConditionDto,String>(pa.expression(), 200, ConditionMessages.INSTANCE.expressionLabel()); 
		ccExpr.setMenuDisabled(true);
		columnConfig.add(ccExpr);

		final Grid<ScheduleConditionDto> grid = new Grid<ScheduleConditionDto>(store, new ColumnModel<ScheduleConditionDto>(columnConfig));
		grid.setHeight(250);
		
		grid.addRowDoubleClickHandler(new RowDoubleClickHandler() {
			
			@Override
			public void onRowDoubleClick(RowDoubleClickEvent event) {
				ScheduleConditionDto item = grid.getStore().get(event.getRowIndex());
				if(null != item)
					displayEditScheduleCondition(item);
			}
		});
		
		return grid;
	}

	private ToolBar createToolbar() {
		ToolBar toolbar = new DwToolBar();
		
		DwTextButton addBtn = toolbarService.createSmallButtonLeft(ConditionMessages.INSTANCE.addConditionLabel(), BaseIcon.ADD);
		addBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				showAddCondition();
			}
		});
		toolbar.add(addBtn);
		
		DwSplitButton remove = new DwSplitButton(ConditionMessages.INSTANCE.removeConditionLabel());
		remove.setIcon(BaseIcon.DELETE);
		remove.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				ScheduleConditionDto cond = grid.getSelectionModel().getSelectedItem();
				if(null != cond)
					remove(cond);
			}
		});
		toolbar.add(remove);
		
		Menu removeMenu = new DwMenu();
		remove.setMenu(removeMenu);
		
		MenuItem removeAll = new DwMenuItem(ConditionMessages.INSTANCE.removeAllConditionLabel(), BaseIcon.DELETE);
		removeAll.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				ConfirmMessageBox cmb = new DwConfirmMessageBox(ConditionMessages.INSTANCE.removeAllConfirmHeading(), ConditionMessages.INSTANCE.removeAllConfirmText());
				cmb.addDialogHideHandler(new DialogHideHandler() {
					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton() == PredefinedButton.YES)
							removeAll();						
					}
				});
				cmb.show();
			}
		});
		removeMenu.add(removeAll);
		
		return toolbar;
	}
	

	protected void remove(ScheduleConditionDto cond) {
		store.remove(cond);
	}

	protected void removeAll() {
		store.clear();
	}


	protected void showAddCondition() {
		final DwWindow window = new DwWindow();
		window.setSize(500, 400);
		window.setHeadingText(ConditionMessages.INSTANCE.addConditionHeader());
		window.setModal(true);
		
		ConditionDtoPA conditionPa = GWT.create(ConditionDtoPA.class);
		
		final ListStore<ConditionDto> conditionStore = new ListStore<ConditionDto>(conditionPa.dtoId());
		List<ColumnConfig<ConditionDto, ?>> columnConfig = new ArrayList<ColumnConfig<ConditionDto, ?>>();
		
		ColumnConfig<ConditionDto, String> ccName = new ColumnConfig<ConditionDto, String>(conditionPa.name(), 200, BaseMessages.INSTANCE.propertyName()); 
		ccName.setMenuDisabled(true);
		columnConfig.add(ccName);

		ColumnConfig<ConditionDto, String> ccExpr = new ColumnConfig<ConditionDto, String>(conditionPa.description(), 350, BaseMessages.INSTANCE.propertyDescription()); 
		ccExpr.setMenuDisabled(true);
		columnConfig.add(ccExpr);

		final Grid<ConditionDto> grid = new Grid<ConditionDto>(conditionStore, new ColumnModel<ConditionDto>(columnConfig));
		grid.getView().setAutoExpandColumn(ccName);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		grid.addRowDoubleClickHandler(new RowDoubleClickHandler() {
			
			@Override
			public void onRowDoubleClick(RowDoubleClickEvent event) {
				onConditionSubmitButtonClick(grid, window);
			}
		});
		
		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		wrapper.setScrollMode(ScrollMode.AUTOY);
		
		DwContentPanel headDesc = new DwContentPanel();
		headDesc.setLightHeader();
		headDesc.setHeadingText(ConditionMessages.INSTANCE.conditionPageHeadline());
		headDesc.setInfoText(ConditionMessages.INSTANCE.conditionPageDescription());
		headDesc.add(grid);
		
		wrapper.add(headDesc, new VerticalLayoutData(1,-1, new Margins(10)));
		
		window.add(wrapper);
		
		/* load data */
		grid.mask(BaseMessages.INSTANCE.loadingMsg());
		conditionDao.getConditions(new RsAsyncCallback<List<ConditionDto>>(){
			@Override
			public void onSuccess(List<ConditionDto> result) {
				grid.unmask();
				conditionStore.addAll(result);
			}
		});
		
		DwTextButton cancelBtn = new DwTextButton(BaseMessages.INSTANCE.cancel());
		window.addButton(cancelBtn);
		cancelBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				window.hide();
			}
		});
		
		DwTextButton submitBtn = new DwTextButton(BaseMessages.INSTANCE.submit());
		window.addButton(submitBtn);
		submitBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				onConditionSubmitButtonClick(grid, window);
			}
		});
	
		
		window.show();
	}
	
	private void onConditionSubmitButtonClick(Grid<ConditionDto> grid, DwWindow window) {
		ConditionDto condition = grid.getSelectionModel().getSelectedItem();
		if(null == condition)
			return;
		
		ScheduleConditionDto scheduleCondition = new ScheduleConditionDto();
		scheduleCondition.setCondition(condition);
		scheduleCondition.setExpression("");
		
		store.add(scheduleCondition);
		
		window.hide();
		
		displayEditScheduleCondition(scheduleCondition);
	}

	protected void displayEditScheduleCondition(final ScheduleConditionDto scheduleCondition) {
		final DwWindow window = new DwWindow();
		window.setHeadingText(ConditionMessages.INSTANCE.editConditionHeader(scheduleCondition.getCondition().getName()));
		window.setModal(true);
		window.setWidth(500);
		window.setHeight(300);
		
		final SimpleForm form = SimpleForm.getInlineLabelessInstance();

		final String taKey = form.addField(String.class, ScheduleConditionDtoPA.INSTANCE.expression(), new SFFCTextArea() {
			
			@Override
			public int getWidth() {
				return 470;
			}
			
			@Override
			public int getHeight() {
				return 100;
			}
		});
		
		final ScheduleConditionDto dummyCondition = new ScheduleConditionDto();
		dummyCondition.setExpression(scheduleCondition.getExpression());
		dummyCondition.setCondition(scheduleCondition.getCondition());
		form.bind(dummyCondition);
		
		ToolBar tb = new DwToolBar();
		DwTextButton repBtn = toolbarService.createSmallButtonLeft(ConditionMessages.INSTANCE.addReplacementLabel(), BaseIcon.WRENCH);
		repBtn.setArrowAlign(ButtonArrowAlign.RIGHT);
		final Menu menu = new DwMenu();
		repBtn.setMenu(menu);
		conditionDao.getReplacementsFor(scheduleCondition.getCondition(), new RsAsyncCallback<List<String>>(){
			public void onSuccess(java.util.List<String> result) {
				for(final String rep : result){
					MenuItem item = new DwMenuItem(rep);
					menu.add(item);
					item.addSelectionHandler(new SelectionHandler<Item>() {
						
						@Override
						public void onSelection(SelectionEvent<Item> event) {
							TextArea ta = (TextArea)form.getField(taKey);
							String value = ta.getValue();
							if(null == value)
								value = rep;
							else
								value = value.substring(0,ta.getCursorPos()) + rep + value.substring(ta.getCursorPos());
							ta.setValue(value);
						}
					});
				}
			};
		});
		tb.add(repBtn);
		
		DwTextButton testBtn = toolbarService.createSmallButtonLeft(ConditionMessages.INSTANCE.testConditionLabel(), BaseIcon.COGS);
		tb.add(testBtn);
		testBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				TextArea ta = (TextArea)form.getField(taKey);
				String value = ta.getValue();
				if(null == value)
					value = "";
				dummyCondition.setExpression(value);
				
				conditionDao.executeCondition(dummyCondition, new RsAsyncCallback<Boolean>(){
					@Override
					public void onSuccess(Boolean result) {
						String msg = result ? ConditionMessages.INSTANCE.conditionHolds() : ConditionMessages.INSTANCE.conditionFails();
						
						AlertMessageBox box = new DwAlertMessageBox(ConditionMessages.INSTANCE.testConditionLabel(), msg);
						
						if(result)
							box.setIcon(MessageBox.ICONS.info());
						
						box.show();
						
					}
				});
			}
		});

		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		wrapper.add(tb, new VerticalLayoutData(1,-1));
		wrapper.add(form, new VerticalLayoutData(1,-1));
		
		
		final DwContentPanel cwrapper = new DwContentPanel();
		cwrapper.setLightHeader();
		cwrapper.setHeadingText(ConditionMessages.INSTANCE.editConditionHeader(scheduleCondition.getCondition().getName()));
		cwrapper.setInfoText(scheduleCondition.getCondition().getDescription());
		cwrapper.add(wrapper);
		
		
		
		DwTextButton cancelBtn = new DwTextButton(BaseMessages.INSTANCE.cancel());
		window.addButton(cancelBtn);
		cancelBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				window.hide();
			}
		});
		
		DwTextButton submitBtn = new DwTextButton(BaseMessages.INSTANCE.submit());
		window.addButton(submitBtn);
		submitBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				TextArea ta = (TextArea)form.getField(taKey);
				String value = ta.getValue();
				if(null == value)
					value = "";
				dummyCondition.setExpression(value);
				
				conditionDao.executeCondition(dummyCondition, new RsAsyncCallback<Boolean>(){
					@Override
					public void onSuccess(Boolean result) {
						scheduleCondition.setExpression(dummyCondition.getExpression());
						store.update(scheduleCondition);
						window.hide();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						new DwAlertMessageBox(BaseMessages.INSTANCE.error(), caught.getMessage()).show();
					}
				});
			}
		});
	
		/* positioning bug */
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				window.add(cwrapper, new MarginData(10));
			}
		});
		window.show();
	}

	@Override
	public void configureConfig(ReportScheduleDefinition configDto,	ReportDto report) {
		if(store.size() > 0){
			ScheduleConditionList list = new ScheduleConditionList();
			list.setFailureStrategy((ConditionFailureStrategy) form.getValue(failureStrategyKey));
			list.setRetryStrategyUnit((RetryTimeUnitDto) form.getValue(retryStrategyUnitKey));
			list.setRetryStrategyAmount((Integer) form.getValue(retryStrategyAmountKey));
			list.setConditionList(new ArrayList(store.getAll()));
			configDto.addAdditionalInfo(list);
		}
	}

	@Override
	public boolean isConfigured(ReportDto report,
			ReportScheduleDefinition definition) {
		return null != definition && null != definition.getAdditionalInfo(ScheduleConditionList.class);
	}

}
