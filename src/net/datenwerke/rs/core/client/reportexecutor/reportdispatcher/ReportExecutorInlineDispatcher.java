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
 
 
package net.datenwerke.rs.core.client.reportexecutor.reportdispatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.datenwerke.gf.client.dispatcher.Dispatchable;
import net.datenwerke.gf.client.dispatcher.hooks.DispatcherTakeOverHookImpl;
import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.reportexecutor.ExecuteReportConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexecutor.events.ExecutorEventHandler;
import net.datenwerke.rs.core.client.reportexecutor.events.ReportExecutorEvent;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanelView;
import net.datenwerke.rs.core.client.reportexecutor.ui.aware.MainPanelAwareView;
import net.datenwerke.rs.core.client.reportexecutor.ui.aware.SelectionAwareView;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.PreviewViewFactory;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.NoVariantStorerConfig;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.VariantStorerConfig;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

public class ReportExecutorInlineDispatcher extends DispatcherTakeOverHookImpl {

	public static final String LOCATION = "inlinereport";
	
	public static final String PARAM_KEY = "key";
	public static final String PARAM_ID = "id";
	public static final String PARAM_UUID = "uuid";
	
	public static final String TYPE_FULL = "full";
	public static final String TYPE_PREVIEW = "preview";
	
	public static final String DEFAULT_VIEW = "defaultview";
	public static final String VIEWS = "views";

	private final ReportExecutorDao reportExecutorDao;
	private final ReportExecutorUIService reportExecutorService;
	
	@Inject
	public ReportExecutorInlineDispatcher(
		ReportExecutorDao reportExecutorDao,
		ReportExecutorUIService reportExecutorService
		){
		this.reportExecutorDao = reportExecutorDao;
		this.reportExecutorService = reportExecutorService;
	}
	
	@Override
	protected boolean checkParameters(HistoryLocation hLocation) {
		return hLocation.hasParameter(PARAM_ID) || hLocation.hasParameter(PARAM_KEY) || hLocation.hasParameter(PARAM_UUID);
	}
	
	@Override
	public Dispatchable getDispatcheable() {
		final HistoryLocation hLocation = getHistoryLocation();
		
		final Viewport vp = new Viewport();
		
		
		
		reportExecutorDao.loadReportForExecutionFrom(hLocation, new RsAsyncCallback<ReportDto>(){
			public void onSuccess(ReportDto result) {
				String type = hLocation.getParameterValue("type");
				if(TYPE_PREVIEW.equals(type))
					displayPreview(result, vp);
				else
					displayFull(hLocation, result, vp);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
		
		return new Dispatchable() {
			
			@Override
			public Widget getWidget() {
				return vp;
			}
		};
	}


	protected void displayPreview(ReportDto result, Viewport vp) {
		PreviewViewFactory factory = reportExecutorService.getPreviewReportComponent(result);
		ReportExecutorMainPanelView view = factory.newInstance(result, Collections.EMPTY_SET);
		
		if(view instanceof AbstractReportPreviewView)
			((AbstractReportPreviewView)view).setDelayModalWindowOnExecution(10000);
		
		Widget component = view.getViewComponent();
		
		view.setExecuteReportToken(reportExecutorService.createExecuteReportToken(result));
		if(view instanceof MainPanelAwareView)
			((MainPanelAwareView)view).makeAwareOfMainPanel(vp);
		if(view instanceof SelectionAwareView)
			((SelectionAwareView)view).makeAwareOfSelection();
		
		vp.add(component);
		vp.forceLayout();
	}

	protected void displayFull(HistoryLocation hLocation, ReportDto result, Viewport vp) {
		final String defaultView = hLocation.hasParameter(DEFAULT_VIEW) ? hLocation.getParameterValue(DEFAULT_VIEW) : AbstractReportPreviewView.VIEW_ID; 
		
		final Set<String> views = new HashSet<String>();
		if(hLocation.hasParameter(VIEWS)){
			String viewParam = hLocation.getParameterValue(VIEWS);
			for(String view : viewParam.split("\\|")){
				views.add(view.trim().toLowerCase());
			}
		}
		
		Component executeReportComponent = reportExecutorService.getExecuteReportComponent(
				result,
				new ExecutorEventHandler() {
					@Override
					public void handleEvent(ReportExecutorEvent event) {
					}
				}, new ExecuteReportConfiguration() {
					@Override
					public VariantStorerConfig getVariantStorerConfig() {
						return new NoVariantStorerConfig();
					}

					@Override
					public String getViewId() {
						return defaultView;
					}

					@Override
					public boolean handleError(Throwable t) {
						return false;
					}
					
					@Override
					public boolean accepctView(String viewId) {
						return views.isEmpty() || null == viewId || views.contains(viewId.toLowerCase());
					}
				}, new InlineReportView()
				);
		if(executeReportComponent instanceof ContentPanel)
			((ContentPanel) executeReportComponent).setHeaderVisible(false);

		vp.add(executeReportComponent);
		vp.forceLayout();
	}

	@Override
	public String getLocation() {
		return LOCATION;
	}

}
