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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.ui.helper;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.DtoModelProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCBaseModel;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCDashboard;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardUiService;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.pa.DashboardDtoPA;

public class DashboardProvider extends DtoModelProvider {

	private static DashboardDtoPA dashboardPa = GWT.create(DashboardDtoPA.class);

	private final DashboardUiService dashboardService;

	@Inject
	public DashboardProvider(DashboardUiService dashboardService) {

		/* store objects */
		this.dashboardService = dashboardService;
	}

	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return type.equals(DashboardDto.class);
	}

	@Override
	public Widget createFormField() {
		final Map<ValueProvider<DashboardDto, String>, String> displayProperties = new LinkedHashMap<ValueProvider<DashboardDto, String>, String>();
		displayProperties.put(dashboardPa.name(), BaseMessages.INSTANCE.name());
		displayProperties.put(new ValueProvider<DashboardDto, String>() {

			@Override
			public void setValue(DashboardDto object, String value) {
			}

			@Override
			public String getValue(DashboardDto object) {
				return String.valueOf(object.getId());
			}

			@Override
			public String getPath() {
				return "";
			}
		}, BaseMessages.INSTANCE.id());

		final SFFCDashboard dashboardConfig = getDashboardConfig();

		configs = new SimpleFormFieldConfiguration[] { new SFFCBaseModel<DashboardDto>() {
			@Override
			public ListStore<DashboardDto> getAllItemsStore() {
				if (dashboardConfig.isLoadAll())
					return DashboardProvider.this.dashboardService.getAllDashboardsStore();
				return DashboardProvider.this.dashboardService.getDashboardStore();
			}

			@Override
			public Map<ValueProvider<DashboardDto, String>, String> getDisplayProperties() {
				return displayProperties;
			}

			@Override
			public boolean isMultiSelect() {
				return dashboardConfig.isMulti();
			}

		} };

		return super.createFormField();
	}

	private SFFCDashboard getDashboardConfig() {
		for (SimpleFormFieldConfiguration config : configs)
			if (config instanceof SFFCDashboard)
				return (SFFCDashboard) config;
		return new SFFCDashboard() {
			@Override
			public boolean isMulti() {
				return false;
			}

			@Override
			public boolean isLoadAll() {
				return false;
			}
		};
	}
}
