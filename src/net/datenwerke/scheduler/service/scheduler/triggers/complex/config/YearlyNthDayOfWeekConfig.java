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
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Months;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Nth;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class YearlyNthDayOfWeekConfig extends DateTriggerConfig {

	
	/**
	 * Jeden yearlyNth(zweiten) yearlyDay(Dienstag) im yearlyMonth2(Dezember)
	 */
	@ExposeToClient
	private Nth yearlyNth;
	
	@ExposeToClient
	private Days yearlyDay;
	
	@ExposeToClient
	private Months yearlyMonth;
	
	/**
	 * Defines the nth ({@link Nth}) day in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_Nth_DAY_OF_WEEK_IN_MONTH}</p>
	 * @return
	 */
	public Nth getYearlyNth() {
		return yearlyNth;
	}
	
	/**
	 * Defines the nth ({@link Nth}) day in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_Nth_DAY_OF_WEEK_IN_MONTH}</p>
	 * @param yearlyNth
	 */
	public void setYearlyNth(Nth yearlyNth) {
		this.yearlyNth = yearlyNth;
	}
	
	/**
	 * Defines the day ({@link Days}) in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_Nth_DAY_OF_WEEK_IN_MONTH}</p>
	 * @return
	 */
	public Days getYearlyDay() {
		return yearlyDay;
	}
	
	/**
	 * Defines the day ({@link Days}) in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_Nth_DAY_OF_WEEK_IN_MONTH}</p>
	 * @param yearlyDay
	 */
	public void setYearlyDay(Days yearlyDay) {
		this.yearlyDay = yearlyDay;
	}
	
	/**
	 * Defines the month ({@link Months}) in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_Nth_DAY_OF_WEEK_IN_MONTH}</p>
	 * @return
	 */
	public Months getYearlyMonth() {
		return yearlyMonth;
	}
	
	/**
	 * Defines the month ({@link Months}) in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_Nth_DAY_OF_WEEK_IN_MONTH}</p>
	 * @param yearlyMonth2
	 */
	public void setYearlyMonth(Months yearlyMonth) {
		this.yearlyMonth = yearlyMonth;
	}
	
}
