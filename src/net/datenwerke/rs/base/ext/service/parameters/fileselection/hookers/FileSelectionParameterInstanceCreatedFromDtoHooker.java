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
 
 
package net.datenwerke.rs.base.ext.service.parameters.fileselection.hookers;

import javax.inject.Inject;

import net.datenwerke.rs.base.ext.client.parameters.fileselection.dto.FileSelectionParameterInstanceDto;
import net.datenwerke.rs.base.ext.client.parameters.fileselection.dto.decorator.FileSelectionParameterInstanceDtoDec;
import net.datenwerke.rs.base.ext.service.parameters.fileselection.FileSelectionParameterDtoHelper;
import net.datenwerke.rs.base.ext.service.parameters.fileselection.FileSelectionParameterInstance;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterInstanceCreatedFromDtoHook;

public class FileSelectionParameterInstanceCreatedFromDtoHooker implements ParameterInstanceCreatedFromDtoHook {

	private FileSelectionParameterDtoHelper fileSelectionParameterDtoHelper;

	@Inject
	public FileSelectionParameterInstanceCreatedFromDtoHooker(FileSelectionParameterDtoHelper fileSelectionParameterDtoHelper) {
		this.fileSelectionParameterDtoHelper = fileSelectionParameterDtoHelper;
	}
	
	@Override
	public boolean consumes(ParameterInstanceDto parameterInstanceDto) {
		return (parameterInstanceDto instanceof FileSelectionParameterInstanceDto);
	}

	@Override
	public void posoCreated(ParameterInstanceDto parameterInstanceDto, ParameterInstance parameterInstance) {
		
		fileSelectionParameterDtoHelper.fixInstanceFromDto((FileSelectionParameterInstance)parameterInstance, (FileSelectionParameterInstanceDtoDec)parameterInstanceDto);
	}

}
