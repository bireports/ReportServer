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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.dadgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.propertywidgets.ParameterView;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardDao;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.ParameterDadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.decorator.ParameterDadgetDtoDec;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class ParameterDadgetProcessor implements DadgetProcessorHook {
	
	private DashboardDao dashboardDao;

	@Inject
	public ParameterDadgetProcessor(
		DashboardDao dashboardDao
		) {
		this.dashboardDao = dashboardDao;
	}

	@Override
	public BaseIcon getIcon() {
		return BaseIcon.FILTER;
	}
	
	@Override
	public boolean isRedrawOnMove() {
		return false;
	}


	@Override
	public String getTitle() {
		return DashboardMessages.INSTANCE.parameterTitle();
	}

	@Override
	public String getDescription() {
		return DashboardMessages.INSTANCE.parameterDescription();
	}

	@Override
	public boolean consumes(DadgetDto dadget) {
		return dadget instanceof ParameterDadgetDto;
	}

	@Override
	public DadgetDto instantiateDadget() {
		return new ParameterDadgetDtoDec();
	}

	@Override
	public void draw(DadgetDto dadget, final DadgetPanel panel) {
		dashboardDao.getDashboardParameterInstances(panel.getView().getDashboard(), new AsyncCallback<Map<String,ParameterInstanceDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Map<String, ParameterInstanceDto> instanceMap) {
				final List<ParameterDefinitionDto> definitions = new ArrayList<>();
				final Set<ParameterInstanceDto> instances = new HashSet<>();
				
				for(ParameterInstanceDto inst : instanceMap.values()){
					definitions.add(inst.getDefinition());
					instances.add(inst);
				}
				
				
				TextButton ok = new DwTextButton(BaseMessages.INSTANCE.submit(), BaseIcon.CHECK);
				ok.addSelectHandler(new SelectHandler() {
					@Override
					public void onSelect(SelectEvent event) {
						dashboardDao.setDashboardParameterInstances(panel.getView().getDashboard(), instances, new AsyncCallback<List<DadgetDto>>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(List<DadgetDto> result) {
								for(DadgetDto dadget : result){
									final DadgetPanel dadgetPanel = panel.getView().getPanel(dadget);
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
				
				ParameterView pv = new ParameterView(definitions, instances);
				VerticalLayoutContainer parameterContainer = pv.getParameterContainer();
				parameterContainer.setScrollMode(ScrollMode.AUTO);
				
				ButtonBar bb = new ButtonBar();
				bb.add(new FillToolItem());
				bb.add(ok);
				parameterContainer.add(bb);
				
				panel.add(parameterContainer);

				panel.unmask();
			}
		});
		
		
		panel.setHeadingText(getTitle());
		panel.setHeaderIcon(BaseIcon.SEARCH);
		panel.addStyleName("rs-dadget-parameter");
		panel.mask();
	}

	

	@Override
	public Widget getAdminConfigDialog(DadgetDto dadget, SimpleForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayConfigDialog(DadgetDto dadget, DadgetConfigureCallback dadgetConfigureCallback) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean hasConfigDialog() {
		return false;
	}

	@Override
	public void addTools(DadgetPanel dadgetPanel) {
	}

}
