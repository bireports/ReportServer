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
 
 
package net.datenwerke.scheduler.client.scheduler.dto.config.complex;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.scheduler.client.scheduler.locale.SchedulerMessages;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public enum NthDto {

	FIRST {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelFirst();
		}
	},
	SECOND {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelSecond();
		}
	},
	THIRD {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelThird();
		}
	},
	FOURTH {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelFourth();
		}
	},
	LAST {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelLast();
		}
	}

}
