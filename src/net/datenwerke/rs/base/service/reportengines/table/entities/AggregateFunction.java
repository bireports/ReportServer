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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities;

import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.reportengines.table.locale.EnumMessages;

@GenerateDto(
		dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto"
)
public enum AggregateFunction {
	
	@EnumLabel(msg=EnumMessages.class,key="avg")
	AVG,
	
	@EnumLabel(msg=EnumMessages.class,key="count")
	COUNT,
	
	@EnumLabel(msg=EnumMessages.class,key="max")
	MAX,
	
	@EnumLabel(msg=EnumMessages.class,key="min")
	MIN,
	
	@EnumLabel(msg=EnumMessages.class,key="sum")
	SUM,
	
	@EnumLabel(msg=EnumMessages.class,key="variance")
	VARIANCE, 
	
	@EnumLabel(msg=EnumMessages.class,key="countDistinct")
	COUNT_DISTINCT;
}
