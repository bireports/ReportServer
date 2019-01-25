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
public enum Months {
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelJanuary")
	JANUARY,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelFebruary")
	FEBRUARY,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelMarch")
	MARCH,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelApril")
	APRIL,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelMay")
	MAY,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelJune")
	JUNE,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelJuly")
	JULY,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelAugust")
	AUGUST,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelSeptember")
	SEPTEMBER,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelOctober")
	OCTOBER,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelNovember")
	NOVEMBER,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelDecember")
	DECEMBER;
	
	public String toString() {
		return LocaliseDateHelper.localMonth(this);
	};
	
	public static Months fromCalendarMonth(int month){
			switch(month){
				case 0: return JANUARY;
				case 1: return FEBRUARY;
				case 2: return MARCH;
				case 3: return APRIL;
				case 4: return MAY;
				case 5: return JUNE;
				case 6: return JULY;
				case 7: return AUGUST;
				case 8: return SEPTEMBER;
				case 9: return OCTOBER;
				case 10: return NOVEMBER;
				case 11: return DECEMBER;
				default: throw new IllegalArgumentException("Not a month: " + month); //$NON-NLS-1$
			}
		}

}
