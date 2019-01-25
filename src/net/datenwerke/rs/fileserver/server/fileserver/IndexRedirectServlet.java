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
 
 
package net.datenwerke.rs.fileserver.server.fileserver;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.utils.config.ConfigService;

import com.google.inject.Provider;

@Singleton
public class IndexRedirectServlet extends HttpServlet{

	private static final long serialVersionUID = 5875654284572141968L;
	private static final String INDEX_FILE_LOCATION = "/resources/public/index.html";

	private Provider<FileServerService> fileServerService;
	private Provider<FileServerAccessServlet> fsas;
	private Provider<ConfigService> configService;
	private Provider<HookHandlerService> hookHandler;


	@Inject
	public IndexRedirectServlet(
			Provider<FileServerService> fileServerService, 
			Provider<FileServerAccessServlet> fsas, 
			Provider<ConfigService> configService, 
			Provider<HookHandlerService> hookHandler) {

		this.fileServerService = fileServerService;
		this.fsas = fsas;
		this.configService = configService;
		this.hookHandler = hookHandler;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		for(IndexRedirectHook hooker : hookHandler.get().getHookers(IndexRedirectHook.class)){
			if(hooker.consumes(req, resp)){
				hooker.doGet(req, resp);
				if(hooker.isLast())
					return;
			}
		}
		
		FileServerService service = fileServerService.get();
		AbstractFileServerNode file = service.getNodeByPath(INDEX_FILE_LOCATION, false);
		if(file instanceof FileServerFile){
			fsas.get().returnFile((FileServerFile) file, req, resp);
		}else{
			boolean doRedirect = configService.get().getConfigFailsafe("ui/ui.cf").getBoolean("useReportServerHtml", false);
			if(doRedirect){
				String url = req.getContextPath() + "/ReportServer.html";
				resp.sendRedirect(url);
			}else{
				req.getRequestDispatcher("/ReportServer.html").forward(req, resp);
			}
		}
	}

}
