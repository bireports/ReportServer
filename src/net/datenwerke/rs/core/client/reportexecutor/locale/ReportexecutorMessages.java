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
 
 
package net.datenwerke.rs.core.client.reportexecutor.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ReportexecutorMessages extends Messages {
	
	public static ReportexecutorMessages INSTANCE = GWT.create(ReportexecutorMessages.class);
	
	String changeVariant(String reportName);
	String columns();
	String confirmRemoveMessage(String reportName);
	String confirmRemoveTitle();
	String createNewVariant(String reportName);
	String createVariant();
	String csvConfigPrintHeader();
	String csvConfigQuote();
	String csvConfigSeparator();
	String csvConfigLineSeparator();
	String editVariant();
	String executeDuration(double executeDuration, double serverCall);
	String executing();
	String export2CSV();
	String failed();
	String frontendHeadline();
	String geolocationLookupText();
	String loading();
	String loadingExecuteDuration();
	String loadingNumColumns();
	String loadingNumPages();
	String loadingNumRecords();
	String noColumnsSelected();
	String noAggregationSelected();
	String pages();
	String parameterWidgetHeader();
	String preview();
	String records();
	String removeDescription();
	
	String store();
	String storedSuccessfully();
	
	String storeNew();
	
	String success();
	String successText(String variantName);
	String variantDeleted();
	
	String executeAreaModule();
	
	String editVariantConfirmTitle();
	String editVariantConfirmMsg();
	
	String closeChangedReportTitle();
	String closeChangedReportMessage();
	
	String forceLegacyPdfPreview();
	String decimalSeparator();
	
	String linktomsg();
	String countRecords();
	String loadingDataTitle();
	String loadingDataMsg();
}
