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
 
 
package net.datenwerke.rs.birt.client.reportengines.ui;

import net.datenwerke.gf.client.upload.FileUploadUiService;
import net.datenwerke.gf.client.upload.dto.UploadProperties;
import net.datenwerke.gf.client.upload.filter.FileUploadFilter;
import net.datenwerke.gxtdto.client.forms.binding.FormBinding;
import net.datenwerke.rs.birt.client.reportengines.BirtUiModule;
import net.datenwerke.rs.birt.client.reportengines.dto.BirtReportDto;
import net.datenwerke.rs.birt.client.reportengines.dto.BirtReportFileDto;
import net.datenwerke.rs.birt.client.reportengines.locale.BirtMessages;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.reportmanager.ui.forms.AbstractReportForm;

import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;

public class BirtReportForm extends AbstractReportForm {

	private final FileUploadUiService fileUploadService;
	
	private Label uploadLabel;

	private static FileUploadFilter uploadFilter = new FileUploadFilter() {
		@Override
		public String doProcess(String name, long size, String base64) {
			boolean error = null == name || ! name.toLowerCase().endsWith(".rptdesign");
			return error ? BirtMessages.INSTANCE.fileMustBeRptDesign() : null;
		}
	};
	
	@Inject
	public BirtReportForm(
		DatasourceUIService datasourceService,
		FileUploadUiService fileUploadService
	) {
		super(datasourceService);
		
		/* store objects */
		this.fileUploadService = fileUploadService;
	}
	
	@Override
	protected void initializeForm(FormPanel form,
			VerticalLayoutContainer fieldWrapper) {
		super.initializeForm(form, fieldWrapper);
		
		UploadProperties uploadProperties = new UploadProperties("birtreport", BirtUiModule.UPLOAD_HANDLER_ID);
		uploadProperties.addMetadata(BirtUiModule.UPLOAD_REPORT_ID_FIELD, String.valueOf(getSelectedNode().getId()));
		uploadProperties.setFilter(uploadFilter);
		
		Component uploadComponent = fileUploadService.addCombinedUploadField(uploadProperties);
		fieldWrapper.add(new FieldLabel(uploadComponent, BirtMessages.INSTANCE.rptdesign()));

		uploadLabel = new Label(BirtMessages.INSTANCE.fileName());
		BirtReportFileDto reportfile = ((BirtReportDto)getSelectedNode()).getReportFile();
		if(null != reportfile)
			uploadLabel.setText(reportfile.getName());
		fieldWrapper.add(uploadLabel);
		
		configureUnboundFields();
	}
	
	@Override
	protected String getHeader() {
		return BirtMessages.INSTANCE.editBirtReport()  + " ("+getSelectedNode().getId()+")"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	
	@Override
	protected boolean isUploadForm() {
		return true;
	}
	
	@Override
	protected String getFormAction() {
		return fileUploadService.getFormAction();
	}

	@Override
	protected boolean isDisplayConfigFieldsForDatasource() {
		return false;
	}

	@Override
	protected void configureAutoBinding(FormBinding binding) {
//		binding.addIgnorePathsForAutoBinding(FileUploadUIModule.FIELDNAME_UPLOAD_ID, FileUploadUIModule.UPLOAD_FILE_PREFIX);
	}
	
	
	/**
	 * Set values for the fields not included in the formbinding
	 */
	private void configureUnboundFields(){
		BirtReportFileDto file = ((BirtReportDto)getSelectedNode()).getReportFile();
		if(null != file)
			uploadLabel.setText(file.getName());

	}
	




}
