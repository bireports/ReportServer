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
 
 
package net.datenwerke.rs.crystal.client.crystal.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalParameterProposalDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;

@RemoteServiceRelativePath("crystalutils")
public interface CrystalUtilsRpcService extends RemoteService{
	
	public List<CrystalParameterProposalDto> proposeParametersFor(CrystalReportDto report) throws ServerCallFailedException;

	public CrystalReportDto addParametersFor(CrystalReportDto report, List<CrystalParameterProposalDto> proposalDtos) throws ServerCallFailedException;

	Boolean hasCrystalLibraries() throws ServerCallFailedException;

}
