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
 
 
package net.datenwerke.rs.condition.service.condition.hookers;

import net.datenwerke.rs.condition.client.condition.dto.ConditionFailureStrategy;
import net.datenwerke.rs.condition.service.condition.ConditionModule;
import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.condition.service.condition.locale.ConditionMessages;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.helper.RetryJobExecution;
import net.datenwerke.scheduler.service.scheduler.helper.RetryTimeUnit;
import net.datenwerke.scheduler.service.scheduler.helper.SkipJobExecution;
import net.datenwerke.scheduler.service.scheduler.helper.VetoJobExecution;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerExecutionHook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class SchedulerVetoProvider implements SchedulerExecutionHook {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final ConditionService conditionService;
	
	@Inject
	public SchedulerVetoProvider(ConditionService conditionService) {
		this.conditionService = conditionService;
	}

	@Override
	public void jobExecutionAboutToStart(AbstractJob job,
			ExecutionLogEntry logEntry) {

	}

	@Override
	public void actionExecutionAboutToStart(AbstractJob job,
			ExecutionLogEntry logEntry) {

	}

	@Override
	public void executionEndedSuccessfully(AbstractJob job,
			ExecutionLogEntry logEntry) {

	}

	@Override
	public void executionEndedAbnormally(AbstractJob job,
			ExecutionLogEntry logEntry, Exception e) {

	}

	@Override
	public VetoJobExecution doesVetoExecution(AbstractJob job,
			ExecutionLogEntry logEntry) {
		if(! (job instanceof ReportExecuteJob))
			return null;
		
		try{
			ReportExecuteJob rJob = (ReportExecuteJob) job; 
			
			int i = 0;
			while(rJob.hasProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_CONDITION_ID)){
				String idStr = rJob.getProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_CONDITION_ID).getValue();
				String expressionStr = rJob.getProperty(ConditionModule.PROPERTY_PREFIX + i + ConditionModule.PROPERTY_POSTFIX_EXPRESSION).getValue();
				
				Long id = Long.valueOf(idStr);
				
				Condition cond = conditionService.getConditionById(id);
				if(null != cond){
					try{
						boolean result = conditionService.executeCondition(cond, expressionStr, rJob.getUser());
						if(! result){
							ConditionFailureStrategy strategy = getFailureStrategy(rJob);
							RetryTimeUnit unit = getRetryUnit(rJob);
							int amount = getRetryAmount(rJob);
							
							switch (strategy) {
							case SKIP:
								return new SkipJobExecution(ConditionMessages.INSTANCE.skipMsg(cond.getName()));
							case RETRY:
								return new RetryJobExecution(ConditionMessages.INSTANCE.retryMsg(cond.getName()), unit, amount);
							default:
								throw new IllegalArgumentException("Do not know strategy: " + strategy);
							}
						}
					} catch(Exception e){
						
						logger.warn( "Evaluating scheduler veto expression failed", e);
					}
				}
				i++;
			}
		}catch(Exception e){
			logger.warn( e.getMessage(), e);
		}
		
		return null;
	}

	private int getRetryAmount(ReportExecuteJob rJob) {
		if(rJob.hasProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_AMOUNT)){
			int amount = Integer.parseInt(rJob.getProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_AMOUNT).getValue());
			return amount;
		}
		return 1;
	}

	private RetryTimeUnit getRetryUnit(ReportExecuteJob rJob) {
		if(rJob.hasProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_UNIT)){
			int ordinal = Integer.valueOf(rJob.getProperty(ConditionModule.PROPERTY_RETRY_STRATEGY_UNIT).getValue());
			for(RetryTimeUnit unit : RetryTimeUnit.values()){
				if(unit.ordinal() == ordinal)
					return unit;
			}
			
		}
		return RetryTimeUnit.HOURS;
	}

	private ConditionFailureStrategy getFailureStrategy(ReportExecuteJob rJob) {
		if(rJob.hasProperty(ConditionModule.PROPERTY_FAILURE_STRATEGY)){
			int ordinal = Integer.valueOf(rJob.getProperty(ConditionModule.PROPERTY_FAILURE_STRATEGY).getValue());
			for(ConditionFailureStrategy strat : ConditionFailureStrategy.values()){
				if(strat.ordinal() == ordinal)
					return strat;
			}
		}
		return ConditionFailureStrategy.SKIP;
	}

	@Override
	public void informAboutVeto(AbstractJob job, ExecutionLogEntry logEntry,
			VetoJobExecution veto) {

	}

	@Override
	public void actionExecutionEndedAbnormally(AbstractJob job,
			AbstractAction action, ActionEntry actionEntry, Exception e) {
		
	}

	@Override
	public void jobExecutionEndedAbnormally(AbstractJob job, JobEntry jobEntry,
			Exception e) {
		
	}

	@Override
	public void actionExecutionEndedSuccessfully(AbstractJob job,
			AbstractAction action, ExecutionLogEntry logEntry) {
		// TODO Auto-generated method stub
		
	}

}
