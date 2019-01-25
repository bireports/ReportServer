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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.objectinfo;

import java.util.Date;

import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoKeyInfoProviderImpl;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ExecutedReportFileReferenceDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.decorator.ExecutedReportFileReferenceDtoDec;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.locale.ScheduleAsFileMessages;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.core.client.util.IconHelper;

public class ExecutedReportObjectInfoHooker extends ObjectInfoKeyInfoProviderImpl<ExecutedReportFileReferenceDto> {
	
	@Override
	public boolean consumes(Object object) {
		return object instanceof ExecutedReportFileReferenceDto;
	}

	@Override
	protected String doGetName(ExecutedReportFileReferenceDto node) {
		return node.getName();
	}

	@Override
	protected String doGetDescription(ExecutedReportFileReferenceDto node) {
		return node.getDescription();
	}

	@Override
	protected String doGetType(ExecutedReportFileReferenceDto node) {
		return ScheduleAsFileMessages.INSTANCE.executedReportNodeType();
	}

	@Override
	protected Date doGetLastUpdatedOn(ExecutedReportFileReferenceDto node) {
		return ((ExecutedReportFileReferenceDtoDec)node).getLastUpdated();
	}

	@Override
	protected Date doGetCreatedOn(ExecutedReportFileReferenceDto node) {
		return ((ExecutedReportFileReferenceDtoDec)node).getCreatedOn();
	}

	@Override
	protected ImageResource doGetIconSmall(ExecutedReportFileReferenceDto node) {
		return node.toIcon().toImageResource();
	}


}
