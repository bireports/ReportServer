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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.dispatcher;

import javax.inject.Inject;

import net.datenwerke.gf.client.dispatcher.Dispatchable;
import net.datenwerke.gf.client.dispatcher.hooks.DispatcherTakeOverHookImpl;
import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardDao;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardTreeLoaderDao;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardContainerDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardContainer;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardMainComponent;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardView;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardView.EditSuccessCallback;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.container.Viewport;

public class DashboardInlineDispatcher extends DispatcherTakeOverHookImpl {

	public static final String LOCATION = "inlinedashboard";
	
	public static final String TYPE_SINGLE = "single";
	
	public static final String DASHBOARD_NR = "nr";
	private static final String DASHBOARD_ID = "id";

	private final Provider<DashboardMainComponent> mainComponentProvider;
	private final Provider<DashboardView> dashboardViewProvider;
	private final DashboardDao dashboardDao;
	private final DashboardTreeLoaderDao dashboardTreeLoaderDao;
	
	@Inject
	public DashboardInlineDispatcher(
		Provider<DashboardMainComponent> mainComponentProvider,
		Provider<DashboardView> dashboardViewProvider,
		DashboardDao dashboardDao,
		DashboardTreeLoaderDao dashboardTreeLoaderDao
		){
		this.mainComponentProvider = mainComponentProvider;
		this.dashboardViewProvider = dashboardViewProvider;
		this.dashboardDao = dashboardDao;
		this.dashboardTreeLoaderDao = dashboardTreeLoaderDao;
	}
	
	@Override
	public Dispatchable getDispatcheable() {
		final HistoryLocation hLocation = getHistoryLocation();
		
		final Viewport vp = new Viewport();
		
		final DwContentPanel panel = DwContentPanel.newInlineInstance();
		vp.add(panel);
	
		String type = hLocation.getParameterValue("type");
		if(TYPE_SINGLE.equals(type) && hLocation.hasParameter(DASHBOARD_NR))
			loadSingleDashboard(hLocation, panel);
		else if(TYPE_SINGLE.equals(type) && hLocation.hasParameter(DASHBOARD_ID))
			loadDashboardById(hLocation, panel);
		else {
			DashboardMainComponent comp = mainComponentProvider.get();
			comp.hideToolbar();
			panel.add(comp);
			comp.selected();
		}
		
		
		return new Dispatchable() {
			
			@Override
			public Widget getWidget() {
				return vp;
			}
		};
	}



	protected void loadDashboardById(HistoryLocation hLocation,
			final DwContentPanel panel) {
		Long id = Long.parseLong(hLocation.getParameterValue(DASHBOARD_ID));
		dashboardTreeLoaderDao.loadNodeById(id, new RsAsyncCallback<AbstractNodeDto>(){
			@Override
			public void onSuccess(AbstractNodeDto result) {
				if(! (result instanceof DashboardNodeDto))
					panel.mask(BaseMessages.INSTANCE.error());
				
				DashboardDto dashboard = ((DashboardNodeDto)result).getDashboard();
				DashboardView dashboardView = dashboardViewProvider.get();
				panel.add(dashboardView);
				dashboardView.init(new DashboardContainer() {
					@Override
					public void resizeDadget(DashboardDto dashboard, DadgetDto dadget,
							int offsetHeight) {
					}
					
					@Override
					public void remove(DashboardDto dashboard, DadgetDto dadget) {
					}
					
					@Override
					public void reload(DashboardDto dashboard) {
					}
					
					@Override
					public void dadgetConfigured(DashboardDto dashboard, DadgetDto dadget,
							EditSuccessCallback callback) {
					}
					
					@Override
					public void addDadget(DashboardDto dashboard, DadgetDto dadget) {
					}
					@Override
					public void edited(DashboardDto dashboard) {
					}
				}, dashboard, true);
			}
		});
	}

	protected void loadSingleDashboard(final HistoryLocation hLocation,
			final DwContentPanel panel) {
		dashboardDao.getDashboardForUser(new RsAsyncCallback<DashboardContainerDto>(){
			@Override
			public void onSuccess(DashboardContainerDto result) {
				int nr = 0;
				try{
					nr = Integer.parseInt(hLocation.getParameterValue(DASHBOARD_NR)) - 1;
				}catch(Exception e){}

				
				DashboardDto dashboardDto = result.getDashboards().get(0);
				if(result.getDashboards().size() > nr)
					dashboardDto = result.getDashboards().get(nr);
				
				DashboardView dashboardView = dashboardViewProvider.get();
				panel.add(dashboardView);
				dashboardView.init(new DashboardContainer() {
					@Override
					public void resizeDadget(DashboardDto dashboard, DadgetDto dadget,
							int offsetHeight) {
					}
					
					@Override
					public void remove(DashboardDto dashboard, DadgetDto dadget) {
					}
					
					@Override
					public void reload(DashboardDto dashboard) {
					}
					
					@Override
					public void dadgetConfigured(DashboardDto dashboard, DadgetDto dadget,
							EditSuccessCallback callback) {
					}
					
					@Override
					public void addDadget(DashboardDto dashboard, DadgetDto dadget) {
					}
					
					@Override
					public void edited(DashboardDto dashboard) {
					}
				}, dashboardDto, true);
				
			}
		});
	}

	@Override
	public String getLocation() {
		return LOCATION;
	}

}
