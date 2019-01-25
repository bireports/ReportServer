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
 
 
package net.datenwerke.rs.installation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutePackagedScriptsTask implements DbInstallationTask {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private FileServerService fileServerService;


	private Provider<PackagedScriptHelper> packagedScriptHelper;

	@Inject
	public ExecutePackagedScriptsTask(
			FileServerService fileServerService,
			Provider<PackagedScriptHelper> packagedScriptHelper
			) {

		this.fileServerService = fileServerService;
		this.packagedScriptHelper = packagedScriptHelper;
	}

	@Override
	public void executeOnStartup() {
		
	}
	
	@Override
	public void executeOnFirstRun() {
		PackagedScriptHelper helper = packagedScriptHelper.get();
		File pkgDir = helper.getPackageDirectory();


		logger.info("Executing package scripts from: " + pkgDir.getAbsolutePath());

		if(pkgDir.exists() && pkgDir.isDirectory()){
			List<File> packagedScripts = helper.listPackages();

			for(File packagedScript : packagedScripts){
				if(helper.validateZip(packagedScript, true)){

					logger.info( "Executing package: " + packagedScript.getAbsolutePath());
					FileServerFolder targetDir = null;
					try {
						targetDir = helper.extractPackageTemporarily(new FileInputStream(packagedScript));

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
		}
	}




}
