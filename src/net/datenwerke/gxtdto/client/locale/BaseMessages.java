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
 
 
package net.datenwerke.gxtdto.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface BaseMessages extends Messages {

	public final static BaseMessages INSTANCE = GWT.create(BaseMessages.class);
	
	/* Buttons */
	String submit();
	String ok();
	String apply();
	String cancel();
	String close();
	String add();
	String insert();
	String yes();
	String no();
	String edit();
	
	String save();
	String saveAs();

	String remove();
	String removeAll();

	String expandAll();
	String collapseAll();
	String reload();
	String refresh();
	String rename();

	String view();
	
	/* generic */
	String properties();
	String next();
	String prev();

	/* labels */
	String propertyName();
	String propertyDescription();
	String width();
	String height();
	String value();
	String name();
	String type();
	String description();
	String information();
	String createdOn();
	String folder();
	String id();
	String changedOn();
	String key();

	/* messages */
	String loadingMsg();
	String storingMsg();
	String waitMsg();
	String busyMsg();
	String progressMsg();
	
	String confirmDeleteMsg(String displayTitle);
	String confirmDeleteTitle();
	
	String needForcefulDeleteMsg(String message);
	String needForcefulDeleteTitle();
	String changesApplied();

	String open();
	String error();
	String displayErrorDetails();
	
	String unknown();
	
	/* tabs */
	String closeOtherTabsText();
	String closeTabText();
	String closeAllsTabText();
	
	/* error messages */
	String alphaNumericErrorMsg();
	String requestCanceled();
	
	String selectValueLabel(String name);
	
	String warning();
	String unsavedChanges();
	String encounteredError();
	String violatedSecurity();
	String menu();
	String unnamed();
	
	String revert();
	String confirmDeleteManyMsg(int size);
	String confirmDeleteAllMsg();
	String infoLabel();
	String helpLabel();

	String copy();
	String paste();
	String uploadError();
	String validUploadTarget();
	
	String download();
	String duplicate();
	String lastModified();
	String informationOn(String displayTitle);
}
