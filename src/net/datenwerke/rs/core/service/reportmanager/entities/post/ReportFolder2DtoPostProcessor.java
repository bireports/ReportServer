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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;

public class ReportFolder2DtoPostProcessor implements
		Poso2DtoPostProcessor<ReportFolder, ReportFolderDto> {

	@Override
	public void dtoCreated(ReportFolder arg0, ReportFolderDto arg1) {
		arg1.setIsReportRoot(null == arg0.getParent());
	}

	@Override
	public void dtoInstantiated(ReportFolder arg0, ReportFolderDto arg1) {
		// TODO Auto-generated method stub
		
	}

}
