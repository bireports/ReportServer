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
 
 
package net.datenwerke.rs.core.client.reportexporter.exporter.generic;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow.OnButtonClickHandler;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.reportexporter.dto.RECCsvDto;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportexporter.dto.decorator.RECCsvDtoDec;
import net.datenwerke.rs.core.client.reportexporter.dto.pa.RECCsvDtoPA;
import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporterImpl;
import net.datenwerke.rs.core.client.reportexporter.locale.ReportExporterMessages;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.container.MarginData;

public abstract class Export2CSV extends ReportExporterImpl {
	
	protected RECCsvDto config;
	
	@Override
	public boolean consumesConfiguration(ReportDto report) {
		return true;
	}
	
	@Override
	public void configureFrom(List<ReportExecutionConfigDto> exportConfiguration) {
		if(null == exportConfiguration || exportConfiguration.isEmpty())
			return;
		
		for(ReportExecutionConfigDto config : exportConfiguration){
			if(config instanceof RECCsvDtoDec){
				this.config = (RECCsvDto) config;
				break;
			}
		}
	}
	
	@Override
	public void displayConfiguration(final ReportDto report, final String executorToken, boolean allowAutomaticConfig, final ConfigurationFinishedCallback callback) {
		final DwWindow window = new DwWindow();
		window.setHeadingText(getExportTitle());
		window.getHeader().setIcon(getIcon());
		window.setSize(420, 270);
		window.setModal(true);
		
		final SimpleForm form = SimpleForm.getInlineInstance();
		window.add(form, new MarginData(10));
		form.addField(Boolean.class, RECCsvDtoPA.INSTANCE.printHeader(), ReportexecutorMessages.INSTANCE.csvConfigPrintHeader());
		form.addField(String.class, RECCsvDtoPA.INSTANCE.separator(), ReportexecutorMessages.INSTANCE.csvConfigSeparator());
		form.addField(String.class, RECCsvDtoPA.INSTANCE.quote(), ReportexecutorMessages.INSTANCE.csvConfigQuote());
		
		if(null == config)
			config = new RECCsvDtoDec();
		form.bind(config);
		
		window.addCancelButton();
		
		DwTextButton submitBtn = window.addSubmitButton(new OnButtonClickHandler() {
			@Override
			public void onClick() {
				callback.success();
			}
		});
		submitBtn.setText(ReportExporterMessages.INSTANCE.exportReport());
		
		window.show();
	}
	
	@Override
	public boolean isConfigured() {
		return null != config;
	}
	
	
	@Override
	public List<ReportExecutionConfigDto> getConfiguration(){
		List<ReportExecutionConfigDto> configs = new ArrayList<ReportExecutionConfigDto>();
		configs.add(config);
		return configs;
	}
	
	@Override
	public String getExportDescription() {
		return ReportexecutorMessages.INSTANCE.export2CSV();
	}

	@Override
	public String getExportTitle() {
		return "CSV"; //$NON-NLS-1$
	}

	@Override
	public String getOutputFormat() {
		return "CSV"; //$NON-NLS-1$
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.FILE_TEXT_O.toImageResource();
	}

	@Override
	public boolean hasConfiguration() {
		return true;
	}

}