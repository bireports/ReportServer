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
 
 
package net.datenwerke.rs.base.client.reportengines.table.prefilter.hooks;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterSpecDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.propertywidgets.PreFilterView.EditPreFilterCallback;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.propertywidgets.PreFilterView.InstantiatePreFilterCallback;

import com.google.gwt.resources.client.ImageResource;

public interface PreFilterConfiguratorHook extends Hook {

	String getHeadline();

	ImageResource getIcon();

	void instantiateFilter(TableReportDto report, String executeToken, InstantiatePreFilterCallback instantiatePreFilterCallback);

	boolean consumes(FilterSpecDto filter);

	void displayFilter(TableReportDto report, FilterSpecDto filter,	String executeToken, EditPreFilterCallback callback);

	void filterInstantiated(TableReportDto report, FilterSpecDto filter, String executeToken, final EditPreFilterCallback callback);

}
