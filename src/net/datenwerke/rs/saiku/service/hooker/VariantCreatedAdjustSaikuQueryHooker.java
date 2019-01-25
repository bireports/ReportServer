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
 
 
package net.datenwerke.rs.saiku.service.hooker;

import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantCreatorHook;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;

public class VariantCreatedAdjustSaikuQueryHooker implements VariantCreatorHook {

	@Override
	public void newVariantCreated(Report referenceReport, Report adjustedReport, Report variant) {

	}

	@Override
	public void temporaryVariantCreated(Report referenceReport, Report adjustedReport, Report variant) {

		if (referenceReport instanceof SaikuReport && adjustedReport instanceof SaikuReport
				&& variant instanceof SaikuReport) {
			if (((SaikuReport) adjustedReport).getQueryXml() != null) {
				((SaikuReport) variant).setQueryXml(((SaikuReport) adjustedReport).getQueryXml());
				((SaikuReport) variant).setHideParents(((SaikuReport) adjustedReport).isHideParents());
			} else {
				((SaikuReport) variant).setQueryXml(((SaikuReport) referenceReport).getQueryXml());
				((SaikuReport) variant).setHideParents(((SaikuReport) referenceReport).isHideParents());
			}
		} else if (referenceReport instanceof TableReport && adjustedReport instanceof TableReport
				&& variant instanceof TableReport) {

			if (((TableReport) variant).isCube()) {

				if (((TableReport) adjustedReport).getCubeXml() != null) {
					((TableReport) variant).setCubeXml(((TableReport) adjustedReport).getCubeXml());
					((TableReport) variant).setHideParents(((TableReport) adjustedReport).isHideParents());
				} else {
					((TableReport) variant).setCubeXml(((TableReport) referenceReport).getCubeXml());
					((TableReport) variant).setHideParents(((TableReport) referenceReport).isHideParents());
				}

			}

		}

	}

}
