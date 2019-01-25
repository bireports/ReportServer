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
 
 
package net.datenwerke.rs.reportdoc.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ReportDocumentationMessages extends Messages {

	public final ReportDocumentationMessages INSTANCE = GWT.create(ReportDocumentationMessages.class);
	
	String name();

	String berichtsdokuHelpText();

	String berichtsdokuGroupHeading();

	String noBerichtsdokuAssignedText();

	String noBerichtsdokuAssignedHeader();

	String couldNotLoadDoku();

	String reportChangedInfoHeader();
	String reportChangedInfoMessage();
	
	String loadError();
	String general();
	String baseReport();
	String distinct();
	String yes();
	String no();
	String groupedBy();
	String subtotals();
	String computedColumns();
	String columns();
	String hidden();
	String order();
	String emptyCells();
	String aggregation();
	String formattedAs();
	String formatCurrency();
	String formatNumber();
	String formatDate();
	String formatText();
	String formatTemplate();
	String ascending();
	String descending();
	String excluded();
	String included();
	String parameter();
	String defaultOrFirst();
	String all();
	String prefilter();
	String reportDoku();
	String page();
	String of();
	String created();
	String lastChanged();
	String creator();
	String caseSensitiveFilter();

	String date();
	String user();
	String change();
	String history();
	String revision();
	String deleted();
	String noHistory();
}