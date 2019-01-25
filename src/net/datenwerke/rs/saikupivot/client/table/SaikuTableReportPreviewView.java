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
 
 
package net.datenwerke.rs.saikupivot.client.table;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;
import net.datenwerke.rs.saikupivot.client.SaikuPivotDao;

public class SaikuTableReportPreviewView extends AbstractReportPreviewView {

	private DwContentPanel wrapper;
	private SaikuPivotDao saikuDao;
	private boolean hasBeenExecuted;
	private HandlerRegistration loadHandler;
	private Frame iframe;
	private boolean hadExecutionError;
	
	@Inject
	public SaikuTableReportPreviewView(
			ReportExecutorDao rexService,
			SaikuPivotDao saikuDao, 
			HookHandlerService hookHandler,
			UtilsUIService utilsService) {
		super(rexService, hookHandler);
		
		this.saikuDao = saikuDao;
		
		wrapper = DwContentPanel.newInlineInstance();
	}
	
	@Override
	protected boolean isCreateStatusBar() {
		return false;
	}

	@Override
	protected void doLoadReport(DwModel reportExecutionResult) {
		wrapper.clear();
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		wrapper.setWidget(container);
		
		boolean isConfigurationProtected = report.isConfigurationProtected();

		iframe= new NamedFrame("rs-saiku-" + getExecuteReportToken());
		iframe.getElement().setAttribute("id", "rs-saiku-" + getExecuteReportToken());
		iframe.setUrl("resources/saiku/index.html?username=" + getExecuteReportToken() + "&password=none&RS_SHOW_RESET=true&RS_CONFIGURATION_PROTECTED=" + isConfigurationProtected);
		iframe.setHeight("100%");
		iframe.setWidth("100%");
		iframe.getElement().setAttribute("width", "100%");
		iframe.getElement().setAttribute("height", "100%");
		iframe.getElement().setAttribute("frameborder", "0");
		iframe.getElement().setPropertyString("scrolling", "no");
		iframe.getElement().getStyle().setProperty("border", "none");
		iframe.getElement().getStyle().setProperty("margin","0");
		
		loadHandler = iframe.addLoadHandler(new LoadHandler() {
			private int i = 0;
			@Override
			public void onLoad(LoadEvent event) {
				if(i > 0){
					loadHandler.removeHandler();
					reload();
				}
				i++;
			}
		});
		
		container.add(iframe, new VerticalLayoutData(1,1,new Margins(0)));
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				wrapper.forceLayout();
				iframe.getElement().getStyle().setProperty("border", "none");
				iframe.getElement().getStyle().setProperty("margin","0");
			}
		});
		
	}

	
	@Override
	protected void cancelExecution(String executeToken) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Request execute(final ReportDto report, String executeToken, final AsyncCallback<DwModel> callback) {
		return saikuDao.stashReport(getExecuteReportToken(), (TableReportDto) report, new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				callback.onSuccess(report);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				hadExecutionError = true;
				callback.onFailure(caught);
			}
		});
	}

	@Override
	public Widget doGetViewComponent() {
		return wrapper;
	}
	
	@Override
	public String getComponentHeader() {
		return SaikuMessages.INSTANCE.cubePreview(); 
	}

	public void makeAwareOfSelection() {
		if(report instanceof TableReportDto){
			
			if(((TableReportDto) report).getColumns().isEmpty()){
				hadExecutionError = true;
				new DwAlertMessageBox(ReportexecutorMessages.INSTANCE.failed(), ReportexecutorMessages.INSTANCE.noColumnsSelected()).show();
				if(null != container)
					container.disable();
				return;
			}
			
			boolean aggregationFound = false;
			for (ColumnDto column: ((TableReportDto) report).getColumns()) {
				if (null != column.getAggregateFunction()) {
					aggregationFound = true;
					break;
				}
			}
			if (!aggregationFound) {
				hadExecutionError = true;
				new DwAlertMessageBox(ReportexecutorMessages.INSTANCE.failed(), ReportexecutorMessages.INSTANCE.noAggregationSelected()).show();
				if(null != container)
					container.disable();
				return;
			}
		}
		
		if(!hasBeenExecuted || hadExecutionError() || hadSaikuError() )
			super.makeAwareOfSelection();
		hasBeenExecuted = true;
		report.fireObjectChangedEvent();
	}
	
	
	public boolean hadSaikuError() {
//		boolean b = "true".equals(iframe.getElement().getAttribute("data-rspivoterror"));
//		iframe.getElement().setAttribute("data-rspivoterror", "false");
//		return b;
		return false;
	}
	
	public boolean hadExecutionError() {
		boolean execError = hadExecutionError;
		hadExecutionError = false;
		return execError;
	}
}
