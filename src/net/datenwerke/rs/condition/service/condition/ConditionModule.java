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
 
 
package net.datenwerke.rs.condition.service.condition;


import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ConditionModule extends AbstractModule {

	public static final String PROPERTY_PREFIX = "__schedule_condition_";
	public static final String PROPERTY_POSTFIX_CONDITION_ID = "_cond_id";
	public static final String PROPERTY_POSTFIX_EXPRESSION = "_expr";
	public static final String PROPERTY_FAILURE_STRATEGY = "__schedule_condition_f_strat";
	public static final String PROPERTY_RETRY_STRATEGY_UNIT = "__schedule_condition_retry_unit";
	public static final String PROPERTY_RETRY_STRATEGY_AMOUNT = "__schedule_condition_retry_amount";

	@Override
	protected void configure() {
		bind(ConditionService.class).to(ConditionServiceImpl.class).in(Singleton.class);
		
		bind(ConditionStartup.class).asEagerSingleton();
	}

}
