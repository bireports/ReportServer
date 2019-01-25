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

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.configservice.service.configservice.hookers.ConfigDirApplicationPropertiesProvider;
import net.datenwerke.rs.configservice.service.configservice.hooks.ConfigStoreHook;
import net.datenwerke.rs.configservice.service.configservice.terminal.ConfigCommand;
import net.datenwerke.rs.configservice.service.configservice.terminal.ConfigReloadCommand;
import net.datenwerke.rs.configservice.service.configservice.terminal.ConfigSubCommandHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesProviderHook;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConfigStartup {


	public static final String FSIMPORT_DIR = "fsimport";
	public static final String LIB_DIR = "lib";

	@Inject
	public ConfigStartup(
		HookHandlerService hookHandlerService,
		
		Provider<FileServerConfigStore> fileServerConfigStore,
		Provider<ConfigDirConfigStore> configDirConfigStore,
		
		Provider<ConfigCommand> configCommand,
		Provider<ConfigReloadCommand> configReloadCommand,

		final Provider<ConfigService> configService,
		final Provider<ConfigDirService> configDirService,
		final FileserverImportHelper importHelper,
		
		ConfigDirApplicationPropertiesProvider configDirApplicationPropertiesProvider
		) {
		
		hookHandlerService.attachHooker(ConfigStoreHook.class, new ConfigStoreHook(configDirConfigStore));
		hookHandlerService.attachHooker(ConfigStoreHook.class, new ConfigStoreHook(fileServerConfigStore), HookHandlerService.PRIORITY_HIGH);
		
		hookHandlerService.attachHooker(TerminalCommandHook.class, configCommand);
		hookHandlerService.attachHooker(ConfigSubCommandHook.class, configReloadCommand);
		
		hookHandlerService.attachHooker(ApplicationPropertiesProviderHook.class, configDirApplicationPropertiesProvider);
		
		/* Add libs from LIB_DIR to webapp classloader */
		new LibDirClasspathHelper(configDirService.get()).loadLibs();
		
		
		/* Import fileserver configdir */
		hookHandlerService.attachHooker(LateInitHook.class, new LateInitHook() {
			@Override
			public void initialize() {
				if(configDirService.get().isEnabled()){
					File importDir = new File(configDirService.get().getConfigDir(), FSIMPORT_DIR);
					if(importDir.exists() && importDir.isDirectory()){

						try { /* import files*/
							FileObject fileObject = VFS.getManager().resolveFile(importDir.getCanonicalPath());
							FileObject[] files = fileObject.findFiles(Selectors.EXCLUDE_SELF);

							for(FileObject f : files){
								try {
									importHelper.importFile(new File(f.getName().getPath()));
									
								} catch (IOException e) {
									e.printStackTrace();
								} catch (ObjectResolverException e) {
									e.printStackTrace();
								}
							}

						} catch (FileSystemException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
							
						configService.get().clearCache();
					}
					
				}
			}
		}, HookHandlerService.PRIORITY_HIGH);
	}
	
	
}
