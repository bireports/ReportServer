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
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.DailyPattern;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class DailyConfig extends DateTriggerConfig {
	
	/**
	 * Daily, every N(3) Days
	 */
	@ExposeToClient
	private Integer dailyN = 1;
	
	@ExposeToClient
	private DailyPattern pattern = DailyPattern.DAILY_EVERY_Nth_DAY;
	
	/**
	 * Defines the period with which the trigger should fire (every nth day).
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#DAILY}</p>
	 * @return
	 */
	public Integer getDailyN() {
		return dailyN;
	}
	
	/**
	 * Defines the period with which the trigger should fire (every nth day).
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#DAILY}</p>
	 * 
	 * @param dailyN
	 */
	public void setDailyN(Integer dailyN) {
		if(null != dailyN && dailyN > 1000)
			throw new IllegalArgumentException("Are you sure that you want to schedule something every " + dailyN + " days?");
		this.dailyN = dailyN;
	}

	public void setPattern(DailyPattern pattern) {
		this.pattern = pattern;
	}

	public DailyPattern getPattern() {
		return pattern;
	}
}
