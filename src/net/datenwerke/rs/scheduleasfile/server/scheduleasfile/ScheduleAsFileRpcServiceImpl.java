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
 
 
package net.datenwerke.rs.scheduleasfile.server.scheduleasfile;

import java.util.List;

import javax.inject.Singleton;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.compiledreportstore.CompiledReportStoreService;
import net.datenwerke.rs.compiledreportstore.entities.PersistentCompiledReport;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.server.reportexport.hooks.ReportExportViaSessionHook;
import net.datenwerke.rs.core.service.reportmanager.ReportDtoService;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECReportExecutorToken;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.rpc.ScheduleAsFileRpcService;
import net.datenwerke.rs.scheduleasfile.server.scheduleasfile.events.ExportReportIntoTeamSpaceFailedEvent;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.ExecutedReportFileReference;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceRole;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskFolder;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionExecutionException;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

@Singleton
public class ScheduleAsFileRpcServiceImpl extends SecuredRemoteServiceServlet
		implements ScheduleAsFileRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 366549503536730298L;

	private final ReportService reportService;
	private final DtoService dtoService;
	private final ReportExecutorService reportExecutorService;
	private final EventBus eventBus;
	private final TsDiskService tsService;
	private final CompiledReportStoreService compiledReportService;
	private final TeamSpaceService teamSpaceService;
	private final ReportDtoService reportDtoService;
	private final HookHandlerService hookHandlerService;

	private final SecurityService securityService;
	
	@Inject
	public ScheduleAsFileRpcServiceImpl(
		ReportService reportService,
		ReportDtoService reportDtoService,
		DtoService dtoService,
		ReportExecutorService reportExecutorService,
		EventBus eventBus,
		TsDiskService tsService,
		SecurityService securityService,
		CompiledReportStoreService compiledReportService,
		HookHandlerService hookHandlerService,
		TeamSpaceService teamSpaceService
		){
		
		this.reportService = reportService;
		this.reportDtoService = reportDtoService;
		this.dtoService = dtoService;
		this.reportExecutorService = reportExecutorService;
		this.eventBus = eventBus;
		this.tsService = tsService;
		this.securityService = securityService;
		this.compiledReportService = compiledReportService;
		this.hookHandlerService = hookHandlerService;
		this.teamSpaceService = teamSpaceService;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void exportIntoTeamSpace(@Named("report") ReportDto reportDto, String executorToke,
			String format, List<ReportExecutionConfigDto> configs,
			AbstractTsDiskNodeDto folder, String name, String description) throws ServerCallFailedException, ExpectedException {
		final ReportExecutionConfig[] configArray = getConfigArray(executorToke, configs);

		/* get a clean and unmanaged report from the database */
		Report orgReport = (Report) reportService.getReportById(reportDto.getId());
		Report referenceReport = reportDtoService.getReferenceReport(reportDto);
		
		/* check rights */
		if(! securityService.checkRights(referenceReport, Execute.class))
			throw new ViolatedSecurityException();

		/* get variant from orginal report to execute */
		Report adjustedReport = (Report) dtoService.createUnmanagedPoso(reportDto);
		final Report toExecute = orgReport.createTemporaryVariant(adjustedReport);
		
		for(ReportExportViaSessionHook hooker : hookHandlerService.getHookers(ReportExportViaSessionHook.class)){
			hooker.adjustReport(toExecute, configArray);
		}
		
		try {
			CompiledReport cReport = reportExecutorService.execute(toExecute, format, configArray);
			
			PersistentCompiledReport pReport = compiledReportService.toPersistenReport(cReport, orgReport);
			
			ExecutedReportFileReference ref = new ExecutedReportFileReference();
			ref.setCompiledReport(pReport);
			ref.setOutputFormat(format);
			ref.setDescription(description);
			ref.setName(name);
			
			AbstractTsDiskNode node = tsService.getNodeById(folder.getId());
			if(null == node || (! (node instanceof TsDiskRoot ) && !(node instanceof TsDiskFolder)))
				throw new ActionExecutionException("could not load folder with id: " + folder.getId());
			
			TeamSpace teamSpace = tsService.getTeamSpaceFor(node);
			if(! teamSpaceService.hasRole(teamSpace, TeamSpaceRole.USER))
				throw new InvalidConfigurationException("Insufficient TeamSpace rights");
			
			node.addChild(ref);
			
			tsService.persist(ref);
			tsService.merge(node);
		} catch(Exception e){
			eventBus.fireEvent(
				new ExportReportIntoTeamSpaceFailedEvent(reportDto, executorToke, format, configs, folder, name, description)	
			);
			
			throw new ExpectedException("Could not store report in teamspace: " + e.getMessage(), e);
		}
	}


	private ReportExecutionConfig[] getConfigArray(String executorToken,
			List<ReportExecutionConfigDto> configs) throws ExpectedException {
		ReportExecutionConfig[] configArray = new ReportExecutionConfig[configs.size()+1];
		for(int i = 0; i < configs.size(); i++)
			configArray[i] = (ReportExecutionConfig) dtoService.createPoso(configs.get(i));
		configArray[configs.size()] = new RECReportExecutorToken(executorToken);
		
		return configArray;
	}



	
}
