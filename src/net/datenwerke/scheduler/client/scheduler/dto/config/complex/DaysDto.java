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
 
 
package net.datenwerke.scheduler.client.scheduler.dto.config.complex;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.scheduler.client.scheduler.locale.SchedulerMessages;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public enum DaysDto {

	MONDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelMonday();
		}
	},
	TUESDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelTuesday();
		}
	},
	WEDNESDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelWednesday();
		}
	},
	THURSDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelThursday();
		}
	},
	FRIDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelFriday();
		}
	},
	SATURDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelSaturday();
		}
	},
	SUNDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelSunday();
		}
	},
	DAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelDay();
		}
	},
	WORKINGDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelWorkingDay();
		}
	},
	WEEKENDDAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelWeekendDay();
		}
	}

}
