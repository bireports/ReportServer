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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex;

import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.DailyConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.DateTriggerConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.MonthlyNthDayConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.MonthlyNthDayOfWeekConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.WeeklyConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.YearlyAtDateConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.YearlyNthDayOfWeekConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.DailyPattern;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DateTriggerFactory {

	private final Provider<DailyNthdayTrigger> dailyNthDayTriggerProvider;
	private final Provider<DailyWorkdayTrigger> dailyWorkdayTriggerProvider;
	private final Provider<WeeklyTrigger> weeklyTriggerProvider;
	private final Provider<MonthlyNamedDayTrigger> monthlyNamedDayTriggerProvider;
	private final Provider<MonthlyNthDayMthMonthTrigger> monthlyNthDayMthMonthTriggerProvider;
	private final Provider<YearlyNamedDayTrigger> yearlyNamedDayTriggerProvider;
	private final Provider<YearlyAtDateTrigger> yearlyAtDateTriggerProvider;


	@Inject
	public DateTriggerFactory(
			Provider<DailyNthdayTrigger> dailyNthDayTriggerProvider,
			Provider<DailyWorkdayTrigger> dailyWorkdayTriggerProvider,
			Provider<WeeklyTrigger> weeklyTriggerProvider,
			Provider<MonthlyNamedDayTrigger> monthlyNamedDayTriggerProvider,
			Provider<MonthlyNthDayMthMonthTrigger> monthlyNthDayMthMonthTriggerProvider,
			Provider<YearlyAtDateTrigger> yearlyAtDateTriggerProvider,
			Provider<YearlyNamedDayTrigger> yearlyNamedDayTriggerProvider) {
		this.dailyNthDayTriggerProvider = dailyNthDayTriggerProvider;
		this.dailyWorkdayTriggerProvider = dailyWorkdayTriggerProvider;
		this.weeklyTriggerProvider = weeklyTriggerProvider;
		this.monthlyNamedDayTriggerProvider = monthlyNamedDayTriggerProvider;
		this.monthlyNthDayMthMonthTriggerProvider = monthlyNthDayMthMonthTriggerProvider;
		this.yearlyAtDateTriggerProvider = yearlyAtDateTriggerProvider;
		this.yearlyNamedDayTriggerProvider = yearlyNamedDayTriggerProvider;
	}



	public <C extends DateTriggerConfig> DateTrigger<C> createTrigger(C config){
		if(config instanceof DailyConfig){
			if(DailyPattern.DAILY_EVERY_Nth_DAY == ((DailyConfig)config).getPattern()){
				DailyNthdayTrigger trigger = dailyNthDayTriggerProvider.get();
				trigger.setConfig((DailyConfig)config);
				return (DateTrigger<C>) trigger;
			} else if(DailyPattern.DAILY_WORKDAY == ((DailyConfig)config).getPattern()){
				DailyWorkdayTrigger trigger = dailyWorkdayTriggerProvider.get();
				trigger.setConfig((DailyConfig) config);
				return (DateTrigger<C>) trigger;
			}
		} else if(config instanceof WeeklyConfig){
			WeeklyTrigger trigger = weeklyTriggerProvider.get();
			trigger.setConfig((WeeklyConfig) config);
			return (DateTrigger<C>) trigger;
		} else if(config instanceof MonthlyNthDayConfig){
			MonthlyNthDayMthMonthTrigger trigger = monthlyNthDayMthMonthTriggerProvider.get();
			trigger.setConfig((MonthlyNthDayConfig) config);
			return (DateTrigger<C>) trigger;
		} else if(config instanceof MonthlyNthDayOfWeekConfig){
			MonthlyNamedDayTrigger trigger = monthlyNamedDayTriggerProvider.get();
			trigger.setConfig((MonthlyNthDayOfWeekConfig) config);
			return (DateTrigger<C>) trigger;
		} else if(config instanceof YearlyAtDateConfig){
			YearlyAtDateTrigger trigger = yearlyAtDateTriggerProvider.get();
			trigger.setConfig((YearlyAtDateConfig) config);
			return (DateTrigger<C>) trigger;
		} else if(config instanceof YearlyNthDayOfWeekConfig){
			YearlyNamedDayTrigger trigger = yearlyNamedDayTriggerProvider.get();
			trigger.setConfig((YearlyNthDayOfWeekConfig) config);
			return (DateTrigger<C>) trigger;
		}
		return null;
	}
}
