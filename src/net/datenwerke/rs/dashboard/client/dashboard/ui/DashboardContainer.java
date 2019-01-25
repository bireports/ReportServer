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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.ui;

import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardView.EditSuccessCallback;

public interface DashboardContainer {

	enum ConfigType{
		CONFIG,
		MISC
	}
	
	void reload(DashboardDto dashboard);
	
	void edited(DashboardDto dashboard);

	void remove(DashboardDto dashboard, DadgetDto dadget);

	void resizeDadget(DashboardDto dashboard, DadgetDto dadget, int height);

	void addDadget(DashboardDto dashboard, DadgetDto dadget);

	void dadgetConfigured(DashboardDto dashboard, DadgetDto dadget, ConfigType type, EditSuccessCallback callback);


}
