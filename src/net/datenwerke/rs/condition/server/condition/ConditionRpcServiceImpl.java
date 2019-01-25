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
 
 
package net.datenwerke.rs.condition.server.condition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.condition.client.condition.dto.ConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionDto;
import net.datenwerke.rs.condition.client.condition.rpc.ConditionRpcService;
import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

@Singleton
public class ConditionRpcServiceImpl extends SecuredRemoteServiceServlet
		implements ConditionRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080108094731030733L;

	
	private final ConditionService conditionService;
	private final DtoService dtoService;
	private final SecurityService securityService;


	@Inject
	public ConditionRpcServiceImpl(
		ConditionService conditionService,
		DtoService dtoService,
		SecurityService securityService
		) {

		/* store objects */
		this.conditionService = conditionService;
		this.dtoService = dtoService;
		this.securityService = securityService;
	}


	@Override
	public List<ConditionDto> getConditions() throws ServerCallFailedException {
		List<ConditionDto> result = new ArrayList<ConditionDto>();
		for(Condition cond : conditionService.getConditions()){
			Report report = cond.getReport();
			if(null == report || ! (report instanceof TableReport))
				continue;
			
			if(securityService.checkRights(report, Read.class, Execute.class))
				result.add((ConditionDto) dtoService.createDto(cond));
		}
		
		return result;
	}


	@Override
	public List<String> getReplacementsFor(ConditionDto conditionDto)
			throws ServerCallFailedException {
		Condition cond = (Condition) dtoService.loadPoso(conditionDto);
		Report report = cond.getReport();
		if(null == report || ! (report instanceof TableReport))
			throw new ServerCallFailedException("Could not resolve report.");
		
		if(! securityService.checkRights(report, Read.class, Execute.class))
			throw new ViolatedSecurityException();
		
		return conditionService.getReplacementsFor(cond);
	}


	@Override
	public boolean executeCondition(ScheduleConditionDto scheduleCondition)
			throws ServerCallFailedException, ExpectedException {
		Condition cond = (Condition) dtoService.loadPoso(scheduleCondition.getCondition());
		Report report = cond.getReport();
		if(null == report || ! (report instanceof TableReport))
			throw new ServerCallFailedException("Could not resolve report.");
		
		if(! securityService.checkRights(report, Read.class, Execute.class))
			throw new ViolatedSecurityException();

		try {
			return conditionService.executeCondition(cond, scheduleCondition.getExpression());
		} catch (Exception e) {
			throw new ExpectedException(e);
		}
	}
	
	
}
