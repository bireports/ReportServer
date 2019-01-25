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
 
 
package net.datenwerke.rs.installation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.datenwerke.rs.configservice.service.configservice.ConfigDirService;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;

public class DemoContentInstallTask implements DbInstallationTask {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private Provider<PackagedScriptHelper> packagedScriptHelper;
	private FileServerService fileServerService;
	private Provider<ConfigDirService> configDirServiceProvider;




	@Inject
	public DemoContentInstallTask(
			Provider<PackagedScriptHelper> packagedScriptHelper,
			FileServerService fileServerService,
			Provider<ConfigDirService> configDirServiceProvider
			) {
				this.packagedScriptHelper = packagedScriptHelper;
				this.fileServerService = fileServerService;
				this.configDirServiceProvider = configDirServiceProvider;
	}

	@Override
	public void executeOnFirstRun() {
		ConfigDirService configDirService = configDirServiceProvider.get();
		if(configDirService.isEnabled()){
			File initpropsfile = new File(configDirService.getConfigDir(), InitConfigTask.RSINIT_PROPERTIES);
			if(initpropsfile.exists()){
				try {
					Properties props = new Properties();
					FileReader reader = new FileReader(initpropsfile);
					props.load(reader);
					IOUtils.closeQuietly(reader);
					
					if(Boolean.valueOf(props.getProperty("democontent.install", "false"))){
						PackagedScriptHelper helper = packagedScriptHelper.get();
						File demobuilderFile = null;;
						for(File f: helper.listPackages()){
							if(f.getName().startsWith("demobuilder-"))
								demobuilderFile = f;
						}
						if(null == demobuilderFile)
							return;
						
						if(helper.validateZip(demobuilderFile, false)){

							logger.info( "Executing package: " + demobuilderFile.getAbsolutePath());
							FileServerFolder targetDir = null;
							try {
								targetDir = helper.extractPackageTemporarily(new FileInputStream(demobuilderFile));
								helper.executePackage(targetDir);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								if(null != targetDir)
									fileServerService.forceRemove(targetDir);
							}
						}
					}
					
				} catch (FileNotFoundException e) {
					logger.warn("Failed to initialize using rsinit.properties", e);
				} catch (IOException e) {
					logger.warn("Failed to initialize using rsinit.properties", e);
				}
				
				
			}
		}
	}



	@Override
	public void executeOnStartup() {
		// TODO Auto-generated method stub

	}

}
