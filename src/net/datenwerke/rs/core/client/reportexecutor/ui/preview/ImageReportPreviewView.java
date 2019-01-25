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
 
 
package net.datenwerke.rs.core.client.reportexecutor.ui.preview;

import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.model.SuccessIndicatorBaseModel;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;

public class ImageReportPreviewView extends AbstractReportPreviewView {

	private FlowLayoutContainer wrapper;

	@Inject
	public ImageReportPreviewView (
			ReportExecutorDao rexService, 
			HookHandlerService hookHandler) {
		super(rexService, hookHandler);
		
		wrapper = new FlowLayoutContainer();
		wrapper.setScrollMode(ScrollMode.AUTO);
	}
	
	@Override
	protected boolean isCreateStatusBar() {
		return false;
	}
	
	@Override
	public Widget doGetViewComponent() {
		return wrapper;
	}

	@Override
	protected void doLoadReport(DwModel reportExecutionResult) {
		if(((SuccessIndicatorBaseModel)reportExecutionResult).isSuccess()){
			wrapper.clear();
			final Image image = new Image(GWT.getModuleBaseURL() + "jasperpreviewprovider?rnd="+Random.nextInt()); //$NON-NLS-1$ //$NON-NLS-2$
			wrapper.add(image);
		}
	}

	@Override
	protected void cancelExecution(String executeToken) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Request execute(ReportDto report, String executeToken, AsyncCallback<DwModel> callback) {
		return reportExecutorDao.storePNGInSession(executeToken, report, callback);
	}

}
