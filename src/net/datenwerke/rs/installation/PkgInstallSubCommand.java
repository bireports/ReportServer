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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.hooks.SubCommand;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.security.service.security.rights.Read;

public class PkgInstallSubCommand implements SubCommand {

	private static final String BASE_COMMAND = "install";
	private Provider<PackagedScriptHelper> packageScriptHelper;
	private FileServerService fileServerService;

	@Inject
	public PkgInstallSubCommand(Provider<PackagedScriptHelper> packageScriptHelper, FileServerService fileServerService) {
		this.packageScriptHelper = packageScriptHelper;
		this.fileServerService = fileServerService;
		
	}
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		String packageName = parser.getNonOptionArguments().get(0);
		PackagedScriptHelper packagedScriptHelper = packageScriptHelper.get();
		if(parser.hasOption("d")){
			File packageFile = new File(packagedScriptHelper.getPackageDirectory(), packageName);
			if(!packageFile.exists()){
				throw new IllegalArgumentException("no such package " + packageName);
			}
			FileServerFolder uploadPackage = null;
			try {
				uploadPackage = packagedScriptHelper.extractPackageTemporarily(new FileInputStream(packageFile));
				packagedScriptHelper.executePackage(uploadPackage, getScriptArguments(parser.getArguments()));
				CommandResult cr = new CommandResult("ok");
				return cr;
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}finally{
				fileServerService.forceRemove(uploadPackage);
			}
		}else{
			Collection<Object> objects = session.getObjectResolver().getObjects(packageName, Read.class);
			if(objects.size() != 1)
				throw new IllegalArgumentException("expression must resolve to exactly one object.");
			
			Object pkgObj = objects.iterator().next();
			if(pkgObj instanceof FileServerFile){
				FileServerFile pkgFile = (FileServerFile) pkgObj;
				if(!packagedScriptHelper.validateZip(new ByteArrayInputStream(pkgFile.getData()), false)){
					throw new IllegalArgumentException("not a valid package");
				}
				FileServerFolder uploadPackage = null;
				try {
					uploadPackage = packagedScriptHelper.extractPackageTemporarily(new ByteArrayInputStream(pkgFile.getData()));
					
					packagedScriptHelper.executePackage(uploadPackage, getScriptArguments(parser.getArguments()));
					CommandResult cr = new CommandResult("ok");
					return cr;
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}finally{
					fileServerService.forceRemove(uploadPackage);
				}
				
			}else{
				throw new IllegalArgumentException("no such package");
			}
				
		}

	}
	
	private String getScriptArguments(String[] opts){
		boolean seenEndOfOptions = false;
		
		for(int i=0 ; i < opts.length; i++){
			if(seenEndOfOptions)
				return opts[i];

			if("--".equals(opts[i]))
				seenEndOfOptions = true;
			
		}
		
		return "";
	}

	
	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		if(autocompleteHelper.getParser().hasOption("d")){
			for(File f : packageScriptHelper.get().listPackages()){
				autocompleteHelper.addAutocompleteNamesForToken(4, f.getName());
			}
		}
		
	}

	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}

}
