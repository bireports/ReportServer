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

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.ShowHideFieldAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.FieldEquals;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCBoolean;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ScheduleAsFileInformation;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.locale.ScheduleAsFileMessages;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleExportSnippetProviderHook;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.helper.config.TeamSpaceViewConfig;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.helper.simpleform.SFFCTsTeamSpaceSelector;

import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;

public class ExportSnippetProviderHook implements
		ScheduleExportSnippetProviderHook {


	private String isExportAsFileKey;
	private String teamSpaceKey;
	private String folderKey;
	private String nameKey;
	private String descriptionKey;

	@Override
	public boolean appliesFor(ReportDto report,
			Collection<ReportViewConfiguration> configs) {
		return true;
	}
	
	@Override
	public void configureSimpleForm(final SimpleForm xform, ReportDto report, Collection<ReportViewConfiguration> configs) {
		xform.setFieldWidth(250);
		
		xform.setLabelWidth(0);
		xform.setLabelAlign(LabelAlign.LEFT);
		isExportAsFileKey = xform.addField(Boolean.class, "", new SFFCBoolean() {
			@Override
			public String getBoxLabel() {
				return ScheduleAsFileMessages.INSTANCE.askStoreAsFile();
			}
		});
		
		xform.setLabelAlign(LabelAlign.TOP);
		xform.setLabelWidth(300);
		xform.beginRow();
		teamSpaceKey = xform.addField(TeamSpaceDto.class, ScheduleAsFileMessages.INSTANCE.teamspace());
		
		folderKey = xform.addField(TsDiskFolderDto.class, ScheduleAsFileMessages.INSTANCE.folder(), new SFFCTsTeamSpaceSelector() {
			@Override
			public TeamSpaceDto getTeamSpace() {
				return (TeamSpaceDto) xform.getValue(teamSpaceKey);
			}
		});
		xform.endRow();
		
		xform.setLabelAlign(LabelAlign.TOP);
		xform.setFieldWidth(1);
		nameKey = xform.addField(String.class, BaseMessages.INSTANCE.propertyName());
		descriptionKey = xform.addField(String.class, BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
		xform.addCondition(isExportAsFileKey, new FieldEquals(true), new ShowHideFieldAction(folderKey));
		xform.addCondition(isExportAsFileKey, new FieldEquals(true), new ShowHideFieldAction(teamSpaceKey));
		xform.addCondition(isExportAsFileKey, new FieldEquals(true), new ShowHideFieldAction(nameKey));
		xform.addCondition(isExportAsFileKey, new FieldEquals(true), new ShowHideFieldAction(descriptionKey));

		TeamSpaceViewConfig config = getConfig(configs);
		if(null != config)
			xform.setValue(teamSpaceKey, config.getTeamSpace());
	}

	private TeamSpaceViewConfig getConfig(
			Collection<ReportViewConfiguration> configs) {
		for(ReportViewConfiguration conf : configs)
			if(conf instanceof TeamSpaceViewConfig)
				return (TeamSpaceViewConfig) conf;
		return null;
	}

	@Override
	public boolean isValid(SimpleForm simpleForm) {
		TeamSpaceDto ts = (TeamSpaceDto) simpleForm.getValue(teamSpaceKey); 
		AbstractTsDiskNodeDto folder = (AbstractTsDiskNodeDto) simpleForm.getValue(folderKey);
		String name = (String) simpleForm.getValue(nameKey);
		
		return (null != ts && null != folder && null != name && !name.isEmpty());
	}

	@Override
	public void configureConfig(ReportScheduleDefinition configDto, SimpleForm form) {
		if(! isActive(form))
			return;

		ScheduleAsFileInformation info = new ScheduleAsFileInformation();
		info.setName((String) form.getValue(nameKey));
		info.setDescription((String) form.getValue(descriptionKey));
		info.setFolder((AbstractTsDiskNodeDto) form.getValue(folderKey));
		
		configDto.addAdditionalInfo(info);
	}

	@Override
	public boolean isActive(SimpleForm simpleForm) {
		return (Boolean) simpleForm.getValue(isExportAsFileKey);
	}

	@Override
	public void loadFields(SimpleForm form, ReportScheduleDefinition definition, ReportDto report) {
		form.setValue(nameKey, "${now} - " + report.getName());

		if(null != definition){ 
			ScheduleAsFileInformation info = definition.getAdditionalInfo(ScheduleAsFileInformation.class);
			if(null != info){
				form.setValue(teamSpaceKey, info.getTeamSpace());
				form.setValue(isExportAsFileKey, true);
				form.setValue(nameKey, info.getName());
				form.setValue(descriptionKey, info.getDescription());
				form.setValue(folderKey, info.getFolder());
			}
		}

		form.loadFields();
	}


}
