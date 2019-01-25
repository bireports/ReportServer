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
 
 
package net.datenwerke.scheduler.client.scheduler.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface SchedulerMessages extends Messages {
	
	public static SchedulerMessages INSTANCE = GWT.create(SchedulerMessages.class);
	
	String enumLabelHours();
	String enumLabelMinutes();

	String enumLabelFirst();
	String enumLabelSecond();
	String enumLabelThird();
	String enumLabelFourth();
	String enumLabelLast();
	
	String enumLabelJanuary();
	String enumLabelFebruary();
	String enumLabelMarch();
	String enumLabelApril();
	String enumLabelMay();
	String enumLabelJune();
	String enumLabelJuly();
	String enumLabelAugust();
	String enumLabelSeptember();
	String enumLabelOctober();
	String enumLabelNovember();
	String enumLabelDecember();
	
	String enumLabelMonday();
	String enumLabelTuesday();
	String enumLabelWednesday();
	String enumLabelThursday();
	String enumLabelFriday();
	String enumLabelSaturday();
	String enumLabelSunday();
	
	String enumLabelDay();
	String enumLabelWorkingDay();
	String enumLabelWeekendDay();
	
	String enumLabelExecutionStatusInactive();
	String enumLabelExecutionStatusWaiting();
	String enumLabelExecutionStatusExecuting();
	String enumLabelExecutionStatusActions();
	String enumLabelExecutionStatusBadFailure();
	
	String enumLabelOutcomeSuccess();
	String enumLabelOutcomeFailure();
	String enumLabelOutcomeVeto();
	String enumLabelOutcomeExecuting();
}
