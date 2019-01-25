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
 
 
package net.datenwerke.rs.saiku.server.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.google.inject.name.Named;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.service.reportmanager.ReportDtoService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.saiku.SaikuRpcService;
import net.datenwerke.rs.saiku.client.saiku.dto.SaikuReportDto;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.saiku.OlapUtilService;
import net.datenwerke.rs.saiku.service.saiku.SaikuSessionContainer;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;

@Singleton
public class SaikuRpcServiceImpl extends SecuredRemoteServiceServlet implements SaikuRpcService {

	private static final long serialVersionUID = -4933426943425132953L;
	
	private final Provider<SecurityService> securityService;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SaikuSessionContainer> sessionContainer;
	private final ReportDtoService reportDtoService;
	private final DtoService dtoService;
	private final OlapUtilService olapService;
	
	
	@Inject
	public SaikuRpcServiceImpl(
			Provider<SecurityService> securityService,
			Provider<AuthenticatorService> authenticatorServiceProvider,
			Provider<SaikuSessionContainer> sessionContainer, 
			OlapUtilService olapService,
			ReportDtoService reportDtoService,
			DtoService dtoService
			) {
		this.securityService = securityService;
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.sessionContainer = sessionContainer;
		this.olapService = olapService;
		this.reportDtoService = reportDtoService;
		this.dtoService = dtoService;
	}

	@SecurityChecked
	@Override
	public void stashReport(String token, @Named("node") SaikuReportDto reportDto) {
		Report report = reportDtoService.getReport(reportDto);
		
		if(!securityService.get().checkRights(report, Execute.class))
			throw new ViolatedSecurityException();
		
		if(report instanceof SaikuReport){
			sessionContainer.get().putReport(token, (SaikuReport) report);
		}else{
			throw new IllegalArgumentException("invalid report type: was: " + report.getClass() + " expected SaikuReport");
		}
	}

	@SecurityChecked(
			argumentVerification = {
				@ArgumentVerification(
					name = "node",
					isDto = true,
					verify = @RightsVerification(rights=Read.class)
				)
			}
		)
	@Override
	public ListLoadResult<String> loadCubesFor(
			@Named("node") MondrianDatasourceDto dto)
			throws ServerCallFailedException {
		MondrianDatasource datasource = (MondrianDatasource) dtoService.loadPoso(dto);

		try {
			List<String> cubes = olapService.getCubes(datasource);
			return new ListLoadResultBean<String>(cubes);
		} catch (Exception e) {
			throw new ServerCallFailedException(e);
		}
	}
	
}
