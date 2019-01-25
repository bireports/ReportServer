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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.reports.supervisor;

import java.util.Collection;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisor;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public class Dto2ReportSupervisor implements Dto2PosoSupervisor<ReportDto, Report> {

	@Override
	public void enclosedObjectsRemovedFromCollection(ReportDto dto,
			Report poso, Collection<?> objectCollection, String fieldname) {
		if(null == objectCollection)
			return;
		
		for(Object o : objectCollection){
			if(o instanceof ParameterInstance || o instanceof ParameterDefinition<?>)
				throw new IllegalArgumentException("Definition or instances should not be removed through merges");
		}
	}

	@Override
	public void referencedObjectRemoved(ReportDto dto, Report poso,
			Object removedProperty, Object replacement, String fieldname) {
		
	}

	@Override
	public void enclosedObjectRemoved(ReportDto dto, Report poso,
			Object removedProperty, Object replacement, String fieldname) {
		if(removedProperty instanceof DatasourceContainer)
			throw new IllegalArgumentException("Datasourcecontainers should not be allowed to be reomved");
	}

}
