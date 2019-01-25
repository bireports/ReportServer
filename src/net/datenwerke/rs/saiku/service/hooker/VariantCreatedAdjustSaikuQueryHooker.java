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

import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReportVariant;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantCreatorHook;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReportVariant;

public class VariantCreatedAdjustSaikuQueryHooker implements VariantCreatorHook {

	@Override
	public void newVariantCreated(Report referenceReport, Report adjustedReport, Report variant) {

	}

	@Override
	public void temporaryVariantCreated(Report referenceReport, Report adjustedReport, Report variant) {
		if(variant instanceof SaikuReportVariant) {
			if(adjustedReport instanceof SaikuReportVariant && ((SaikuReportVariant) adjustedReport).getQueryXml() != null) {
				((SaikuReportVariant) variant).setQueryXml(((SaikuReportVariant) adjustedReport).getQueryXml());
			}else if(referenceReport instanceof SaikuReportVariant){
				((SaikuReportVariant) variant).setQueryXml(((SaikuReportVariant) referenceReport).getQueryXml());
			}
		} else if(variant instanceof TableReport && ((TableReport)variant).isCube()){
			if(adjustedReport instanceof TableReport && ((TableReport) adjustedReport).getCubeXml() != null) {
				((TableReportVariant) variant).setCubeXml(((TableReport) adjustedReport).getCubeXml());
			}else if(referenceReport instanceof TableReport){
				((TableReportVariant) variant).setCubeXml(((TableReport) referenceReport).getCubeXml());
			}
		}
	}

}
