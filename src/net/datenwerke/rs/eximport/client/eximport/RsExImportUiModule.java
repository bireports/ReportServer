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
 
 
package net.datenwerke.rs.eximport.client.eximport;

import net.datenwerke.rs.eximport.client.eximport.im.ImportUIModule;

import com.google.gwt.inject.client.AbstractGinModule;

public class RsExImportUiModule extends AbstractGinModule {

	public static final String IMPORT_DATA_FORM_ACTION = "importDataUploadServlet";
	public static final String IMPORT_DATA_FILE_NAME = "importDataFile";
	public static final String IMPORT_IGNORE_WARNING_KEY = "ignoreWarnings";
	public static final String IMPORT_IGNORE_ERRORS_KEY = "ignoreErrors";
	
	public static final String UPLOAD_HANDLER_ID = "imexport_upload_handler";

	@Override
	protected void configure() {
		bind(ExImportStartup.class).asEagerSingleton();
		
		install(new ImportUIModule());
	}

}
