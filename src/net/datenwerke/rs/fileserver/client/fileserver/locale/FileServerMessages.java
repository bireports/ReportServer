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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface FileServerMessages extends Messages {

	public static final FileServerMessages INSTANCE = GWT.create(FileServerMessages.class);
	
	String adminLabel();

	String mainPanelHeading();

	String folder();
	String insert();

	String permissionModuleDescription();

	String file();

	String editFolder();
	String editFile();
	
	String contentTypeLabel();
	String fileUploadLabel();
	String previewLabel();

	String sizeLabel();
	String sizeValue(int bytes);
	String sizeValueKb(double d);
	String sizeValueMb(double d);

	String publiclyAccessibleLabel();

	String previewUrlLabel();

	String fileViewHeader();

	String importConfigFailureNoParent();

	String importWhereTo();

	String importMainPropertiesDescription();

	String importMainPropertiesHeadline();

	String selectFileFromFileServerText();

	String folderDescription();
	String fileDescription();

	String zipUploadedTitle();
	String zipUploadedMsg();



	



}
