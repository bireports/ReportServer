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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;

public class LibDirClasspathHelper {

	private ConfigDirService configDirService;


	public LibDirClasspathHelper(ConfigDirService configDirService) {
		this.configDirService = configDirService;
	}
	
	
	public void loadLibs(){
		ClassLoader cl = getClassloader();

		if(null != cl) {
			try {
				if(configDirService.isEnabled()){
					File libDir = new File(configDirService.getConfigDir(), ConfigStartup.LIB_DIR);
					if(libDir.exists() && libDir.isDirectory()){
						try{
							FileObject fileObject = VFS.getManager().resolveFile(libDir.getCanonicalPath());
							FileObject[] files = fileObject.findFiles(Selectors.EXCLUDE_SELF);
							for(FileObject f : files){
								if("jar".equals(f.getName().getExtension().toLowerCase())){
									try {
										addUrl(cl, new File(f.getName().getPath()).toURI().toURL());
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							}
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}

		}
	}

	public void addUrl(ClassLoader cl, URL url) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		addURLMethod.setAccessible(true);
		addURLMethod.invoke(cl, url);
	}

	public ClassLoader getClassloader() {
		URLClassLoader candidate = null;
		ClassLoader cl = this.getClass().getClassLoader();
		while(null != cl){
			if(cl instanceof URLClassLoader){
				for(URL u : ((URLClassLoader)cl).getURLs()){
					if(u.toString().contains("reportserver.jar") || u.toString().contains("/classes/") ){
						candidate = (URLClassLoader) cl;
					}
				}
			}
			cl = cl.getParent();
		}
		cl = candidate;
		return cl;
	}
}
