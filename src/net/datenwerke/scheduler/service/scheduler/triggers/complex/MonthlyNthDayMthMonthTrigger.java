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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.DateTriggerConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.MonthlyNthDayConfig;

import org.apache.commons.lang.time.DateUtils;

@Entity
@Table(name="SCHED_TRIG_MON_DAY_O_MON")
public class MonthlyNthDayMthMonthTrigger extends DateTrigger<MonthlyNthDayConfig>{

	public MonthlyNthDayMthMonthTrigger(){
		this(null);
	}

	public MonthlyNthDayMthMonthTrigger(MonthlyNthDayConfig config) {
		super(config);
	}

	public Date nextIncludedDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		if(cal.get(Calendar.DAY_OF_MONTH) > getConfig().getDayInMonth())
			cal.add(Calendar.MONTH, 1);
		
		cal.set(Calendar.DAY_OF_MONTH, getConfig().getDayInMonth());
		
		return cal.getTime();
	}
	
	public boolean isGoodMonth(Date d){
		Calendar first = new GregorianCalendar();
		first.setTime(getFirstFireTime());
		first.set(Calendar.DAY_OF_MONTH, 1);
		first = DateUtils.truncate(first, Calendar.DATE);
		
		Calendar dest = new GregorianCalendar();
		dest.setTime(d);
		dest.set(Calendar.DAY_OF_MONTH, 1);
		dest = DateUtils.truncate(dest, Calendar.DATE);
		
		if(first.after(dest))
			return false;
		
		int diff = 0;
		while(first.before(dest)){
			first.add(Calendar.MONTH, 1);
			diff++;
		}
		
		return 0 == diff % getConfig().getMonth();
	}
	
	
	@Override
	protected Date getFirstIncludedDayBefore(Date date){
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, getConfig().getMonth() * -32);
		cal.setTime(_computeNextFireTime(cal.getTime(), true));
		
		Date last = null;
		while(! date.before(cal.getTime())){
			last = cal.getTime();
			cal.setTime(_computeNextFireTime(cal.getTime(), true));
		}
		
		if(null != last)
			return last;
		
		throw new IllegalArgumentException("The first fire time comes before the submitted date ... this is your problem!"); //$NON-NLS-1$
	}
	
	@Override
	public Date _computeNextFireTime(Date afterTime, boolean isFirstRun){
		if(isGoodMonth(afterTime)){
			Date adjustedDate = isFirstRun ? DateUtils.addSeconds(afterTime,1) : afterTime;
			Date next = getNthFireTimeOfDayAfter(nextIncludedDay(adjustedDate), 1);
			if(null != next && isGoodMonth(next)){
				/* reset seconds */
				next = DateUtils.truncate(next, Calendar.MINUTE);
				return next; /* we have won */
			} else if(null != next){
				while(! isGoodMonth(next))
					next = DateUtils.addDays(next, -1);

				/* reset seconds */
				next = DateUtils.truncate(next, Calendar.MINUTE);
				return next;
			}
		} 
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(afterTime);
		cal = DateUtils.truncate(cal, Calendar.DATE);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		return _computeNextFireTime(cal.getTime(), false);
	}

	@Override
	public Date computeFirstFireTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(config.getFirstExecution());
		cal.setTime(nextIncludedDay(cal.getTime()));
		
		/* get first time */
		switch(config.getDailyRepeatType()){
		case ONCE:
			cal.set(Calendar.HOUR_OF_DAY,config.getAtTime().getHour());
			cal.set(Calendar.MINUTE,config.getAtTime().getMinutes());
			cal.set(Calendar.SECOND, 0);
			break;
		case BOUNDED_INTERVAL:
			cal.set(Calendar.HOUR_OF_DAY,config.getTimeRangeStart().getHour());
			cal.set(Calendar.MINUTE,config.getTimeRangeStart().getMinutes());
			cal.set(Calendar.SECOND, 0);
			break;
		}
		
		return cal.getTime();
	}

	
	
	@Override
	public boolean consumes(DateTriggerConfig config) {
		return
		(	super.consumes(config) &&
				
			config instanceof MonthlyNthDayConfig &&
			
			null != ((MonthlyNthDayConfig)config).getMonth() &&
			null != ((MonthlyNthDayConfig)config).getDayInMonth()
		);
	}

}
