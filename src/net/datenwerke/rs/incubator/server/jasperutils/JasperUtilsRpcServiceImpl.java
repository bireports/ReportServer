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
 
 
package net.datenwerke.rs.incubator.server.jasperutils;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.jasperutils.dto.JasperParameterProposalDto;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperParameterProposal;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportParameterService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.incubator.client.jasperutils.rpc.JasperUtilsRpcService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class JasperUtilsRpcServiceImpl extends SecuredRemoteServiceServlet implements JasperUtilsRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5349153525496058426L;

	private final ReportService reportService;
	private final ReportParameterService parameterService;
	private final DtoService dtoService;
	private final JasperUtilsService jasperUtils;
	
	@Inject
	public JasperUtilsRpcServiceImpl(
		ReportService reportManagerService,
		ReportParameterService parameterService,
		DtoService dtoService,
		JasperUtilsService jasperUtils
		){
		
		this.reportService = reportManagerService;
		this.parameterService = parameterService;
		this.dtoService = dtoService;
		this.jasperUtils = jasperUtils;
	}
	
	@SecurityChecked(
			argumentVerification = {
				@ArgumentVerification(
					name = "report",
					isDto = true,
					verify = @RightsVerification(rights={Read.class, Write.class})
				)
			}
		)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<JasperParameterProposalDto> proposeParametersFor(
			@Named("report") JasperReportDto jasperReportDto) {
		
		/* load jasper report */
		JasperReport report = (JasperReport) dtoService.loadPoso(jasperReportDto);
		if(null == report || null == report.getMasterFile())
			return null;
		
		List<JasperParameterProposal> proposals = jasperUtils.extractParameters(report.getMasterFile().getContent());
		
		List<JasperParameterProposalDto> proposalDtos = new ArrayList<JasperParameterProposalDto>();
		for(JasperParameterProposal proposal : proposals)
			proposalDtos.add((JasperParameterProposalDto) dtoService.createDto(proposal));
		
		return proposalDtos;
	}

	@SecurityChecked(
			argumentVerification = {
				@ArgumentVerification(
					name = "report",
					isDto = true,
					verify = @RightsVerification(rights={Read.class, Write.class})
				)
			}
		)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public JasperReportDto addParametersFor(@Named("report") JasperReportDto jasperReportDto, List<JasperParameterProposalDto> proposalDtos) throws ExpectedException {
		/* load jasper report */
		JasperReport report = (JasperReport) dtoService.loadPoso(jasperReportDto);
		if(null == report)
			return null;
		
		for(JasperParameterProposalDto proposal : proposalDtos){
			if(null == proposal.getParameterProposal())
				continue;
			
			ParameterDefinition definition = (ParameterDefinition) dtoService.createPoso(proposal.getParameterProposal());
			
			/* init default values */
			definition.initWithDefaultValues();
			
			/* override with parameters from proposal */
			definition.setKey(proposal.getKey());
			definition.setName(proposal.getName());
			
			/* add to report/folder */
			parameterService.addParameterDefinition(report, definition);
		}

		/* persist parameter */
		reportService.merge(report);
		
		return (JasperReportDto) dtoService.createDto(report);
	}

}
