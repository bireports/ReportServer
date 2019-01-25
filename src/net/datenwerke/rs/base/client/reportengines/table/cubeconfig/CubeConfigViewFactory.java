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
 
 
package net.datenwerke.rs.base.client.reportengines.table.cubeconfig;

import java.util.Collection;

import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewFactory;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.security.client.security.dto.ExecuteDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class CubeConfigViewFactory implements ReportViewFactory{
	
	private final Provider<CubeConfigView> cubeConfigViewProvider;
	
	@Inject
	public CubeConfigViewFactory(
			Provider<CubeConfigView> cubeConfigViewProvider
		) {
		this.cubeConfigViewProvider = cubeConfigViewProvider;
	}
	
	public CubeConfigView newInstance(ReportDto report, Collection<? extends ReportViewConfiguration> configs) {
		CubeConfigView fw = cubeConfigViewProvider.get();
		fw.setReport((TableReportDto) report);
		return fw;
	}

	public boolean consumes(ReportDto report) {
		return 
			report instanceof TableReportDto &&
			report.hasAccessRight(ExecuteDto.class) && ((TableReportDto)report).isCube();
	}
	
	@Override
	public String getViewId() {
		return CubeConfigView.VIEW_ID;
	}
}
