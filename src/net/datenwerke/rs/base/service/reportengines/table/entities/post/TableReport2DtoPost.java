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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.base.client.reportengines.table.dto.AdditionalColumnSpecDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnReferenceDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;

public class TableReport2DtoPost implements Poso2DtoPostProcessor<TableReport, TableReportDto> {

	@Override
	public void dtoCreated(TableReport report, TableReportDto dto) {
		for(ColumnDto col : dto.getColumns()){
			if(col instanceof ColumnReferenceDto){
				for(AdditionalColumnSpecDto spec: dto.getAdditionalColumns()){
					if(spec.equals(((ColumnReferenceDto)col).getReference())){
						((ColumnReferenceDto)col).setReference(spec);
						break;
					}
				}
			}
		}
	}

	@Override
	public void dtoInstantiated(TableReport arg0, TableReportDto arg1) {
		
	}

}
