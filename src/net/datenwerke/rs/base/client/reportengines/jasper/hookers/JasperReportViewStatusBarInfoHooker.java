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
 
 
package net.datenwerke.rs.base.client.reportengines.jasper.hookers;

import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.model.SuccessIndicatorBaseModel;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportPreviewViewStatusbarHook;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * 
 *
 */
public class JasperReportViewStatusBarInfoHooker implements ReportPreviewViewStatusbarHook {

	private Status countPages;
	
	@Inject
	public JasperReportViewStatusBarInfoHooker(
		){
	}
	
	@Override
	public void reportPreviewViewStatusbarHook_addLeft(final AbstractReportPreviewView reportPreviewView, ToolBar toolbar, final ReportDto report) {
		if(! (report instanceof JasperReportDto))
			return;
		
		countPages = new Status();
		countPages.setBorders(false);
		toolbar.add(countPages);
	}

	@Override
	public void reportPreviewViewStatusbarHook_addRight(AbstractReportPreviewView reportPreviewView, ToolBar toolbar, ReportDto report) {
	}
	
	@Override
	public void reportPreviewViewStatusbarHook_reportToBeReloaded(ReportDto report, AbstractReportPreviewView abstractReportPreviewView) {
	}

	@Override
	public void reportPreviewViewStatusbarHook_reportUpdated(ReportDto report, final AbstractReportPreviewView abstractReportPreviewView, boolean configChanged) {
	}
	
	@Override
	public void reportPreviewViewStatusbarHook_reportLoaded(ReportDto report,
			AsyncCallback<DwModel> modalAsyncCallback, DwModel result, boolean configChanged) {
		if(! (report instanceof JasperReportDto))
			return;
		
		if(result instanceof SuccessIndicatorBaseModel){
			SuccessIndicatorBaseModel suc = (SuccessIndicatorBaseModel) result;
			if(suc.getData().size() > 0){
				countPages.setText(ReportexecutorMessages.INSTANCE.pages() + suc.getData().get(0).getValue());
			}
		}
	}

	@Override
	public void reportPreviewViewStatusbarHook_cancel(ReportDto report,
			AbstractReportPreviewView abstractReportPreviewView) {
		// TODO Auto-generated method stub
		
	}
}
