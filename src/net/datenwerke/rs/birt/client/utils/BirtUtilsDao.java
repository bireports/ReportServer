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
 
 
package net.datenwerke.rs.birt.client.utils;

import java.util.List;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.birt.client.reportengines.dto.BirtReportDto;
import net.datenwerke.rs.birt.client.utils.dto.BirtParameterProposalDto;
import net.datenwerke.rs.birt.client.utils.rpc.BirtUtilsRpcServiceAsync;

public class BirtUtilsDao extends Dao {

	private final BirtUtilsRpcServiceAsync rpcSerice;

	@Inject
	public BirtUtilsDao(BirtUtilsRpcServiceAsync rpcSerice) {
		this.rpcSerice = rpcSerice;
	}
	
	public void proposeParametersFor(BirtReportDto report, RsAsyncCallback<List<BirtParameterProposalDto>> callback) {
		rpcSerice.proposeParametersFor(report, transformAndKeepCallback(callback));
	}

	public void addParametersFor(BirtReportDto report, List<BirtParameterProposalDto> proposalDtos, RsAsyncCallback<BirtReportDto> rsAsyncCallback) {
		rpcSerice.addParametersFor(report, (List<BirtParameterProposalDto>) unproxy(proposalDtos), transformDtoCallback(rsAsyncCallback));
	}

}
