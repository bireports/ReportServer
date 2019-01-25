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
 
 
package net.datenwerke.rs.condition.service.condition.hookers;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.condition.client.condition.dto.ConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ConditionFailureStrategy;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionList;
import net.datenwerke.rs.condition.service.condition.ConditionModule;
import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.RetryTimeUnitDto;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class ScheduleConditionConfigProvider implements
		ScheduleConfigProviderHook {

	private final DtoService dtoService;
	private final SecurityService securityService;
	private final ConditionService conditionService;
	
	@Inject
	public ScheduleConditionConfigProvider(
		DtoService dtoService,
		SecurityService securityService,
		ConditionService conditionService
		) {
		
		/*  store objects */
		this.dtoService = dtoService;
		this.securityService = securityService;
		this.conditionService = conditionService;
	}

	@Override
	public void adaptJob(ReportExecuteJob job, ReportScheduleDefinition scheduleDTO)
			throws InvalidConfigurationException {
		ScheduleConditionList list = scheduleDTO.getAdditionalInfo(ScheduleConditionList.class);
		if(null != list && list.getConditionList().size() > 0){
			int i = 0;
			for(ScheduleConditionDto conditionDto :  list.getConditionList()){
				Condition condition = (Condition) dtoService.loadPoso(conditionDto.getCondition());
				Report report = condition.getReport();
				if(null == report || ! (report instanceof TableReport))
					throw new InvalidConfigurationException("could not load report");
				
				if(! securityService.checkRights(report, Read.class, Execute.class))
					throw new InvalidConfigurationException("insufficient rights");
				
				job.setProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_CONDITION_ID, condition.getId().toString());
				job.setProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_EXPRESSION, conditionDto.getExpression());
				i++;
			}
			
			ConditionFailureStrategy strat = list.getFailureStrategy();
			if(null != strat)
				job.setProperty(ConditionModule.PROPERTY_FAILURE_STRATEGY, String.valueOf(strat.ordinal()));
			RetryTimeUnitDto unit = list.getRetryStrategyUnit();
			if(null != unit)
				job.setProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_UNIT, String.valueOf(unit.ordinal()));
			
			int amount = list.getRetryStrategyAmount();
			job.setProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_AMOUNT, String.valueOf(amount));
		}
	}


	@Override
	public void adaptScheduleDefinition(ReportScheduleDefinition rsd,
			ReportExecuteJob job) {
		ScheduleConditionList conditionList = new ScheduleConditionList();
		
		int i = 0;
		List<ScheduleConditionDto> list = new ArrayList<ScheduleConditionDto>();
		while(job.hasProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_CONDITION_ID)){
			ScheduleConditionDto scheduleCond = new ScheduleConditionDto();
			Long conditionId = Long.valueOf(job.getProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_CONDITION_ID).getValue());
			Condition condition = conditionService.getConditionById(conditionId);
			Report report = condition.getReport();
			if(null == report || ! (report instanceof TableReport))
				continue;
			
			if(! securityService.checkRights(report, Read.class, Execute.class))
				continue;
			
			scheduleCond.setCondition((ConditionDto) dtoService.createDto(condition));
			scheduleCond.setExpression(job.getProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_EXPRESSION).getValue());
			list.add(scheduleCond);
			
			i++;
		}
		
		if(! list.isEmpty()){
			if(job.hasProperty(ConditionModule.PROPERTY_FAILURE_STRATEGY)){
				int ordinal = Integer.valueOf(job.getProperty(ConditionModule.PROPERTY_FAILURE_STRATEGY).getValue());
				for(ConditionFailureStrategy strat : ConditionFailureStrategy.values()){
					if(strat.ordinal() == ordinal){
						conditionList.setFailureStrategy(strat);
						break;
					}
				}
			}
			if(job.hasProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_UNIT)){
				int ordinal = Integer.valueOf(job.getProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_UNIT).getValue());
				for(RetryTimeUnitDto unit : RetryTimeUnitDto.values()){
					if(unit.ordinal() == ordinal){
						conditionList.setRetryStrategyUnit(unit);
						break;
					}
				}
				
			}
			if(job.hasProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_AMOUNT)){
				int amount = Integer.parseInt(job.getProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_AMOUNT).getValue());
				conditionList.setRetryStrategyAmount(amount);
			}
			
			conditionList.setConditionList(list);
			rsd.addAdditionalInfo(conditionList);
		}
	}

}
