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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClassloaderHelper {

	@Inject
	public ClassloaderHelper() {
		// TODO Auto-generated constructor stub
	}
	
	
	private void addFileCp(File f) throws MalformedURLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		addURLCp(f.toURI().toURL());
	}

	private void addURLCp(URL u) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;

		java.lang.reflect.Method method = sysclass.getDeclaredMethod("addURL", URL.class);
		method.setAccessible(true);
		method.invoke(sysloader, u);
	}
	
	private URLClassLoader findWebappClassLoader() {
		
		URLClassLoader candidate = null;
		ClassLoader cl = this.getClass().getClassLoader();
		while(null != cl){
			if(cl instanceof URLClassLoader){
				for(URL u : ((URLClassLoader)cl).getURLs()){
					if(u.toString().contains("rscore.jar") || u.toString().contains("/RsCore/")){
						candidate = (URLClassLoader) cl;
					}
				}
			}

			cl = cl.getParent();
		}
		return candidate;
	}
}
