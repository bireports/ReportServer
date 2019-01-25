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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums;

import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.scheduler.service.scheduler.locale.LocaliseDateHelper;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
public enum TimeUnits {
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelHours")
	HOURS,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelMinutes")
	MINUTES;
	
	public String toString() {
		return LocaliseDateHelper.localTimeUnit(this);
	};
	
	public int getCalendarUnit(){
		switch(this){
			case HOURS:		return 11; //Calendar.HOUR_OF_DAY;
			case MINUTES:	return 12; //Calendar.MINUTE;
			default: throw new IllegalArgumentException();
		}
	}
}
