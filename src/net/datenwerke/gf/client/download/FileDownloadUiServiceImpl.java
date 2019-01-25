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
 
 
package net.datenwerke.gf.client.download;

import net.datenwerke.gf.client.download.dto.DownloadProperties;
import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;

import com.google.gwt.core.client.GWT;

public class FileDownloadUiServiceImpl implements FileDownloadUiService {

	@Override
	public void triggerDownload(DownloadProperties properties) {
		StringBuilder url = new StringBuilder().append(GWT.getModuleBaseURL()).append("filedownload?id=").append(properties.getId());
		url.append("&handler=").append(properties.getHandler());
		for(String key : properties.getMetadata().keySet()){
			String value = properties.getMetadata().get(key);
			url.append("&").append(FileDownloadUiModule.META_FIELD_PREFIX).append("=").append(value);
		}
		
		ClientDownloadHelper.triggerDownload(url.toString());		
	}

}
