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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers;

import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.ScheduleAsFileUiModule;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ExecutedReportFileReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskGeneralReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.GeneralReferenceHandlerHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.ui.TsDiskMainComponent;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

public class HandleExecutedReportFileHooker implements
		GeneralReferenceHandlerHook {

	private final UtilsUIService utilsService;
	
	@Inject
	public HandleExecutedReportFileHooker(
		UtilsUIService utilsService
		) {
		
		/* store objects */
		this.utilsService = utilsService;
	}

	@Override
	public boolean consumes(TsDiskGeneralReferenceDto item) {	
		return item instanceof ExecutedReportFileReferenceDto;
	}

	@Override
	public void handle(TsDiskGeneralReferenceDto item, TsDiskMainComponent mainComponent) {
		String id = String.valueOf(item.getId());
		String url = GWT.getModuleBaseURL() +  ScheduleAsFileUiModule.EXPORT_SERVLET + "?fileId=" + id + "&download=false"; //$NON-NLS-1$
		
		utilsService.redirectInPopup(url);
	}

}
