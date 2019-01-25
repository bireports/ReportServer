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
 
 
package net.datenwerke.rs.incubator.service.xslt.terminal.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.incubator.service.xslt.locale.XsltCommandMessages;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;
import net.sf.saxon.lib.FeatureKeys;
import net.sf.saxon.lib.StandardErrorListener;

import com.google.inject.Inject;

public class XsltCommand implements TerminalCommandHook {

	public static final String BASE_COMMAND = "xslt";
	
	private final FileServerService fileService;
	private final SecurityService securityService;

	@Inject
	public XsltCommand(FileServerService fileService,
			SecurityService securityService) {
		this.fileService = fileService;
		this.securityService = securityService;
	}

	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@CliHelpMessage(
		messageClass = XsltCommandMessages.class,
		name = BASE_COMMAND,
		description = "commandXslt_description",
		nonOptArgs = {
			@NonOptArgument(name="stylesheet", description="commandXslt_stylesheet"),
			@NonOptArgument(name="intput", description="commandXslt_input"),
			@NonOptArgument(name="output", description="commandXslt_output")
		}
	)
	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) {
		List<String> args = parser.getNonOptionArguments();
		if(args.size() < 2)
			throw new IllegalArgumentException("Expected at least 2 arguments");

		try{
			FileServerFile stylesheet = (FileServerFile) session.getObjectResolver().getObject(args.get(0), Read.class);
			FileServerFile input = (FileServerFile) session.getObjectResolver().getObject(args.get(1), Read.class);
			
			boolean outputFile = args.size() >= 3;
			FileServerFile output = null;

			if(outputFile){
				try{
					output = (FileServerFile) session.getObjectResolver().getObject(args.get(2));
				}catch(Exception e){}
				if(null != output && ! securityService.checkRights(output, Write.class))
					throw new ViolatedSecurityException();
				
				if(null == output){
					AbstractFileServerNode parent = null;
					try {
						parent = (AbstractFileServerNode) session.getFileSystem().getCurrentLocation().getObject();
						if(! securityService.checkRights(parent, Write.class))
							throw new ViolatedSecurityException();
					} catch (VFSException e) {
						return new CommandResult(e.getMessage());
					}
				
					output = new FileServerFile();
					output.setName(args.get(2));
					if(null != parent)
						parent.addChild(output);
					fileService.persist(output);
				}
			}
			
			ByteArrayOutputStream errorOut = new ByteArrayOutputStream();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			
			transformerFactory.setAttribute(FeatureKeys.ALLOW_EXTERNAL_FUNCTIONS, "false");
			((StandardErrorListener)transformerFactory.getErrorListener()).setErrorOutput(new PrintStream(errorOut));
			
			Transformer trans = transformerFactory.newTransformer(new StreamSource(new StringReader(new String(stylesheet.getData()))));

			StringWriter out = new StringWriter();
			trans.transform(new StreamSource(new StringReader(new String(input.getData()))), new StreamResult(out));
			
			final String result = out.toString();
			
			String error = errorOut.toString();
			
			if(outputFile){
				output.setData(result.getBytes());
				fileService.merge(output);
				
				return new CommandResult("Transformation succeded: " + error);
			} else {
				return new CommandResult(result);
			}
			
		} catch(RuntimeException e){
			throw e;
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

}
