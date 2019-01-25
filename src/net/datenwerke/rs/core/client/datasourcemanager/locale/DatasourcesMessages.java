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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface DatasourcesMessages extends Messages {

	public final static DatasourcesMessages INSTANCE = GWT.create(DatasourcesMessages.class);
	
	String dataSourcePermissionModuleDescription();

	String datasources();

	String query();
	
	String url();
	String username();
	String password();
	String database();

	String folder();
	String insert();
	
	String adminModuleName();
	String editDataSource();

	String dataSource();

	String editFolder();

	String useDefault();

	String useDefaultFailureTitle();

	String useDefaultFailureMessage();

	String useDefaultSuccessMessage();

	String loading();
	String submit();

	String setDefaultDatasource();
	
	String urlContainsWhitespaceWarning();

	String clearPassword();

	String importWhereTo();
	String importConfigFailureNoParent();
	String importMainPropertiesDescription();
	String importMainPropertiesHeadline();

	String importerName();

	String exportGroup();
	String quickExportText();

	String execute();

	String dataSourceConfigTitle();

	String defaultDatasource();
	
}