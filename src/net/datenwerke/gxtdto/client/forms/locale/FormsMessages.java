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
 
 
package net.datenwerke.gxtdto.client.forms.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface FormsMessages extends Messages {

	public final static FormsMessages INSTANCE = GWT.create(FormsMessages.class);
	
	String addAll();
	String defaultValues();
	String finish();
	String invalidConfigMessage();
	String invalidConfigTitle();
	String invalidDecimal();
	String invalidDouble();
	String invalidFloatingpoint();
	String invalidInteger();
	String invalidTime();
	String noDataAvailable();
	String noDataSelected();
	String onlySelectable();
	String select();
	String selectedItems();
	String validationFailedMessage();
	String validationFailedTitle();
	String selectedValues();
	
	String fieldMustBeAlphaNumeric();
	String regexDefaultErrorMessage(String regex);
	String invalidBoolean();
	
	String dropFilesHere();
}
