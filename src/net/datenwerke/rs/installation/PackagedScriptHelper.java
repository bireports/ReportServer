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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;
import javax.servlet.ServletContext;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrMatcher;
import org.apache.commons.lang.text.StrTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.unzip.BasepathZipExtractConfigFactory;
import net.datenwerke.rs.license.service.LicenseService;
import net.datenwerke.rs.reportdoc.service.helper.ReportDocGroovyHelper;
import net.datenwerke.rs.scripting.service.scripting.SimpleScriptingService;
import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VirtualFileSystemDeamon;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.utils.zip.ZipExtractionConfig;
import net.datenwerke.rs.utils.zip.ZipUtilsService;
import net.datenwerke.security.service.usermanager.entities.User;

public class PackagedScriptHelper {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private static final String GROOVY = "groovy";
	
	private final static String PKG_DIR = "pkg";

	private final Provider<ReportDocGroovyHelper> groovyHelperProvider;
	private final LicenseService licenseService;
	private TerminalService terminalService;
	private SimpleScriptingService scriptingService;
	private FileServerService fileServerService;
	private ZipUtilsService zipUtilsService;
	private Provider<ServletContext> servletContext;

	private BasepathZipExtractConfigFactory zipExtractConfigFactory;

	@Inject
	public PackagedScriptHelper(
			Provider<ReportDocGroovyHelper> groovyHelperProvider,
			LicenseService licenseService,
			TerminalService terminalService, 
			SimpleScriptingService scriptingService, 
			FileServerService fileServerService, 
			BasepathZipExtractConfigFactory zipExtractConfigFactory, 
			ZipUtilsService zipUtilsService, 
			
			Provider<ServletContext> servletContext
			) {
		
		this.groovyHelperProvider = groovyHelperProvider;
		this.licenseService = licenseService;
		this.terminalService = terminalService;
		this.scriptingService = scriptingService;
		this.fileServerService = fileServerService;
		this.zipExtractConfigFactory = zipExtractConfigFactory;
		this.zipUtilsService = zipUtilsService;
		this.servletContext = servletContext;
	}
	
	protected CompiledScript compile(String script) throws IOException, ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine groovy = manager.getEngineByName(GROOVY);
		Compilable compGroovy = (Compilable) groovy;
		return compGroovy.compile(script);
	}
	
	public File getPackageDirectory(){
		return new File(servletContext.get().getRealPath(""), PKG_DIR);
	}
	
	public FileServerFolder extractPackageTemporarily(InputStream is) throws FileNotFoundException, IOException{
		FileServerFolder targetDir = new FileServerFolder(UUID.randomUUID().toString());
		try{
			getFileServerTempDir().addChild(targetDir);
			fileServerService.persist(targetDir);
			fileServerService.merge(getFileServerTempDir());
	
			zipUtilsService.extractZip(is, zipExtractConfigFactory.create(targetDir));
			return targetDir;
		}catch(Exception e){
			fileServerService.forceRemove(targetDir);
			throw e;
		}
	}
	
	public FileServerFolder getFileServerTempDir(){
		FileServerFolder tmpDir = (FileServerFolder) fileServerService.getNodeByPath("/tmp", false);
		if(null == tmpDir){
			tmpDir = new FileServerFolder("tmp");
			AbstractFileServerNode root = fileServerService.getRoots().get(0);
			root.addChild(tmpDir);
			fileServerService.persist(tmpDir);
			fileServerService.merge(root);
		}
	
		return tmpDir;
	}
	
	public void executePackage(FileServerFolder targetDir) {
		executePackage(targetDir, "");
	}
	
	public void executePackage(FileServerFolder targetDir, String scriptOptions) {
		FileServerFile configFile = null;
		for(FileServerFile f : targetDir.getChildrenOfType(FileServerFile.class)){
			if(f.getName().equals("package.xml")){
				configFile = f;
			}
		}
		if(null != configFile){
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			try {
				config.load(new ByteArrayInputStream(configFile.getData()));
				for(ConfigurationNode cfgNode : config.getRoot().getChildren()) {
					String name = cfgNode.getName();
					if("runScript".equals(name)){
						TerminalSession unscopedTerminalSession = terminalService.getUnscopedTerminalSession();
						unscopedTerminalSession.setCheckRights(false);
						VirtualFileSystemDeamon fs = unscopedTerminalSession.getFileSystem();
						VFSLocation targetLocation = fs.getLocationFor(targetDir);
						try {
							String scriptPath = cfgNode.getAttributes("src").get(0).getValue().toString();
							fs.setLocation(targetLocation);
							VFSLocation scriptLocation = fs.getLocation(scriptPath);
							FileServerFile scriptFile = (FileServerFile) scriptLocation.getObject();

							if(licenseService.isEnterprise()){
								scriptingService.executeScript(new String(scriptFile.getData()), unscopedTerminalSession, new HashMap<String, Object>() , "--baseDir " + targetLocation.toString() + "/" + ("".equals(scriptOptions)?"":" " + scriptOptions));
							} else {
								CompiledScript compiled = compile(new String(scriptFile.getData()));
								
								SimpleBindings binding = new SimpleBindings();
								ReportDocGroovyHelper groovyHelper = groovyHelperProvider.get();
								groovyHelper.setSession(unscopedTerminalSession);
								binding.put("GLOBALS", groovyHelper);
								
								StrTokenizer tokenizer = new StrTokenizer("--baseDir " + targetLocation.toString() + "/" + ("".equals(scriptOptions)?"":" " + scriptOptions));
								tokenizer.setDelimiterMatcher(StrMatcher.trimMatcher());
								tokenizer.setQuoteMatcher(StrMatcher.quoteMatcher());
								
								binding.put("args", tokenizer.getTokenArray());
								binding.put("tout", System.out);
								
								compiled.eval(binding);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}else if("copyFile".equals(name)){
						TerminalSession unscopedTerminalSession = terminalService.getUnscopedTerminalSession();
						unscopedTerminalSession.setCheckRights(false);
						VirtualFileSystemDeamon fs = unscopedTerminalSession.getFileSystem();
						VFSLocation targetLocation = fs.getLocationFor(targetDir);

						try {
							String srcPath = cfgNode.getAttributes("src").get(0).getValue().toString();
							fs.setLocation(targetLocation);
							VFSLocation srcLocation = fs.getLocation(srcPath);

							String dstPath = cfgNode.getAttributes("dst").get(0).getValue().toString();
							AbstractFileServerNode dstNode = fileServerService.getNodeByPath(dstPath);
							VFSLocation dstLocation = fs.getLocationFor(dstNode);

							dstLocation.getFilesystemManager().copyFilesTo(srcLocation, dstLocation, true);
						} catch (VFSException e) {
							e.printStackTrace();
						}
					}else{
						logger.error( "Error during initialisation. Unknown package operation :" + name);
					}
				}
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}

	}

	public List<File> listPackages(){
		File[] listFiles = getPackageDirectory().listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".zip") && validateZip(new File(dir, name), false);
			}
		});
		
		return Arrays.asList(listFiles);
	}
	
	public boolean validateZip(InputStream is, final boolean requireAutorun) {
		final List<ZipEntry> entries = new ArrayList<ZipEntry>();
		try {
			zipUtilsService.extractZip(is, new ZipExtractionConfig() {

				@Override
				public boolean isAllowedFile(ZipEntry entry) {
					return entry.getName().equals("package.xml");
				}

				@Override
				public void processContent(ZipEntry entry, byte[] content) {
					XMLConfiguration config = new XMLConfiguration();
					config.setDelimiterParsingDisabled(true);
					try {
						config.load(new ByteArrayInputStream(content));

						if(config.getRootNode().getName().equals("packagedscript")){
							boolean autorun = false;
							for(ConfigurationNode att : config.getRoot().getAttributes("autorun")){
								autorun = "true".equals(att.getValue());
							}
							if(autorun || !requireAutorun){
								entries.add(entry);
							}
						}
					} catch (ConfigurationException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return entries.size() > 0;
	}
	
	public boolean validateZip(File f, final boolean requireAutorun) {
		try {
			return validateZip(new FileInputStream(f), requireAutorun);
		} catch (FileNotFoundException e) {
		}
		return false;
	}
}
