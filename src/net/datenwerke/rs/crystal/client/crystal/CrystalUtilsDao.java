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
 
 
package net.datenwerke.rs.crystal.client.crystal;

import java.util.List;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalParameterProposalDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;
import net.datenwerke.rs.crystal.client.crystal.rpc.CrystalUtilsRpcServiceAsync;

public class CrystalUtilsDao extends Dao {

	private final CrystalUtilsRpcServiceAsync rpcService;

	@Inject
	public CrystalUtilsDao(CrystalUtilsRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void proposeParametersFor(CrystalReportDto report, RsAsyncCallback<List<CrystalParameterProposalDto>> callback) {
		rpcService.proposeParametersFor(report, transformAndKeepCallback(callback));
	}

	public void addParametersFor(CrystalReportDto report, List<CrystalParameterProposalDto> proposalDtos, RsAsyncCallback<CrystalReportDto> rsAsyncCallback) {
		rpcService.addParametersFor(report, (List<CrystalParameterProposalDto>) unproxy(proposalDtos), transformDtoCallback(rsAsyncCallback));
	}
	
	public void hasCrystalLibraries(RsAsyncCallback<Boolean> callback) {
		rpcService.hasCrystalLibraries(transformAndKeepCallback(callback));
	}
	
}
