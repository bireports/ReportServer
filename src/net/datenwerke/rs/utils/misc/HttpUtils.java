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
		
		return cd + "; filename=\"" + strictfileName  +"\"; filename*=UTF-8''" + replaceChars(UriUtils.encode(sanitizedfilename)) + "";
	}
	
	/* Replace the punctuation and punctuation characters and URL component delimiter characters that are not replaced
	 * as shown here: http://www.gwtproject.org/javadoc/latest/com/google/gwt/http/client/URL.html#encode-java.lang.String-
	 */
	private String replaceChars(String toEncode) {
		return toEncode.replace("-", "%2D").replace("_", "%5F").replace(".", "%2E")
				.replace("!", "%21").replace("~", "%7E").replace("*", "%2A").replace("'", "%27")
				.replace("(", "%28").replace(")", "%29")
				.replace(";", "%3B").replace("/", "%2F").replace("?", "%3F").replace(":", "%3A")
				.replace("&", "%26").replace("=", "%3D").replace("+", "%2B").replace("$", "%24")
				.replace(",", "%2C").replace("#", "%23");
	}
}
