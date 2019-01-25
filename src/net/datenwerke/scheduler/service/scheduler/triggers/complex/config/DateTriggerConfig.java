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

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.DailyRepeatType;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.EndTypes;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.TimeUnits;

@Entity
@Table(name="DATE_TRIGGER_CONFIG")
@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class DateTriggerConfig {

	@ExposeToClient
	protected Date firstExecution = new Date();
	
	@ExposeToClient
	@EnclosedEntity
	protected EndTypes endType = EndTypes.COUNT;
	
	@ExposeToClient
	protected Date lastExecution;
	
	@ExposeToClient
	protected Integer numberOfExecutions = 1;

	@ExposeToClient
	@Embedded
	@EnclosedEntity
	@AttributeOverrides({
		@AttributeOverride(name="hour", column=@Column(name="at_time_hour")),
		@AttributeOverride(name="minutes", column=@Column(name="at_time_minutes"))
	})
	private Time atTime = new Time();
	
	@ExposeToClient
	private DailyRepeatType dailyRepeatType = DailyRepeatType.ONCE;

	@ExposeToClient
	@Embedded
	@EnclosedEntity
	@AttributeOverrides({
		@AttributeOverride(name="hour", column=@Column(name="time_range_start_hour")),
		@AttributeOverride(name="minutes", column=@Column(name="time_range_start_minutes"))
	})
	private Time timeRangeStart = new Time();
	
	@ExposeToClient
	@Embedded
	@EnclosedEntity
	@AttributeOverrides({
		@AttributeOverride(name="hour", column=@Column(name="time_range_end_hour")),
		@AttributeOverride(name="minutes", column=@Column(name="time_range_end_minutes"))
	})
	private Time timeRangeEnd = new Time();
	
	@ExposeToClient
	private Integer timeRangeInterval = 1;
	
	@ExposeToClient
	private TimeUnits timeRangeUnit = TimeUnits.HOURS;
	
	@Version
	private Long version;
	
	@ExposeToClient(id=true)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Defines the first time the trigger should fire.
	 * @return
	 */
	public Date getFirstExecution() {
		return firstExecution;
	}
	
	/**
	 * Defines the first time the trigger should fire.
	 * @param firstExecution
	 */
	public void setFirstExecution(Date firstExecution) {
		this.firstExecution = firstExecution;
	}
	

	/**
	 * Defines the method with which is decided if the trigger is done.
	 * 
	 * @return
	 */
	public EndTypes getEndType() {
		return endType;
	}
	
	/**
	 * Defines the method with which is decided if the trigger is done.
	 * 
	 * @param endType
	 */
	public void setEndType(EndTypes endType) {
		this.endType = endType;
	}
	

	/**
	 * Defines the last execution time.
	 * 
	 * <p>Only applies if {@link EndTypes} is set to {@link EndTypes#DATE}</p>
	 * 
	 * @return
	 * @see ComplexTriggerConfig#setEndType(EndTypes)
	 */
	public Date getLastExecution() {
		return lastExecution;
	}
	
	/**
	 * Defines the last execution time.
	 * 
	 * <p>Only applies if {@link EndTypes} is set to {@link EndTypes#DATE}</p>
	 * 
	 * @param lastExecution
	 * @see ComplexTriggerConfig#setEndType(EndTypes)
	 */
	public void setLastExecution(Date lastExecution) {
		this.lastExecution = lastExecution;
	}
	
	/**
	 * Defines the number of executions.
	 * 
	 * <p>Only applies if {@link EndTypes} is set to {@link EndTypes#COUNT}</p>
	 * 
	 * @return
	 * @see ComplexTriggerConfig#setEndType(EndTypes)
	 */
	public Integer getNumberOfExecutions() {
		return numberOfExecutions;
	}
	
	/**
	 * Defines the number of executions.
	 * 
	 * <p>Only applies if {@link EndTypes} is set to {@link EndTypes#COUNT}</p>
	 * 
	 * @param numberOfExecutions
	 * @see ComplexTriggerConfig#setEndType(EndTypes)
	 */
	public void setNumberOfExecutions(Integer numberOfExecutions) {
		this.numberOfExecutions = numberOfExecutions;
	}
	
	/**
	 * If a specific trigger time was set this method returns the set time.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#ONCE}</p>
	 * 
	 * @return
	 * @see DailyRepeatType 
	 */
	public Time getAtTime() {
		return atTime;
	}
	
	/**
	 * Sets a specific trigger time. 
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 *
	 * @param atTime
	 * @see DailyRepeatType
	 */
	public void setAtTime(Time atTime) {
		this.atTime = atTime;
	}
	
	public void setAtTime(Date atTime){
		this.atTime = new Time(atTime);
	}

	/**
	 * Returns the type of triggering per day.
	 * 
	 * @return
	 */
	public DailyRepeatType getDailyRepeatType() {
		return dailyRepeatType;
	}
	
	/**
	 * Sets the type with which triggering occurs per day.
	 * @param dailyRepeatType
	 */
	public void setDailyRepeatType(DailyRepeatType dailyRepeatType) {
		this.dailyRepeatType = dailyRepeatType;
	}
	
	/**
	 * Defines the start time of the interval within which the trigger fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * @return
	 * @see DailyRepeatType
	 */
	public Time getTimeRangeStart() {
		return timeRangeStart;
	}
	
	/**
	 * Defines the start time of the interval within which the trigger fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * @param timeRangeStart
	 * @see DailyRepeatType
	 */
	public void setTimeRangeStart(Time timeRangeStart) {
		this.timeRangeStart = timeRangeStart;
	}
	
	/**
	 * Defines the end time of the interval within which the trigger fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * @return
	 * @see DailyRepeatType
	 */
	public Time getTimeRangeEnd() {
		return timeRangeEnd;
	}
	
	/**
	 * Defines the end time of the interval within which the trigger fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * @param timeRangeEnd
	 * @see DailyRepeatType
	 */
	public void setTimeRangeEnd(Time timeRangeEnd) {
		this.timeRangeEnd = timeRangeEnd;
	}
	
	/**
	 * Defines the period with which the triggers fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * <p>
	 * The trigger fires every 5 minutes for two hours between 2pm and 4pm.
	 * </p>
	 * 
	 * @return
	 */
	public Integer getTimeRangeInterval() {
		return timeRangeInterval;
	}
	
	/**
	 * Defines the period with which the triggers fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * <p>
	 * The trigger fires every 5 minutes for two hours between 2pm and 4pm.
	 * </p>
	 * 
	 * @param timeRangeInterval
	 * @see DailyRepeatType
	 * @see #setTimeRangeUnit(TimeUnits)
	 */
	public void setTimeRangeInterval(Integer timeRangeInterval) {
		this.timeRangeInterval = timeRangeInterval;
	}
	
	/**
	 * Defines the period's unit with which the trigger fires.
	 * 
	 * 
	 * Defines the period with which the triggers fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * <p>
	 * The trigger fires every 5 minutes for two hours between 2pm and 4pm.
	 * </p>
	 * 
	 * @return
	 * @see DailyRepeatType
	 * @see #getTimeRangeInterval()
	 */
	public TimeUnits getTimeRangeUnit() {
		return timeRangeUnit;
	}
	
	/**
	 *  Defines the period's unit with which the trigger fires.
	 * 
	 * 
	 * Defines the period with which the triggers fires.
	 * 
	 * <p>Only specified if {@link DailyRepeatType} is set to {@link DailyRepeatType#BOUNDED_INTERVAL}</p>
	 * 
	 * <p>
	 * The trigger fires every 5 minutes for two hours between 2pm and 4pm.
	 * </p>
	 * 
	 * @param timeRangeUnit
	 * 
	 * @see DailyRepeatType
	 * @see #setTimeRangeInterval(Integer)
	 */
	public void setTimeRangeUnit(TimeUnits timeRangeUnit) {
		this.timeRangeUnit = timeRangeUnit;
	}
}