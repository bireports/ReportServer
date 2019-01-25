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
 
 
package net.datenwerke.rs.scripting.service.jobs;

import java.util.HashMap;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.inject.Inject;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.ReportServerJob;
import net.datenwerke.rs.scripting.service.scripting.ScriptResult;
import net.datenwerke.rs.scripting.service.scripting.SimpleScriptingService;
import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.exceptions.JobExecutionException;
import net.datenwerke.scheduler.service.scheduler.jobs.BaseJob__;

@Entity
@Table(name="SCHED_EXECUTE_SCRIPT_JOB")
@Inheritance(strategy=InheritanceType.JOINED)
@AssociationOverride(name=BaseJob__.baseProperties, 
                     joinTable=@JoinTable(name="SCHED_SCR_EXE_JOB_2_PROP"))
public class ScriptExecuteJob extends ReportServerJob {

	@Inject @Transient private FileServerService fileServer;
	@Inject @Transient private SimpleScriptingService scriptService;
	@Inject @Transient private TerminalService terminalService;
	
	private Long scriptId;

	private String arguments = "";
	
	@Transient
	private ScriptResult scriptResult;
	
	@Override
	public void execute() throws JobExecutionException {
		FileServerFile script = getScript();
		try {
			TerminalSession session = terminalService.getUnscopedTerminalSession();
			session.setCheckRights(false);
			
			VFSLocation location = session.getFileSystem().getLocationFor(script);
			session.getFileSystem().setLocation(location.getParentLocation());
			
			try{
				if(null != getUser())
					authenticatiorServiceProvider.get().setAuthenticatedInThread(getUser().getId());
				
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("isScheduledScript", true);
		
				scriptResult = scriptService.executeScript(script, session, objectMap, arguments == null ? "" : arguments);
			}finally{
				if(null != getUser())
					authenticatiorServiceProvider.get().logoffUserInThread();
			}
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public FileServerFile getScript() {
		return (FileServerFile) fileServer.getNodeById(scriptId);
	}
	
	public void setScript(FileServerFile script){
		this.scriptId = script.getId();
	}

	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}

	public Long getScriptId() {
		return scriptId;
	}
	
	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	@Override
	public void adjustJobEntryForSuccess(JobEntry jobEntry) {
		super.adjustJobEntryForSuccess(jobEntry);
		
		String stdout = scriptResult.getStdout();
		if(null != stdout)
			jobEntry.addHistoryProperty("stdout", stdout);
		
		Object result = scriptResult.getResult();
		if(null != result)
			jobEntry.addHistoryProperty("result", result.toString());
	}

}
