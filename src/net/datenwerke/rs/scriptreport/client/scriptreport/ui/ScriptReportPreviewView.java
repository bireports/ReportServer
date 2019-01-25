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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.ui;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class ScriptReportPreviewView extends AbstractReportPreviewView {

	private DwContentPanel wrapper;
	private final UtilsUIService utilsService;

	
	@Inject
	public ScriptReportPreviewView(
		ReportExecutorDao rexService, HookHandlerService hookHandler,
		UtilsUIService utilsService) {
		super(rexService, hookHandler);
		
		this.utilsService = utilsService;
		
		wrapper = DwContentPanel.newInlineInstance();
	}
	
	@Override
	protected boolean isCreateStatusBar() {
		return false;
	}

	@Override
	protected void doLoadReport(final DwModel result) {
		StringBaseModel reportExecutionResult = (net.datenwerke.gxtdto.client.model.StringBaseModel) result;
		if(null != reportExecutionResult && null != reportExecutionResult.getValue()){
			wrapper.clear();
			SimpleContainer container = new SimpleContainer();
			wrapper.setWidget(container);
			
			container.add(utilsService.asIframe((String) reportExecutionResult.getValue()));
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					wrapper.forceLayout();
				}
			});
		}
	}
	
	
	@Override
	protected void cancelExecution(String executeToken) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Request execute(ReportDto report, String executeToken, AsyncCallback<DwModel> callback) {
		return reportExecutorDao.executeAs("SCRIPT_REPORT_PREVIEW", executeToken, report, null, callback);
	}
	
	@Override
	public Widget doGetViewComponent() {
		return wrapper;
	}
	

}
