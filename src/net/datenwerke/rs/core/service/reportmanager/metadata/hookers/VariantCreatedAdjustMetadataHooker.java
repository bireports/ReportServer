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
 
 
package net.datenwerke.rs.core.service.reportmanager.metadata.hookers;

import java.util.Set;

import com.google.inject.Inject;

import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportMetadata;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantCreatorHook;
import net.datenwerke.rs.utils.entitycloner.EntityClonerService;

public class VariantCreatedAdjustMetadataHooker implements VariantCreatorHook {
	
	private final EntityClonerService entityClonerService;
	private final ReportService reportService;

	@Inject
	public VariantCreatedAdjustMetadataHooker(EntityClonerService entityClonerService, ReportService reportService) {
		this.entityClonerService = entityClonerService;
		this.reportService = reportService;
	}
	
	@Override
	public void newVariantCreated(Report referenceReport, Report adjustedReport, Report variant) {

	}

	@Override
	public void temporaryVariantCreated(Report referenceReport, Report adjustedReport, Report variant) {

		if (null == variant.getOldTransientId())
			return;
		
		Report variantReport  = reportService.getReportById(variant.getOldTransientId());
		if (null == variantReport)
			return;
		
		Set<ReportMetadata> variantReportMetadata = variantReport.getReportMetadata();
		for (ReportMetadata variantMetadata: variantReportMetadata) {
			variant.addReportMetadata(entityClonerService.cloneEntity(variantMetadata));
		}
	}

}
