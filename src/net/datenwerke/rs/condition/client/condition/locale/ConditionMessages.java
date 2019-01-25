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
 
 
package net.datenwerke.rs.condition.client.condition.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ConditionMessages extends Messages {

	public final static ConditionMessages INSTANCE = GWT.create(ConditionMessages.class);
	
	String expressionLabel();

	String addConditionLabel();
	String removeConditionLabel();
	String removeAllConditionLabel();

	String conditionPageHeadline();
	String conditionPageDescription();

	String editConditionHeader(String name);

	String testConditionLabel();
	
	String removeAllConfirmHeading();
	String removeAllConfirmText();
	
	String addReplacementLabel();
	
	String schedulerPageHeadline();
	String schedulerPageDescription();
	
	String failureStrategyLabel();
	
	String skipLabel();
	String retryLabel();
	
	String minutesLabel();
	String hoursLabel();
	String daysLabel();
	String weeksLabel();
	String monthsLabel();
	String yearsLabel();
	
	String failureRetryUnitLabel();
	String failureRetryAmount();

	String addConditionHeader();

	String conditionHolds();
	String conditionFails();
	

	
}
