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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.hookers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow.OnButtonClickHandler;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.dialog.error.DetailErrorDialog;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.propertywidgets.ParameterView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardDao;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.ParameterDadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.ReportDadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DashboardToolbarHook;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardMainComponent;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardView;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;

public class DashboardToolbarParameterHooker implements DashboardToolbarHook {

	private DwTextButton filterBtn;
	
	private final DashboardDao dashboardDao;

	private DashboardDto dashboard;
	private DashboardView dashboardView;

	private DwToolBar toolbar;

	private DashboardMainComponent component;

	@Inject
	public DashboardToolbarParameterHooker(
		DashboardDao dashboardDao	
		){
		this.dashboardDao = dashboardDao;
	}
	
	@Override
	public void addLeft(DwToolBar toolbar, DashboardMainComponent dashboardMainComponent) {
		
	}

	@Override
	public void addRight(DwToolBar toolbar, DashboardMainComponent dashboardMainComponent) {
		this.toolbar = toolbar;
		this.component = dashboardMainComponent;

		filterBtn = new DwTextButton(DashboardMessages.INSTANCE.editParameterBtnLabel(), BaseIcon.FILTER);
		filterBtn.hide();
		filterBtn.disable();
		
		toolbar.add(filterBtn);
		toolbar.add(new SeparatorToolItem());
		
		filterBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				showParameterSelection();
			}
		});
	}
	
	protected void showParameterSelection() {
		if(null == dashboard)
			return;
		
		final DwWindow dialog = new DwWindow();
		dialog.setSize(640, 480);
		dialog.setModal(true);
		dialog.setHeaderIcon(BaseIcon.FILTER);
		
		
		dashboardView.mask(BaseMessages.INSTANCE.loadingMsg());
		dashboardDao.getDashboardParameterInstances(dashboard, new AsyncCallback<Map<String,ParameterInstanceDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				dashboardView.unmask();
				new DetailErrorDialog(caught).show();
			}

			@Override
			public void onSuccess(Map<String, ParameterInstanceDto> instanceMap) {
				dashboardView.unmask();
				
				final List<ParameterDefinitionDto> definitions = new ArrayList<>();
				final Set<ParameterInstanceDto> instances = new HashSet<>();
				
				for(ParameterInstanceDto inst : instanceMap.values()){
					definitions.add(inst.getDefinition());
					instances.add(inst);
				}
				
				ParameterView pv = new ParameterView(definitions, instances);
				dialog.add(pv.getViewComponent());
				dialog.setHeadingText(pv.getComponentHeader());
				
				dialog.addCancelButton();
				dialog.addSubmitButton(new OnButtonClickHandler() {
					
					@Override
					public void onClick() {
						dialog.mask();
						dashboardDao.setDashboardParameterInstances(dashboard, instances, new AsyncCallback<List<DadgetDto>>() {

							@Override
							public void onFailure(Throwable caught) {
								dialog.hide();
							}

							@Override
							public void onSuccess(List<DadgetDto> result) {
								dialog.hide();
								for(DadgetDto dadget : result){
									final DadgetPanel dadgetPanel = dashboardView.getPanel(dadget);
									dadgetPanel.updateDadget(dadget);
									Scheduler.get().scheduleDeferred(new ScheduledCommand() {
										@Override
										public void execute() {
											dadgetPanel.getView().dadgetConfigured(dadgetPanel);
										}
									});
								}
							}
						});
					}
				});
				dialog.show();
			}
		});
	}

	@Override
	public void dashboardDisplayed(DashboardDto dashboard, DashboardView dashboardView) {
		this.dashboard = dashboard;
		this.dashboardView = dashboardView;
		
		boolean display = false;
		
		for(DadgetDto dadget : dashboard.getDadgets()){
			if(dadget instanceof ReportDadgetDto){
				ReportDto report = ((ReportDadgetDto) dadget).getReport();
				if(null != report && ! report.getParameterInstances().isEmpty())
					display = true;
				TsDiskReportReferenceDto reference = ((ReportDadgetDto) dadget).getReportReference();
				if(null != reference && null != reference.getReport() && ! reference.getReport().getParameterInstances().isEmpty())
					display = true;
			}
			if(dadget instanceof ParameterDadgetDto){
				display = false;
				break;
			}
		}

		if(display){
			filterBtn.show();
			filterBtn.enable();
		} else {
			filterBtn.hide();
			filterBtn.disable();
		}
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				component.forceLayout();
			}
		});
	}
	
	@Override
	public void dashboardChanged(DashboardDto dashboard, DashboardView view) {
		dashboardDisplayed(dashboard, view);
	}

}
