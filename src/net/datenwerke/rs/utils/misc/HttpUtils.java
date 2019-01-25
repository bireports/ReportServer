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
 
 
package net.datenwerke.rs.utils.misc;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.safehtml.shared.UriUtils;

import net.datenwerke.rs.utils.filename.FileNameService;

public class HttpUtils {
	
	public final static String CONTENT_DISPOSITION = "Content-Disposition";

	private final Provider<FileNameService> fileNameServiceProvider;
	
	@Inject
	public HttpUtils(Provider<FileNameService> fileNameServiceProvider) {
		this.fileNameServiceProvider = fileNameServiceProvider;
	}
	
	public String makeContentDispositionHeader(boolean download, String filename) {
		String cd = download ? "attachment" : "inline";

		String sanitizedfilename = fileNameServiceProvider.get().sanitizeFileName(filename);
		String strictfileName = fileNameServiceProvider.get().sanitizeFileNameStrict(filename);
		
		return cd + "; filename=\"" + strictfileName  +"\"; filename*=UTF-8''" + UriUtils.encode(sanitizedfilename);
	}
}
