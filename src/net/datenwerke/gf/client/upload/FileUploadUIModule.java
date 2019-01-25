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
 
 
package net.datenwerke.gf.client.upload;

import net.datenwerke.gf.client.upload.fileselectionsource.FileUploadSource;

import com.google.gwt.inject.client.AbstractGinModule;

public class FileUploadUIModule extends AbstractGinModule {

	public static final String UPLOAD_FILE_PREFIX = "rs_file_up_";
	public static final String UPLOAD_FILE_ID_PREFIX = UPLOAD_FILE_PREFIX + "_id_";
	public static final String UPLOAD_FILE_FILE_PREFIX = UPLOAD_FILE_PREFIX + "_file_";
	public static final String UPLOAD_FILE_HANDLER_PREFIX = UPLOAD_FILE_PREFIX + "_handler_";
	public static final String UPLOAD_FILE_META_PREFIX = UPLOAD_FILE_PREFIX + "_meta_";
	public static final String UPLOAD_FILE_XHR_CONTENT_PREFIX = UPLOAD_FILE_PREFIX + "_xhr_";
	public static final String UPLOAD_FILE_XHR_LENGTH_PREFIX = UPLOAD_FILE_PREFIX + "_xhrlength_";
	public static final String UPLOAD_FILE_XHR_NAME_PREFIX = UPLOAD_FILE_PREFIX + "_xhrname_";
	
	public static final String UPLOAD_SUCCESSFUL_PREFIX = "RS_UPLOAD_SUCCESS:";
	
	public static final String SERVLET_NAME = "fileupload";
	public static final String INTERIM_FILE_UPLOAD_HANDLER = "fileupload_fileselection_handler";
	
	public static final String UPLOADED_FILE_TYPE = "fileupload_uploadedfile"; 
	
	@Override
	protected void configure() {
		bind(FileUploadUiService.class).to(FileUploadUiServiceImpl.class);
		
		bind(FileUploadUiStartup.class).asEagerSingleton();
		
		requestStaticInjection(FileUploadSource.class);
	}

}
