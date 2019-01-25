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
 
 
package net.datenwerke.rs.saiku.service.hooker;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReportVariant;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantToBeStoredHook;
import net.datenwerke.rs.saiku.service.saiku.SaikuSessionContainer;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReportVariant;

import org.saiku.olap.query.IQuery;

public class VariantStoreHooker implements VariantToBeStoredHook {

	private Provider<SaikuSessionContainer> saikuSessionContainer;

	@Inject
	public VariantStoreHooker(
			Provider<SaikuSessionContainer> saikuSessionContainer) {
		
		this.saikuSessionContainer = saikuSessionContainer;
	}

	@Override
	public void variantToBeStored(Report report, String executerToken) {
		if(report instanceof SaikuReportVariant) {
			SaikuReportVariant variant = (SaikuReportVariant) report;
			SaikuReport report2 = saikuSessionContainer.get().getReport(executerToken);
			IQuery query = saikuSessionContainer.get().getQueryForReport(report2);
			String queryXml = query.toXml();
			variant.setQueryXml(queryXml);
		} else if(report instanceof TableReport && ((TableReport)report).isCube()){
			TableReportVariant variant = (TableReportVariant) report;
			
			SaikuReport report2 = saikuSessionContainer.get().getReport(executerToken);
			if(null == report2)
				return;
			IQuery query = saikuSessionContainer.get().getQueryForReport(report2);
			if(null == query)
				return;
			
			String queryXml = query.toXml();
			variant.setCubeXml(queryXml);
		}
	}

}
