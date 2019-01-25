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
 
 
package net.datenwerke.gf.service.download;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.datenwerke.gf.service.download.hooks.FileDownloadHandlerHook;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;

public class FileDownloadServiceImpl implements FileDownloadService {

	private final HookHandlerService hookHandlerService;
	
	@Inject
	public FileDownloadServiceImpl(HookHandlerService hookHandlerService) {
		this.hookHandlerService = hookHandlerService;
	}


	@Override
	public void processDownload(String id, String handler,
			Map<String, String> metadata, HttpServletResponse response) {
		for(FileDownloadHandlerHook hooker : hookHandlerService.getHookers(FileDownloadHandlerHook.class)){
			if(hooker.consumes(handler)){
				try {
					hooker.processDownload(id,handler, metadata, response);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
				break;
			}
		}
	}

}
