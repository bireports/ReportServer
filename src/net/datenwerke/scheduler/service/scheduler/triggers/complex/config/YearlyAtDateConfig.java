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
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Months;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class YearlyAtDateConfig extends DateTriggerConfig {

	/**
	 * Am yearlyNDay(8) yearlyMonth(Dezember)
	 */
	@ExposeToClient
	private Integer yearlyNDay;
	
	@ExposeToClient
	private Months yearlyMonth;
	
	/**
	 * Defines the day in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_AT_DATE}</p>
	 *
	 * @return
	 */
	public Integer getYearlyNDay() {
		return yearlyNDay;
	}
	
	/**
	 * Defines the day in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_AT_DATE}</p>
	 *
	 * @param yearlyNDay
	 */
	public void setYearlyNDay(Integer yearlyNDay) {
		this.yearlyNDay = yearlyNDay;
	}
	
	/**
	 * Defines the month in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_AT_DATE}</p>
	 * @return
	 */
	public Months getYearlyMonth() {
		return yearlyMonth;
	}
	
	/**
	 * Defines the month in a yearly pattern.
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#YEARLY} and {@link SeriesSubPattern} is set to {@link SeriesSubPattern#YEARLY_AT_DATE}</p>
	 * 
	 * @param yearlyMonth
	 */
	public void setYearlyMonth(Months yearlyMonth) {
		this.yearlyMonth = yearlyMonth;
	}
	
	public void setYearlyMonth(int month){
		this.yearlyMonth = Months.fromCalendarMonth(month);
	}
}
