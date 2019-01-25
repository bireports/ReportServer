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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers;

import java.util.Collection;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.rs.core.client.helper.simpleform.ExportTypeSelection;
import net.datenwerke.rs.core.client.helper.simpleform.config.SFFCExportTypeSelector;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorInformation;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanel;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexporter.hooks.ExportExternalEntryProviderHook;
import net.datenwerke.rs.core.client.reportexporter.locale.ReportExporterMessages;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.ScheduleAsFileDao;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.locale.ScheduleAsFileMessages;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.helper.config.TeamSpaceViewConfig;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.helper.simpleform.SFFCTsTeamSpaceSelector;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class ExportToTeamSpaceHooker implements ExportExternalEntryProviderHook {

	@Inject
	private ScheduleAsFileDao safDao;
	
	@Override
	public void getMenuEntry(Menu menu, final ReportDto report,
			final ReportExecutorInformation info, final ReportExecutorMainPanel mainPanel) {
		
		MenuItem item = new DwMenuItem(ScheduleAsFileMessages.INSTANCE.exportToTeamSpaceLabel(), BaseIcon.GROUP_EDIT);
		menu.add(item);
		item.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				displayExportDialog(report, info, mainPanel.getViewConfigs());
			}
		});
	}


	protected void displayExportDialog(final ReportDto report,
			final ReportExecutorInformation info, Collection<ReportViewConfiguration> configs) {
		final DwWindow window = new DwWindow();
		window.setHeaderIcon(BaseIcon.GROUP_EDIT);
		window.setHeadingText(ScheduleAsFileMessages.INSTANCE.exportToTeamSpaceLabel());
		window.setWidth(500);
		window.setHeight(530);
		
		/* configure form */
		final SimpleForm form = SimpleForm.getInlineInstance();
		
		final String formatKey = form.addField(ExportTypeSelection.class, ScheduleAsFileMessages.INSTANCE.exportTypeLabel(), new SFFCExportTypeSelector() {
			@Override
			public ReportDto getReport() {
				return report;
			}
			
			@Override
			public String getExecuteReportToken(){
				return info.getExecuteReportToken();
			}
		});
		
		final String teamSpaceKey = form.addField(TeamSpaceDto.class, ScheduleAsFileMessages.INSTANCE.teamspace());
		
		final String folderKey = form.addField(TsDiskFolderDto.class, ScheduleAsFileMessages.INSTANCE.folder(), new SFFCTsTeamSpaceSelector() {
			@Override
			public TeamSpaceDto getTeamSpace() {
				return (TeamSpaceDto) form.getValue(teamSpaceKey);
			}
		});

		final String nameKey = form.addField(String.class, ScheduleAsFileMessages.INSTANCE.nameLabel());
		final String descKey = form.addField(String.class, ScheduleAsFileMessages.INSTANCE.descriptionLabel(), new SFFCTextAreaImpl());
		
		window.add(form, new MarginData(10));
		
		/* set properties */
		form.setValue(nameKey, "");
		form.setValue(descKey, "");
		
		TeamSpaceViewConfig config = getConfig(configs);
		if(null != config)
			form.setValue(teamSpaceKey, config.getTeamSpace());
		
		/* load fields */
		form.loadFields();
		
		window.addCancelButton();

		DwTextButton submitBtn = new DwTextButton(BaseMessages.INSTANCE.submit());
		submitBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				String name = (String) form.getValue(nameKey);
				String description = (String) form.getValue(descKey);
				AbstractTsDiskNodeDto folder = (AbstractTsDiskNodeDto) form.getValue(folderKey);
				
				if(null == folder){
					new DwAlertMessageBox(BaseMessages.INSTANCE.error(), ScheduleAsFileMessages.INSTANCE.noFolderSelected()).show();
					return;
				}
				
				ExportTypeSelection type = (ExportTypeSelection) form.getValue(formatKey);
				
				if(!type.isConfigured()){
					new DwAlertMessageBox(BaseMessages.INSTANCE.error(), ReportExporterMessages.INSTANCE.exportTypeNotConfigured()).show();
					return;
				}
				
				safDao.exportIntoTeamSpace(report, info.getExecuteReportToken(), type.getOutputFormat(), type.getExportConfiguration(), folder, name, description, 
						new NotamCallback<Void>(ScheduleAsFileMessages.INSTANCE.exportedIntoTeamSpace()));
				
				window.hide();
			}
		});
		window.addButton(submitBtn);
		
		window.show();
	}


	private TeamSpaceViewConfig getConfig(
			Collection<ReportViewConfiguration> configs) {
		for(ReportViewConfiguration conf : configs)
			if(conf instanceof TeamSpaceViewConfig)
				return (TeamSpaceViewConfig) conf;
		return null;
	}
	
}
