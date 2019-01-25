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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.entities.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.decorator.FileServerFileDtoDec;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;

public class File2DtoPostProcessor implements
		Poso2DtoPostProcessor<FileServerFile, FileServerFileDto> {

	@Override
	public void dtoCreated(FileServerFile poso, FileServerFileDto dto) {
		if(null == poso.getData())
			((FileServerFileDtoDec)dto).setSize(0);
		else
			((FileServerFileDtoDec)dto).setSize(poso.getData().length);
	}

	@Override
	public void dtoInstantiated(FileServerFile poso, FileServerFileDto dto) {
		
	}

}
