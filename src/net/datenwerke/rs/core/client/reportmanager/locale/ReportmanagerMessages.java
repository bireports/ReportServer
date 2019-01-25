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
 
 
package net.datenwerke.rs.core.client.reportmanager.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ReportmanagerMessages extends Messages {
	
	public final ReportmanagerMessages INSTANCE = GWT.create(ReportmanagerMessages.class);
	
	String addSubreport();
	String createPreview();
	String datasource();
	String date();
	String dateAndTime();
	String dateParameterName();
	String defaultValue();
	String editFolder();
	String editJasperReport();

	String editReportVariant();
	String editTableReport();

	String folder();
	String ID();
	String importConfigFailureFolderBeneathReport();
	String importConfigFailureNoParent();
	String importConfigFailureVariantParent();
	String importerName();
	String importMainPropertiesDescription();
	String importMainPropertiesHeadline();
	String importRemoveKeyFieldLabel();
	String importWhereTo();
	String insert();
	String jrxmlFile();
	String JRXMLMaster();
	String key();
	String lastModified();
	String metadataDataSource();
	String nameMasterJRXML();
	String owner();
	String parameterManagement();
	String preview();
	String previewCreated();
	String quickExportIncludeVariantsText();
	String quickExportIncludeVariantsTitle();
	
	
	String refreshed();
	
	String removedSuccess();
	String reportManagement();
	
	String reportmanager();
	String reportSecurityDescription();
	
	String stringParameterName();
	String subreports();
	
	String time();
	String type();
	String upload();
	
	String uploadError();
	
	String selectParamBtnLabel();
	
	String execute();
	
	String switchToFormulaMode();
	String switchToDateMode();
	String writeProtect();
	String configurationProtect();
	String variant();
	
	String reportLabel();
	String selectReportHeader();
	String catalog();

	String isVariant();
	String parentReportName();
	
	String propertyAllowCubify();
	String fieldLabelCubify();
	String fileSelectionParameterName();
	
	String deleteVariantConfirmMessage(String string);
}
