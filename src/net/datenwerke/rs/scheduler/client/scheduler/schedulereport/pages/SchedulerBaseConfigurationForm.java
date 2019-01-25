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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.TimeField;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.FormHelper;
import net.datenwerke.gxtdto.client.forms.selection.SimpleGridSelectionPopup;
import net.datenwerke.gxtdto.client.forms.wizard.Validatable;
import net.datenwerke.gxtdto.client.forms.wizard.WizardDialog;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterUIService;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter;
import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter.ConfigurationFinishedCallback;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleConfigWizardPageProviderHook;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUserPA;
import net.datenwerke.security.ext.client.usermanager.UserManagerDao;
import net.datenwerke.security.ext.client.usermanager.UserManagerUIService;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeBasicSingleton;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class SchedulerBaseConfigurationForm extends DwContentPanel implements Validatable{

	private final UITree userTree;
	
	private final ReportExporterUIService reportExporterService;
	private final UserManagerUIService userManagerService;
	private final UserManagerDao userManagerDao;
	private final LoginService loginService;
	
	private ListStore<AbstractNodeDto> recipientStore;
	private ToggleGroup exportTypeGroup;
	private DateField date;
	private TimeField time;
	private TextField subject;
	private TextArea message;
	private List<ReportExecutionConfigDto> exporterConfig;
	private Map<Radio, ReportExporter> exporterMap;
	private final FormHelper formHelper;
	
	/* ie properties */
	private static ListStore<StrippedDownUser> strippedUserStore;
	private ListStore<StrippedDownUser> selectedStrippedUserStore;
	
	@Inject
	public SchedulerBaseConfigurationForm(
		LoginService loginService,
		ReportExporterUIService reportExporterService,	
		@UserManagerTreeBasicSingleton Provider<UITree> userTreeProvider,
		UserManagerUIService userManagerService,
		UserManagerDao userManagerDao,
		FormHelper formHelper
		){
		
		/* store objects */
		this.loginService = loginService;
		this.reportExporterService = reportExporterService;
		this.userManagerService = userManagerService;
		this.userManagerDao = userManagerDao;
		this.formHelper = formHelper;
		
		userTree = userTreeProvider.get();
	}

	
	public void configureForm(final ReportDto report, final ReportScheduleDefinition definition, final WizardDialog wizard, final List<ScheduleConfigWizardPageProviderHook> addPages) {
		/* build form */
		setHeaderVisible(false);
		
		DwFlowContainer flowContainer = new DwFlowContainer();
		add(flowContainer, new MarginData(10));
		
		exportTypeGroup = new ToggleGroup();
		HorizontalPanel radioPanel = new HorizontalPanel();
		
		final DwTextButton formatConfigBtn = new DwTextButton(SchedulerMessages.INSTANCE.formatConfig());
		
		List<ReportExporter> exporters = reportExporterService.getCleanedUpAvailableExporters(report);
		exporterMap = new HashMap<Radio, ReportExporter>();
		boolean first = true;
		for(final ReportExporter exporter : exporters){
			if(! exporter.canBeScheduled())
				continue;
			
			String name = exporter.getExportTitle();

			final Radio radio = new Radio();
			radio.setName("exportFormat"); //$NON-NLS-1$
			radio.setData("rs_outputFormat", exporter.getOutputFormat()); //$NON-NLS-1$
			radio.setBoxLabel(name);
			
			/* select default */
			if(null == definition && first)
				radio.setValue(true);
			
			radio.addValueChangeHandler(new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					if(radio.getValue()){
						if(exporter.hasConfiguration())
							formatConfigBtn.enable();
						else
							formatConfigBtn.disable();
					}
				}
			});
			
			radioPanel.add(radio);
			exportTypeGroup.add(radio);
			exporterMap.put(radio, exporter);
			
			/* select */
			if(null != definition && exporter.getOutputFormat().equals(definition.getOutputFormat())){
				radio.setValue(true);
				exporter.configureFrom(definition.getExportConfiguration());
				exporterConfig = exporter.getConfiguration();
			}
			
			if(radio.getValue() == true){
				formatConfigBtn.setEnabled(exporter.hasConfiguration());
			}
			
			first = false;
		}
		
		/* add to form */
		flowContainer.add(formHelper.createLabelTop(radioPanel,SchedulerMessages.INSTANCE.exportType()));
		
		/* selection listener for extra config */
		formatConfigBtn.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				Radio radio = null;
				for(HasValue<Boolean> hv : exportTypeGroup){
					if(Boolean.TRUE.equals(hv.getValue())){
						radio = (Radio) hv;
						break;
					}
				}

				if(null != radio && exporterMap.containsKey(radio)){
					final ReportExporter exporter = exporterMap.get(radio);
					exporter.displayConfiguration(report, null, false, new ConfigurationFinishedCallback() {
						@Override
						public void success() {
							exporterConfig = exporter.getConfiguration();
						}
					});
				}
			}
		});
		radioPanel.add(formatConfigBtn);
		
		/* recipients grid */
		Component recipients = getIeRecipientsSelection(definition);
		flowContainer.add(formHelper.createLabelTop(recipients, SchedulerMessages.INSTANCE.recipients()));
		
		/* advanced options */
		final CheckBox advanced = new CheckBox();
		advanced.setToolTip(SchedulerMessages.INSTANCE.showAdvancedOptionsTooltip());
		advanced.setBoxLabel(SchedulerMessages.INSTANCE.showAdvancedOptionsTooltip());
		flowContainer.add(formHelper.createLabelTop(advanced, SchedulerMessages.INSTANCE.showAdvancedOptions()));

		Collections.reverse(addPages);
		advanced.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				showAdvanced(event.getValue(), report, definition, wizard, addPages);
			}
		});

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				for(ScheduleConfigWizardPageProviderHook pageProvider : addPages){
					if(pageProvider.isAdvanced()){
						if(pageProvider.isConfigured(report, definition)){
							advanced.setValue(true, true);
							showAdvanced(true, report, definition, wizard, addPages);
							break;
						}
					}
				}
			}
		});
	}
	
	protected void showAdvanced(Boolean show, ReportDto report, ReportScheduleDefinition definition, WizardDialog wizard, List<ScheduleConfigWizardPageProviderHook> addPages) {
		if(show){
			for(ScheduleConfigWizardPageProviderHook pageProvider : addPages){
				if(pageProvider.isAdvanced())
					wizard.addPage(wizard.getPageCount()-1, pageProvider.getPage(report, definition));
			}
		} else {
			for(ScheduleConfigWizardPageProviderHook pageProvider : addPages){
				if(pageProvider.isAdvanced())
					wizard.removePage(pageProvider.getPage(report, definition));
			}
		}
	}


	protected Component getIeRecipientsSelection(ReportScheduleDefinition definition) {
		if(null == strippedUserStore)
			strippedUserStore = userManagerService.getStrippedUserStore();
		
		selectedStrippedUserStore = new ListStore<StrippedDownUser>(StrippedDownUserPA.INSTANCE.dtoId());
		
		Map<ValueProvider<StrippedDownUser, String>, String> displayProperties = new LinkedHashMap<ValueProvider<StrippedDownUser, String>, String>();
		
		displayProperties.put(StrippedDownUserPA.INSTANCE.firstname(), SchedulerMessages.INSTANCE.firstName());
		displayProperties.put(StrippedDownUserPA.INSTANCE.lastname(), SchedulerMessages.INSTANCE.lastName());
		displayProperties.put(StrippedDownUserPA.INSTANCE.ou(), SchedulerMessages.INSTANCE.ou());
		
		final SimpleGridSelectionPopup<StrippedDownUser> userGrid = new SimpleGridSelectionPopup<StrippedDownUser>(displayProperties, 90, selectedStrippedUserStore, strippedUserStore);
		userGrid.setHeight(250);
		userGrid.getView().setShowDirtyCells(false);
		userGrid.getView().setEmptyText(SchedulerMessages.INSTANCE.noRecipientSelected());
		userGrid.setBorders(true);
		
		if(null != definition && null != definition.getRecipients() && ! definition.getRecipients().isEmpty()){
			userGrid.mask(BaseMessages.INSTANCE.loadingMsg());
			
			userManagerDao.getStrippedDownUsers(definition.getRecipients(), new RsAsyncCallback<List<StrippedDownUser>>(){
				@Override
				public void onSuccess(List<StrippedDownUser> result) {
					userGrid.unmask();
					selectedStrippedUserStore.addAll(result);
				}
			});
		} else {
			UserDto user = loginService.getCurrentUser();
			StrippedDownUser sUser = StrippedDownUser.fromUser(user);
			selectedStrippedUserStore.add(sUser);
		}
		
		return userGrid;
	}
	
	public ListStore<StrippedDownUser> getSelectedStrippedUserStore(){
		return selectedStrippedUserStore;
	}
	
	public ListStore<AbstractNodeDto> getRecipientStore() {
		return recipientStore;
	}

	public ToggleGroup getExportTypeGroup() {
		return exportTypeGroup;
	}
	
	public String getOutputFormat(){
		for(HasValue<Boolean> hv : exportTypeGroup)
			if(Boolean.TRUE.equals(hv.getValue()))
				return ((Radio) hv).getData("rs_outputFormat");
		
		return null;
	}

	public DateField getDate() {
		return date;
	}

	public TimeField getTime() {
		return time;
	}

	public TextField getSubject() {
		return subject;
	}

	public TextArea getMessage() {
		return message;
	}
	
	public List<ReportExecutionConfigDto> getExportConfiguration(){
		return exporterConfig;
	}
	
	@Override
	public boolean isValid() {
		Radio radio = null;
		for(HasValue<Boolean> hv : exportTypeGroup){
			if(Boolean.TRUE.equals(hv.getValue())){
				radio = (Radio) hv;
				break;
			}
		}
		
		if(null != radio && exporterMap.containsKey(radio)){
			final ReportExporter exporter = exporterMap.get(radio);
			if(! exporter.isConfigured()){
				new MessageBox(SchedulerMessages.INSTANCE.formatConfig(), SchedulerMessages.INSTANCE.formatConfigError()).show();
				return false;
			}
		} else {
			return false;
		}
		
		if(selectedStrippedUserStore.size() == 0)
			return false;
		
		return true;
	}
}
