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
 
 
package net.datenwerke.rs.base.client.parameters.locale;


import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface RsMessages extends Messages {

	public final static RsMessages INSTANCE = GWT.create(RsMessages.class);
	
	String datasource();
	String datasourceParameter();
	String defaultValue();
	String displayText();
	String dropdown();
	String format();
	String generalProperties();
	String headline();
	String mode();
	String multiSelect();
	String noDataSelected();
	String popup();
	String returnType();
	String seletionMode();
	String separator();
	String singleSelect();
	String singleSeletionMode();
	String multiSeletionMode();
	String text();
	String useNowAsDefault();
	String defaultFormula();
	String defaultValues();
	String radio();
	String checkbox();
	String listbox();
	
	
	String boxLayoutPackColSize();
	String boxLayoutPackMode();
	String columns();
	String packages();
	String boxLayoutMode();
	String boxLayoutLrTd();
	String boxLayoutTdLr();
	String datasourceParameterPostProcess();
	String datasourceNotConfigured();
	
	String validatorRegexLabel();
	String badRegex(String regex);
	String invalidParameter(String name);

	String dateTimeParameterText();
	String blaTextParameterText();
	String datasourceParameterText();
	String headlineParameterText();
	String separatorParameterText();
	String textParameterText();
	String fileSelectionParameterText();
	
	String enableFileUpload();
	String enableTeamSpaceSelection();
	String enableFileServerSelection();
	
	String maximalFileSize();
	String allowedFileExtensions();
	
	String minNumberOfFiles();
	String maxNumberOfFiles();
	String enableDownload();
	String uploadedFile();
	String teamspaceFile();
	String fileserverFile();
	String dropdownFirstValuePerDefaultInfo();
	
}
