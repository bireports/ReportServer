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
 
 
package net.datenwerke.rs.core.server.versioninfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Singleton
public class VersionInfoServlet extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private static final long serialVersionUID = -3667983893196665074L;

	private HookHandlerService hookHandler;
	
	@Inject
	public VersionInfoServlet(HookHandlerService hookHandler) {
		this.hookHandler = hookHandler;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{
			InputStream propfile = getClass().getClassLoader().getResourceAsStream("rsversion.properties");
			Properties p = new Properties();
			p.load(propfile);

			resp.setContentType("text/plain");

			String style = "full";
			if(req.getParameterMap().containsKey("style")) 
				style = req.getParameter("style");

			PrintWriter pw = new PrintWriter(resp.getOutputStream());
			if("full".equals(style)){
				propfile = getClass().getClassLoader().getResourceAsStream("rsversion.properties");
				pw.print(IOUtils.toString(propfile));
				
			}else if("num".equals(style)){
				pw.print(p.get("version"));
				
			}else if("banner".equals(style)){
				String date = p.getProperty("buildDate");
				pw.print(p.getProperty("version") + "<br/>" + date.substring(date.indexOf("-") + 1));
				
			}else if("ext".equals(style)){
				HashMap<String, Object> res = new HashMap<String, Object>();
				res.put("version.num", p.get("version"));
				String date = p.getProperty("buildDate");
				res.put("version.banner", p.getProperty("version") + "<br/>" + date.substring(date.indexOf("-") + 1));
				
				for(VersionInfoExtensionHook vie : hookHandler.getHookers(VersionInfoExtensionHook.class)){
					res.put(vie.getKey(), vie.getValue(req.getParameterMap()));
				}
				String json = new JSONObject(res).toString();
				if(req.getParameterMap().containsKey("callback")){
					json = req.getParameter("callback") + "(" + json + ");";
				}
				pw.print(json);
			}
			
			pw.close();
		}catch(Exception e){
			logger.debug("Could not read version file", e);
		}
	}

	
}
