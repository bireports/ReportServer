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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex",
	createDecorator=true
)
@Embeddable
public class Time implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4572079169255426690L;
	
	@ExposeToClient
	private Integer hour = 0;
	
	@ExposeToClient
	private Integer minutes = 0;

	public Time(){
		
	}
	
	public Time(int hour, int minutes) {
	    setHour(hour);
	    setMinutes(minutes);
	}

	public Time(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		setHour(cal.get(Calendar.HOUR_OF_DAY));
	    setMinutes(cal.get(Calendar.MINUTE));
	}

	
	public int getHour() {
		return null == hour ? 0 : hour;
	}

	public void setHour(int hour) {
		if(hour < 0 || hour > 23)
			throw new IllegalArgumentException("Hours have to fall into [0-23]"); //$NON-NLS-1$
		this.hour = hour;
	}

	public int getMinutes() {
		return null == minutes ? 0 : minutes;
	}

	public void setMinutes(int minutes) {
		if(minutes < 0 || minutes > 59)
			throw new IllegalArgumentException("Minutes have to fall into [0-59]"); //$NON-NLS-1$
		this.minutes = minutes;
	}
	
	public Date getDate(){
		/* 
		 * Do not use new Date() and setXY(), because the
		 * milliseconds (for which no setter exists, will not
		 * neccesarily be zero.
		 */
		Date d = new Date(2000 - 1900,01,01,hour,minutes,0);
		return d;
	}
	
	@Override
	public String toString() {
		return hour + ":" + minutes; //$NON-NLS-1$
	}
}
