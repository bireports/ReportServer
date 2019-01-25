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
 
 
package net.datenwerke.rs.core.client.reportmanager.objectinfo;

import java.util.Date;

import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoKeyInfoProviderImpl;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;

public class FolderObjectInfo extends ObjectInfoKeyInfoProviderImpl<ReportFolderDto> {

	
	
	@Override
	public boolean consumes(Object object) {
		return object instanceof ReportFolderDto;
	}

	@Override
	protected String doGetName(ReportFolderDto folder) {
		return folder.getName();
	}

	@Override
	protected String doGetDescription(ReportFolderDto folder) {
		return folder.getDescription();
	}

	@Override
	protected String doGetType(ReportFolderDto folder) {
		return "Ordner";
	}

	@Override
	public ImageResource doGetIconSmall(ReportFolderDto object) {
		return BaseIcon.FOLDER_O.toImageResource();
	}

	@Override
	protected Date doGetLastUpdatedOn(ReportFolderDto object) {
		return object.getLastUpdated();
	}

	@Override
	protected Date doGetCreatedOn(ReportFolderDto object) {
		return object.getCreatedOn();
	}


}
