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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.core.service.reportmanager.helpers.ReportIconHelper;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ExecutedReportFileReferenceDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.decorator.ExecutedReportFileReferenceDtoDec;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.ExecutedReportFileReference;

import com.google.inject.Inject;

public class ExecuteReportFileReference2DtoPost implements
		Poso2DtoPostProcessor<ExecutedReportFileReference, ExecutedReportFileReferenceDto> {

	private final ReportIconHelper iconHelper;

	@Inject
	public ExecuteReportFileReference2DtoPost(ReportIconHelper iconHelper) {
		this.iconHelper = iconHelper;
	}

	@Override
	public void dtoCreated(ExecutedReportFileReference ref,
			ExecutedReportFileReferenceDto refDto) {
		if(null != ref.getOutputFormat()){
			((ExecutedReportFileReferenceDtoDec)refDto).setIconStr(iconHelper.getSmallIconForOutputFormat(ref.getOutputFormat()));
			((ExecutedReportFileReferenceDtoDec)refDto).setTypeStr(ref.getOutputFormat().toLowerCase());
		}
		
	}

	@Override
	public void dtoInstantiated(ExecutedReportFileReference arg0,
			ExecutedReportFileReferenceDto arg1) {
		
	}

}
