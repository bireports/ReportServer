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
 
 
package net.datenwerke.rs.core.client.reportexecutor.variantstorer;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwSplitButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStringMaxLength;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.ModalAsyncCallback;
import net.datenwerke.rs.core.client.reportexecutor.events.ExecutorEventHandler;
import net.datenwerke.rs.core.client.reportexecutor.events.VariantChangedEvent;
import net.datenwerke.rs.core.client.reportexecutor.events.VariantCreatedEvent;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorInformation;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanel;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.decorator.ReportDtoDec;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportDtoPA;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.dto.ExecuteDto;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;

import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;

import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * 
 *
 */
public class ReportViewVariantStorerHooker implements VariantStorerHook {

	private final LoginService loginService;
	
	private ExecutorEventHandler eventHandler;
	private VariantStorerConfig config;
	
	@Inject
	public ReportViewVariantStorerHooker(
		LoginService loginService
		){

		/* store objects */
		this.loginService = loginService;
	}
	
	@Override
	public boolean reportPreviewViewToolbarHook_addLeft(ToolBar toolbar, ReportDto report, ReportExecutorInformation info, ReportExecutorMainPanel mainPanel) {
		return false;
	}

	@Override
	public boolean reportPreviewViewToolbarHook_addRight(ToolBar toolbar, ReportDto report, ReportExecutorInformation info, ReportExecutorMainPanel mainPanel) {
		if(! report.hasAccessRight(ExecuteDto.class))
			return false;
		
		/* store variant */
		TextButton storeVariantBtn = createStoreVariantButton(report, info.getExecuteReportToken());
		toolbar.add(storeVariantBtn);
		
		return true;
	}

	private TextButton createStoreVariantButton(final ReportDto report, final String executeToken) {
		TextButton btn = null;
		if(report instanceof ReportVariantDto && config.allowEditVariant()){
			btn = new DwSplitButton(ReportexecutorMessages.INSTANCE.store());
			((DwSplitButton)btn).setIcon(BaseIcon.REPORT_DISK); 
			btn.addSelectHandler(new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					if(config.displayEditVariantOnStore())
						displayEditVariantDialog(report, executeToken);
					else {
						ConfirmMessageBox cmb = new DwConfirmMessageBox(ReportexecutorMessages.INSTANCE.editVariantConfirmTitle(), ReportexecutorMessages.INSTANCE.editVariantConfirmMsg());
						cmb.addDialogHideHandler(new DialogHideHandler() {
							@Override
							public void onDialogHide(DialogHideEvent event) {
								if (event.getHideButton() == PredefinedButton.YES) { 
									/* prepare callback */
									AsyncCallback<ReportDto> callback = new ModalAsyncCallback<ReportDto>(ReportexecutorMessages.INSTANCE.storedSuccessfully()) { 
											@Override
											public void doOnSuccess(ReportDto resultReport) {
												VariantChangedEvent event = new VariantChangedEvent();
												event.setVariant(resultReport);
												eventHandler.handleEvent(event);
											}
									};
									
									/* perform server call */
									config.getServerCallHandler().editVariant(report,executeToken,report.getName(),	report.getDescription(),callback);
								}	
							}
						});
						cmb.show();
					}
				}
			});
			
			Menu menu = new DwMenu(); 

			MenuItem saveNewItem = new DwMenuItem(ReportexecutorMessages.INSTANCE.storeNew(), BaseIcon.REPORT_ADD);
			saveNewItem.addSelectionHandler(new SelectionHandler<Item>() {

				@Override
				public void onSelection(SelectionEvent<Item> event) {
					displayStoreVariantDialog(report,executeToken);
				}
			});
			menu.add(saveNewItem);

			btn.setMenu(menu);
		} else {
			btn = new DwTextButton(ReportexecutorMessages.INSTANCE.store(),BaseIcon.REPORT_DISK);
			btn.addSelectHandler(new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					displayStoreVariantDialog(report, executeToken);
				}
			});
		}
		
		
		
		return btn;
	}
	
	private void displayEditVariantDialog(final ReportDto report, final String executeToken) {
		final DwWindow dialog = new DwWindow();
		dialog.setHeadingText(ReportexecutorMessages.INSTANCE.changeVariant(Format.ellipse(report.getName(),100)));
		dialog.setSize(450,	300);
		dialog.getHeader().setIcon(BaseIcon.FILE.toImageResource());
		dialog.setModal(true);
		
		/* create form */
		final SimpleForm form = SimpleForm.getNewInstance();
		form.getButtonBar().clear();
		form.setHeaderVisible(false);
		form.setBodyBorder(false);
		form.setBorders(false);
		form.setLoadFieldsOnBinding(false);
		
		final String nameKey = form.addField(String.class, ReportDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.name(), 
			new SFFCStringMaxLength(){
				@Override
				public int maxLength() {
					return 128;
				}
			},
			new SFFCAllowBlank() {
				@Override
				public boolean allowBlank() {
					return false;
				}
			}
		); 
		final String descKey = form.addField(String.class, ReportDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.description(), new SFFCTextAreaImpl(){
			@Override
			public int getHeight() {
				return 100;
			}
		});

		/* load fields */
		form.loadFields();
		
		/* create model to bind to */
		ReportDto dummyModel = new ReportDtoDec();
		dummyModel.setName(report.getName()); 
		dummyModel.setDescription(report.getDescription()); 
		form.bind(dummyModel);
		
		dialog.add(form, new MarginData(10));
		
		/* add buttons */
		dialog.getButtonBar().clear();
		DwTextButton okButton = new DwTextButton(ReportexecutorMessages.INSTANCE.editVariant()); 
		okButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if(! form.isValid())
					return;
				
				/* prepare callback and properties */
				AsyncCallback<ReportDto> callback = new ModalAsyncCallback<ReportDto>(ReportexecutorMessages.INSTANCE.storedSuccessfully()) { 
						@Override
						public void doOnSuccess(ReportDto resultReport) {
							VariantChangedEvent event = new VariantChangedEvent();
							event.setVariant(resultReport);
							eventHandler.handleEvent(event);
						}
				};
				String name = (String)form.getValue(nameKey); 
				String desc = (String)form.getValue(descKey);
				
				/* perform server call */
				config.getServerCallHandler().editVariant(report,executeToken,name,desc,callback);
				
				dialog.hide();
			}
		});
		dialog.addButton(okButton);
		
		dialog.show();
	}
	
	private void displayStoreVariantDialog(final ReportDto report, final String executeToken) {
		final DwWindow dialog = new DwWindow();
		dialog.setHeadingText(ReportexecutorMessages.INSTANCE.createNewVariant(Format.ellipse(report.getName(),100))); 
		dialog.setSize(450,	300);
		dialog.setModal(true);
		
		/* create form */
		final SimpleForm form = SimpleForm.getNewInstance();
		form.getButtonBar().clear();
		form.setHeaderVisible(false);
		form.setBodyBorder(false);
		form.setBorders(false);
		form.setLoadFieldsOnBinding(false);
		
		final String nameKey = form.addField(String.class, BaseMessages.INSTANCE.name(), 
			new SFFCStringMaxLength(){
				@Override
				public int maxLength() {
					return 128;
				}
			},
			new SFFCAllowBlank() {
				@Override
				public boolean allowBlank() {
					return false;
				}
			}
		);
		final String descKey = form.addField(String.class, BaseMessages.INSTANCE.description(), new SFFCTextAreaImpl(){
			@Override
			public int getHeight() {
				return 100;
			}
		});
		
		form.loadFields();
		dialog.add(form, new MarginData(10));
		
		/* add buttons */
		dialog.getButtonBar().clear();
		DwTextButton okButton = new DwTextButton(ReportexecutorMessages.INSTANCE.createVariant()); 
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(! form.isValid())
					return;

				/* prepare callback and properties */
				AsyncCallback<ReportDto> callback = new ModalAsyncCallback<ReportDto>(ReportexecutorMessages.INSTANCE.storedSuccessfully()) { 
						@Override
						public void doOnSuccess(ReportDto resultReport) {
							VariantCreatedEvent event = new VariantCreatedEvent();
							event.setVariant(resultReport);
							eventHandler.handleEvent(event);
						}
				};
				String name = (String)form.getValue(nameKey);
				String desc = (String)form.getValue(descKey);
				
				/* server call */
				config.getServerCallHandler().createNewVariant(report,	executeToken, name,	desc, callback);
				
				dialog.hide();
			}
		});
		dialog.addButton(okButton);
		
		dialog.show();
	}
	
	@Override
	public void reportPreviewViewToolbarHook_reportUpdated(ReportDto report, ReportExecutorInformation info) {
		// do not care
	}

	@Override
	public void setEventHandler(ExecutorEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public void setConfig(VariantStorerConfig variantStorerConfig) {
		this.config = variantStorerConfig;
	}

}
