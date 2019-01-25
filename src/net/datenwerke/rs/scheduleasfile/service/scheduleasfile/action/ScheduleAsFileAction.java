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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.compiledreportstore.CompiledReportStoreService;
import net.datenwerke.rs.compiledreportstore.entities.PersistentCompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.ExecutedReportFileReference;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceRole;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskFolder;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionExecutionException;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Entity
@Table(name="SCHED_ACTION_AS_FILE")
@Inheritance(strategy=InheritanceType.JOINED)
public class ScheduleAsFileAction extends AbstractAction {

	@Transient @Inject private HookHandlerService hookHandler;
	@Transient @Inject private Provider<SimpleJuel> simpleJuelProvider;
	@Transient @Inject private TsDiskService tsDiskService;
	@Transient @Inject private TeamSpaceService tsService;
	@Transient @Inject private CompiledReportStoreService compiledReportService;
	@Transient @Inject private Provider<EntityManager> entityManagerProvider ;
	
	@Transient private TeamSpace teamspace;
	@Transient private AbstractTsDiskNode folder;
	@Transient private Report report;
	
	private String name;
	private String description;
	private Long folderId;
	private Long teamspaceId;
	
	@Override
	public void execute(AbstractJob job) throws ActionExecutionException {
		if(! (job instanceof ReportExecuteJob))
			throw new ActionExecutionException("No idea what job that is");
		
		ReportExecuteJob rJob = (ReportExecuteJob) job;
		
		CompiledReport compiledReport = rJob.getExecutedReport();
		report = rJob.getReport();
		
		PersistentCompiledReport pReport = compiledReportService.toPersistenReport(compiledReport, report);
		
		ExecutedReportFileReference ref = new ExecutedReportFileReference();
		ref.setCompiledReport(pReport);
		ref.setOutputFormat(rJob.getOutputFormat());
		ref.setDescription(description);
		
		SimpleJuel juel = simpleJuelProvider.get();
		juel.addReplacement("now", new SimpleDateFormat("yyyyMMddhhmm").format(Calendar.getInstance().getTime()));
		String parsedName = null == name ? "" : juel.parse(name);
		ref.setName(parsedName);
		
		folder = tsDiskService.getNodeById(folderId);
		if(null == folder)
			throw new ActionExecutionException("could not load folder with id: " + folderId + ". Was it deleted?");
			
//			EntityManager em = entityManagerProvider.get();
//			em.lock(folder, LockModeType.PESSIMISTIC_WRITE);	
		teamspace = tsDiskService.getTeamSpaceFor(folder);
		
		if(null == teamspace)
			throw new ActionExecutionException("could not load teamspace for folder with id: " + folderId);
		
		if(!tsService.hasRole(rJob.getUser(), teamspace, TeamSpaceRole.USER))
			throw new ViolatedSecurityException("user " + rJob.getUser().getId() + " has insufficient rights to writo to teamspace " + teamspace.getId());
		
		if(null == folder || (! (folder instanceof TsDiskRoot ) && !(folder instanceof TsDiskFolder)))
			throw new ActionExecutionException("could not load folder with id: " + folderId);
		
		folder.addChild(ref);
		
		tsDiskService.persist(ref);
		tsDiskService.merge(folder);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Long getTeamspaceId() {
		return teamspaceId;
	}
	
	public void setTeamspaceId(Long teamspaceId) {
		this.teamspaceId = teamspaceId;
	}
	
	public TeamSpace getTeamspace() {
		return teamspace;
	}
	
	public AbstractTsDiskNode getFolder() {
		return folder;
	}
	
	public Report getReport() {
		return report;
	}
}
