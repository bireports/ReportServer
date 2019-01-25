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
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Days;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Nth;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class MonthlyNthDayOfWeekConfig extends DateTriggerConfig {

	/**
	 * Am monthlyNth(zweiten), monthlyDay(Dienstag) jedes monthlyO(4).Monats
	 */
	@ExposeToClient
	private Nth monthlyNth;
	
	@ExposeToClient
	private Days monthlyDay;
	
	@ExposeToClient
	private Integer month;
	
	/**
	 * Defines the nth ({@link Nth}) day of monthly pattern.
	 *  
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_OF_WEEK_Mth_MONTH}</p>
	 * 
	 * @return
	 */
	public Nth getMonthlyNth() {
		return monthlyNth;
	}
	
	/**
	 * Defines the nth ({@link Nth}) day of monthly pattern.
	 *  
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_OF_WEEK_Mth_MONTH}</p>
	 * 
	 * @param monthlyNth
	 */
	public void setMonthlyNth(Nth monthlyNth) {
		this.monthlyNth = monthlyNth;
	}
	
	/**
	 * Defines the type of day ({@link Days}) of monthly pattern.
	 *  
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_OF_WEEK_Mth_MONTH}</p>
	 * 
	 * @return
	 */
	public Days getMonthlyDay() {
		return monthlyDay;
	}
	
	/**
	 * Defines the type of day ({@link Days}) of monthly pattern.
	 *  
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_OF_WEEK_Mth_MONTH}</p>
	 * 
	 * @param monthlyDay
	 */
	public void setMonthlyDay(Days monthlyDay) {
		this.monthlyDay = monthlyDay;
	}
	
	/**
	 * Defines the monthly period of monthly pattern.
	 *  
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_OF_WEEK_Mth_MONTH}</p>
	 * 
	 * @return
	 */
	public Integer getMonth() {
		return month;
	}
	
	/**
	 * Defines the monthly period of monthly pattern.
	 *  
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#MONTHLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#MONTHLY_Nth_DAY_OF_WEEK_Mth_MONTH}</p>
	 * 
	 * @param month
	 */
	public void setMonth(Integer month) {
		if(null != month && month > 1000)
			throw new IllegalArgumentException("Are you sure that you want to schedule something every " + month + " months?");
		
		this.month = month;
	}
	

}
