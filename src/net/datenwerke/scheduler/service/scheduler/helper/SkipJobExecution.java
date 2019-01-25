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
 
 
package net.datenwerke.scheduler.service.scheduler.helper;

import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;

public class SkipJobExecution implements VetoJobExecution {

	private final String explanation;
	
	public SkipJobExecution(String explanation) {
		super();
		this.explanation = explanation;
	}

	@Override
	public VetoJobExecutionMode getMode() {
		return VetoJobExecutionMode.SKIP;
	}

	@Override
	public String getExplanation() {
		return explanation;
	}

	@Override
	public void updateTrigger(AbstractJob job, ExecutionLogEntry logEntry) {

	}

	@Override
	public RetryTimeUnit getRetryUnit() {
		return null;
	}

	@Override
	public int getRetryAmount() {
		return 0;
	}

}
