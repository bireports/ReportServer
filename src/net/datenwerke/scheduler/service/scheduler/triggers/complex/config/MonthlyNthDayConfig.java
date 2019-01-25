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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class MonthlyNthDayConfig extends DateTriggerConfig {

	/**
	 * Am monthlyNthDay(1). Tag jedes monthlyM(2). Monats
	 */
	@ExposeToClient	
	private Integer dayInMonth;
	
	@ExposeToClient
	private Integer month = 1;
	
	/**
	 * Defines the day in a month on which the trigger should fire.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY}</p>
     *
	 * @return
	 */
	public Integer getDayInMonth() {
		return dayInMonth;
	}

	/**
	 * Defines the day in a month on which the trigger should fire.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY}</p>
	 * @param monthlyNDay
	 */
	public void setDayInMonth(Integer monthlyNDay) {
		this.dayInMonth = monthlyNDay;
	}
	
	/**
	 * Defines the months on which the trigger should fire.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_Mth_MONTH}</p>
	 * 
	 * <p>Example: On the 5th ({@link #setDayInMonth(Integer)}) of every 2nd month (getMontlyM);
	 *
	 * @return
	 */
	public Integer getMonth() {
		return month;
	}
	
	/**
 	 * Defines the months on which the trigger should fire.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_Mth_MONTH}</p>
	 * 
	 * <p>Example: On the 5th ({@link #setDayInMonth(Integer)}) of every 2nd month (getMontlyM);
	 *
	 * @param monthlyM
	 */
	public void setMonth(Integer monthlyM) {
		if(null != monthlyM && monthlyM > 1000)
			throw new IllegalArgumentException("Are you sure that you want to schedule something every " + monthlyM + " months?");

		this.month = monthlyM;
	}
}
