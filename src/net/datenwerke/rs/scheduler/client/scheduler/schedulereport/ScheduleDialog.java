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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.datenwerke.gxtdto.client.forms.wizard.WizardDialog;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.sendto.SendToClientConfig;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleConfigWizardPageProviderHook;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.ExportConfigFormFactory;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.SchedulerBaseConfigurationForm;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.SchedulerExportConfigurationForm;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.SeriesConfigFormFactory;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.SeriesConfigurationForm;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DateTriggerConfigDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScheduleDialog {

	private final Provider<SchedulerBaseConfigurationForm> schedulerBaseConfigurationFormProvider;
	private final ExportConfigFormFactory exportFormFactory;
	private final SeriesConfigFormFactory seriesConfigurationFormFactory;
	private final HookHandlerService hookHandler;
	
	
	public interface DialogCallback{
		void finished(ReportScheduleDefinition configDto, WizardDialog dialog);
	}
	
	@Inject
	public ScheduleDialog(
			Provider<SchedulerBaseConfigurationForm> schedulerConfigurationFormProvider,
			ExportConfigFormFactory exportFormFactory,
			SeriesConfigFormFactory seriesConfigurationFormFactory,
			HookHandlerService hookHandler
			) {
		
		/* store objects */
		this.schedulerBaseConfigurationFormProvider = schedulerConfigurationFormProvider;
		this.exportFormFactory = exportFormFactory;
		this.seriesConfigurationFormFactory = seriesConfigurationFormFactory;
		this.hookHandler = hookHandler;
		
	}

	public void displayDialog(final ReportDto report, Collection<ReportViewConfiguration> configs, ArrayList<SendToClientConfig> sendToConfigs, DialogCallback callback){
		displayDialog(report, configs, sendToConfigs, null, callback);
	}
	
	public void displayEdit(ReportScheduleDefinition definition, ArrayList<SendToClientConfig> sendToConfigs, ReportDto report, DialogCallback callback) {
		displayDialog(report, new HashSet<ReportViewConfiguration>(), sendToConfigs, definition, callback);
	}
	
	protected void displayDialog(final ReportDto report, Collection<ReportViewConfiguration> configs, ArrayList<SendToClientConfig> sendToConfigs, ReportScheduleDefinition definition, final DialogCallback callback){
		final SchedulerBaseConfigurationForm schedulerConfigForm = schedulerBaseConfigurationFormProvider.get();
		final SchedulerExportConfigurationForm exportForm = exportFormFactory.create(report, configs, sendToConfigs, definition);
		final SeriesConfigurationForm seriesConfigForm = seriesConfigurationFormFactory.create(report, definition);

		final List<ScheduleConfigWizardPageProviderHook> addPages = hookHandler.getHookers(ScheduleConfigWizardPageProviderHook.class);
		
		WizardDialog wizard = new WizardDialog(){
			@Override
			public void onFinish() {
				DateTriggerConfigDto schedulerConfigDto = seriesConfigForm.createDto();
				ReportScheduleDefinition configDto = new ReportScheduleDefinition();
				configDto.setSchedulerConfig(schedulerConfigDto);
				configDto.setReport(report);
				
				/* prepare list of ids */
				List<Long> recipientIds = new ArrayList<Long>();
				for(StrippedDownUser sUser : schedulerConfigForm.getSelectedStrippedUserStore().getAll())
					recipientIds.add(sUser.getId());
				configDto.setRecipients(recipientIds);
				
				exportForm.configureConfig(configDto);
				configDto.setOutputFormat(schedulerConfigForm.getOutputFormat());
				configDto.setExportConfiguration(schedulerConfigForm.getExportConfiguration());
				
				for(ScheduleConfigWizardPageProviderHook pageProvider : addPages)
					pageProvider.configureConfig(configDto, report);
				
				callback.finished(configDto, this);
			}
		};
		
		schedulerConfigForm.configureForm(report, definition, wizard, addPages);
		wizard.addPage(schedulerConfigForm);
		wizard.addPage(exportForm);
		
		for(ScheduleConfigWizardPageProviderHook pageProvider : addPages){
			if(! pageProvider.isAdvanced())
				wizard.addPage(pageProvider.getPage(report, definition));
		}
		
		wizard.addPage(seriesConfigForm);
		wizard.setSize(640, 480);
		wizard.setHeadingText(SchedulerMessages.INSTANCE.scheduleReportMulti(report.getName()));
		wizard.show();
	}

	

}
