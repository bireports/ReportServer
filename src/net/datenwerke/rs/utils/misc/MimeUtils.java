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
 
 
package net.datenwerke.rs.utils.misc;

import javax.inject.Inject;
import javax.servlet.ServletContext;

public class MimeUtils {

	private ServletContext servletContext;

	@Inject
	public MimeUtils(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String getMimeTypeByExtension(String filename) {
		String mime = servletContext.getMimeType(filename);
		if(null == mime){
			if(filename.endsWith(".groovy")){
				return "text/groovy";
			}else if(filename.endsWith(".rs")){
				return "text/groovy";
			}else if(filename.endsWith(".cf")){
				return "text/xml";
			}else if(filename.endsWith(".cf.disabled")){
				return "text/xml";
			}
		}
		
		return mime;
	}
}
