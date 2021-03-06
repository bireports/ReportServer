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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums;

import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.scheduler.service.scheduler.locale.LocaliseDateHelper;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
public enum Nth {
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelFirst")
	FIRST,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelSecond")
	SECOND,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelThird")
	THIRD,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelFourth")
	FOURTH,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelLast")
	LAST;
	
	public String toString() {
		return LocaliseDateHelper.localNth(this);
	};
}
