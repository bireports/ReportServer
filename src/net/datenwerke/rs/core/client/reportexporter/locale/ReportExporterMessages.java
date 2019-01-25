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
 
 
package net.datenwerke.rs.core.client.reportexporter.locale;


import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ReportExporterMessages extends Messages {

	public final static ReportExporterMessages INSTANCE = GWT.create(ReportExporterMessages.class);
	
	String executing();

	String Export2Excel();

	String export2HTML();

	String Export2PDF();
	
	String Export2RTF();
	
	String exportMenuBtnLabel();
	String exportReport();
	
	String exportReportTo(String exportTitle);
	
	String exportTypeLabel();
	
	String exportViaMailLabel();
	String formatConfigLabel();
	
	String messageSend();
	
	String selectUserLabel();
	String sendToLabel();
	String subjectLabel();

	String messageLabel();

	String exportTypeNotConfigured();

	String reportNotExportable();

	String Export2Doc();

	String export2Text();
	String export2XML();

	String reportIsBeingExportedTitle();
	String reportIsBeingExportedMsg(String outputFormat);
	
	String reportLoadingWait();

}
