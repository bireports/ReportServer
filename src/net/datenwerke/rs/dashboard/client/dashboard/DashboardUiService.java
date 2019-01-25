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
 
 
package net.datenwerke.rs.dashboard.client.dashboard;

import com.google.inject.ImplementedBy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.widget.core.client.button.IconButton;

import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;

@ImplementedBy(DashboardUiServiceImpl.class)
public interface DashboardUiService {

	IconButton addParameterToolButtonTo(DadgetPanel dadgetPanel, DadgetProcessorHook dadgetProcessor);

	void showHideParameterToolButton(DadgetPanel panel, ReportDto report);

	ListStore<DashboardDto> getAllDashboardsStore();

	ListLoader<ListLoadConfig, ListLoadResult<DashboardDto>> getAllDashboardsLoader();

	ListStore<DashboardDto> getDashboardStore();

	ListLoader<ListLoadConfig, ListLoadResult<DashboardDto>> getDashboardsLoader();

}
