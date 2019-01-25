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
 
 
package net.datenwerke.rs.saiku.client.saiku.ui;

import java.util.HashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.saiku.client.saiku.SaikuDao;
import net.datenwerke.rs.saiku.client.saiku.SaikuUiService;
import net.datenwerke.rs.saiku.client.saiku.dto.SaikuReportDto;

public class SaikuReportPreviewView extends AbstractReportPreviewView {

	private DwContentPanel wrapper;
	private SaikuDao saikuDao;
	private SaikuUiService saikuService;
	
	@Inject
	public SaikuReportPreviewView(
			ReportExecutorDao rexService,
			SaikuDao saikuDao, 
			HookHandlerService hookHandler,
			SaikuUiService saikuService) {
		super(rexService, hookHandler);
		
		this.saikuDao = saikuDao;
		this.saikuService = saikuService;
		
		wrapper = DwContentPanel.newInlineInstance();
	}
	
	@Override
	protected boolean isCreateStatusBar() {
		return false;
	}

	@Override
	protected void doLoadReport(DwModel reportExecutionResult) {
		saikuService.getSettings(new AsyncCallback<HashMap<String,String>>() {
			@Override
			public void onSuccess(HashMap<String, String> settings) {
				wrapper.clear();
				
				VerticalLayoutContainer container = new VerticalLayoutContainer();
				wrapper.setWidget(container);
				
				String addProperties = "";
				if(null != settings){
					for(String key : settings.keySet()){
						String value = settings.get(key);
						
						addProperties += "&" + URL.encode(key) + "=" + URL.encode(value);
					}
				}
				
				boolean isConfigurationProtected = report.isConfigurationProtected();
				
				final NamedFrame iframe= new NamedFrame("rs-saiku-" + getExecuteReportToken());
				iframe.getElement().setAttribute("id", "rs-saiku-" + getExecuteReportToken());
				iframe.setUrl("resources/saiku/index.html?username=" + getExecuteReportToken() + "&password=none&RS_SHOW_RESET=true&RS_CONFIGURATION_PROTECTED=" + isConfigurationProtected + addProperties);
				iframe.setHeight("100%");
				iframe.setWidth("100%");
				iframe.getElement().setAttribute("width", "100%");
				iframe.getElement().setAttribute("height", "100%");
				iframe.getElement().setAttribute("frameborder", "0");
				iframe.getElement().setPropertyString("scrolling", "no");
				iframe.getElement().getStyle().setProperty("border", "none");
				iframe.getElement().getStyle().setProperty("margin","0");
				
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
			public void onFailure(Throwable caught) {
			}
		});
	}

	
	@Override
	protected void cancelExecution(String executeToken) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Request execute(final ReportDto report, String executeToken, final AsyncCallback<DwModel> callback) {
		return saikuDao.stashReport(getExecuteReportToken(), (SaikuReportDto) report, new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				callback.onSuccess(report);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});
	}

	@Override
	public Widget doGetViewComponent() {
		return wrapper;
	}


}
