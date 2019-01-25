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
 
 
package net.datenwerke.rs.incubator.client.jasperutils;

import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.base.client.jasperutils.dto.JasperParameterProposalDto;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.incubator.client.jasperutils.rpc.JasperUtilsRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class JasperUtilsDao extends Dao {

	private final JasperUtilsRpcServiceAsync rpcService;
	
	@Inject
	public JasperUtilsDao(
		JasperUtilsRpcServiceAsync rpcService
		){
		this.rpcService = rpcService;
	}
	
	public void proposeParametersFor(JasperReportDto jasperReportDto,
			AsyncCallback<List<JasperParameterProposalDto>> callback){
		rpcService.proposeParametersFor(jasperReportDto, transformAndKeepCallback(callback));
	}
	
	public void addParametersFor(JasperReportDto jasperReportDto,
			List<JasperParameterProposalDto> proposalDtos,
			AsyncCallback<JasperReportDto> callback){
		rpcService.addParametersFor(jasperReportDto, (List<JasperParameterProposalDto>) unproxy(proposalDtos), transformDtoCallback(callback));
	}
}
