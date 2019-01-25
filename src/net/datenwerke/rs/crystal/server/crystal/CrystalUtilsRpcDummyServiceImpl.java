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
 
 
package net.datenwerke.rs.crystal.server.crystal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalParameterProposalDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;
import net.datenwerke.rs.crystal.client.crystal.rpc.CrystalUtilsRpcService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

@Singleton
public class CrystalUtilsRpcDummyServiceImpl extends SecuredRemoteServiceServlet implements CrystalUtilsRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1872709659662933367L;

	@Inject
	public CrystalUtilsRpcDummyServiceImpl() {
	}

	@Override
	public List<CrystalParameterProposalDto> proposeParametersFor(CrystalReportDto report)
			throws ServerCallFailedException {
		return null;
	}

	@Override
	public CrystalReportDto addParametersFor(CrystalReportDto report, List<CrystalParameterProposalDto> proposalDtos)
			throws ServerCallFailedException {
		return null;
	}

	@Override
	public Boolean hasCrystalLibraries() throws ServerCallFailedException {
		return false;
	}
	
	

}
