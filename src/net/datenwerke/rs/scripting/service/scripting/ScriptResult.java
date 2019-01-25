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
 
 
package net.datenwerke.rs.scripting.service.scripting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultExtension;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultList;

public class ScriptResult {

	private Object result;
	private String stdout;
	private boolean disabled = false;

	private List<CommandResultExtension> resultExtensions = new ArrayList<CommandResultExtension>();
	private Exception exception;
	
	private String contentType;
	
	public ScriptResult() {
	}
	
	public ScriptResult(Exception e) {
		this.exception = e;
	}
	
	public boolean hasException(){
		return null != exception;
	}
	
	public Exception getException(){
		return exception;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void setResultExtension(List<CommandResultExtension> resultExtension) {
		this.resultExtensions = resultExtension;
	}

	
	public void addResultExtension(CommandResultExtension resultExtension) {
		this.resultExtensions.add(resultExtension);
	}
	
	public void addResultExtensions(Collection<? extends CommandResultExtension> resultExtensions) {
		this.resultExtensions.addAll(resultExtensions);
	}

	public List<CommandResultExtension> getResultExtensions() {
		return resultExtensions;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public String getStdout() {
		return stdout;
	}
	
	public void setStdout(String stdout) {
		this.stdout = stdout;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public CommandResult asCommandResult() {
		if(getResult() instanceof CommandResult){
			CommandResult cr = (CommandResult) getResult();
			cr.addExtensions(getResultExtensions());
			
			return cr;
		} else {
			CommandResult cr = new CommandResult();
			
			if(null != getResult()){
				CommandResultList list = new CommandResultList(Arrays.asList(getResult().toString().split("\n")));
				list.setDenyBreakUp(true);
				cr.addEntry(list);
			}

			cr.addExtensions(getResultExtensions());
			
			if(null != stdout && !stdout.isEmpty()){
				Scanner sc = new Scanner(stdout);
				while(sc.hasNextLine()){
					cr.addResultLine(sc.nextLine());
				}
			}
			
			return cr;
		}
	}

	
}
