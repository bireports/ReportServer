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

import java.util.Collection;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoPostProcessor;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.ColumnReference;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;

import com.google.inject.Inject;

public class Dto2TableReportPost implements Dto2PosoPostProcessor<TableReportDto, TableReport> {

	@Inject
	public Dto2TableReportPost() {
		super();
	}

	@Override
	public void posoCreated(TableReportDto arg0, TableReport arg1) {
		
	}

	@Override
	public void posoInstantiated(TableReport arg0) {
		
	}

	@Override
	public void posoLoaded(TableReportDto arg0, TableReport arg1) {
		
	}

	@Override
	public void posoMerged(TableReportDto arg0, TableReport arg1) {

	}

	@Override
	public void posoCreatedUnmanaged(TableReportDto arg0, TableReport report) {
		/* this is a rather crude hack, but the dto generator is not capable enough */
		for(AdditionalColumnSpec spec : report.getAdditionalColumns()){
			for(Column col : report.getColumns()){
				if(col instanceof ColumnReference){
					AdditionalColumnSpec reference = ((ColumnReference) col).getReference();
					if(null != reference.getTransientId() && reference.getTransientId().equals(spec.getTransientId()))
						 ((ColumnReference) col).setReference(spec);
				}
			}
		}
		
		/* validate */
		for(Column col : report.getColumns()){
			if(col instanceof ColumnReference){
				AdditionalColumnSpec spec = ((ColumnReference) col).getReference();
				if(! containsSpec(report.getAdditionalColumns(), spec))
					throw new IllegalStateException();
			}
		}
	}

	private boolean containsSpec(Collection<AdditionalColumnSpec> toCompare, AdditionalColumnSpec spec) {
		for(AdditionalColumnSpec toComp : toCompare)
			if(toComp == spec)
				return true;
		return false;
	}

}
