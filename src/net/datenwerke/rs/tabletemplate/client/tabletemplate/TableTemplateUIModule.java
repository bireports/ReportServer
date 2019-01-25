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
 
 
package net.datenwerke.rs.tabletemplate.client.tabletemplate;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class TableTemplateUIModule extends AbstractGinModule {

	public static final String DOWNLOAD_SERVLET = "TableTemplateDownload";
	public static final String FORM_ACTION = "TableTemplateUpload";
	public static final String UPLOAD_FIELD_NAME = "xlsTemplate";
	public static final String EXECUTE_REPORT_TOKEN = "executeReportToken";
	public static final String JXLS_TEMPLATE_PROPERTY_NAME = "jxls:template";
	
	public static final String EXPORT_OUTPUT_FORMAT = "TABLE_TEMPLATE";
	public static final String REPORT_ID = "reportId";
	public static final String TEMPLATE_ID = "templateId";
	public static final String TEMPLATE_TEMPORARY_ID = "templateTemporaryId";
	
	public static final String TEMPLATE_LIST_PROPERTY_NAME = "tabletemplate:templatelist";
	
	@Override
	protected void configure() {
		bind(TableTemplateUIService.class).to(TableTemplateUIServiceImpl.class).in(Singleton.class);
		bind(TableTemplateUIStartup.class).asEagerSingleton();
	}

}
