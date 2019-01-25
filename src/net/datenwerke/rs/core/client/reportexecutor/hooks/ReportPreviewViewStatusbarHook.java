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
 
 
package net.datenwerke.rs.core.client.reportexecutor.hooks;

import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public interface ReportPreviewViewStatusbarHook extends Hook {

	public void reportPreviewViewStatusbarHook_addLeft(AbstractReportPreviewView reportPreviewView, ToolBar toolbar, ReportDto report);
	
	public void reportPreviewViewStatusbarHook_addRight(AbstractReportPreviewView reportPreviewView, ToolBar toolbar, ReportDto report);

	public void reportPreviewViewStatusbarHook_reportUpdated(ReportDto report, AbstractReportPreviewView abstractReportPreviewView, boolean configChanged);

	public void reportPreviewViewStatusbarHook_reportToBeReloaded(ReportDto report, AbstractReportPreviewView abstractReportPreviewView);

	public void reportPreviewViewStatusbarHook_reportLoaded(ReportDto report,
			AsyncCallback<DwModel> AsyncCallback, DwModel result, boolean configChanged);
	
	public void reportPreviewViewStatusbarHook_cancel(ReportDto report, AbstractReportPreviewView abstractReportPreviewView);
}
