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
 
 
package net.datenwerke.rs.base.client.reportengines.table.prefilter.propertywidgets;

import java.util.Collection;

import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewFactory;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class PreFilterViewFactory implements ReportViewFactory {

	private final Provider<PreFilterView> filterViewProvider;
	
	
	@Inject
	public PreFilterViewFactory(
		Provider<PreFilterView> filterViewProvider
		) {
		
		this.filterViewProvider = filterViewProvider;
	}
	
	public PreFilterView newInstance(ReportDto report, Collection<? extends ReportViewConfiguration> configs) {
		PreFilterView fw = filterViewProvider.get();
		fw.setReport((TableReportDto) report);
		return fw;
	}
	@Override
	public boolean consumes(ReportDto report) {
		return report instanceof TableReportDto;
	}
	
	@Override
	public String getViewId() {
		return PreFilterView.VIEW_ID;
	}

}
