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
 
 
package net.datenwerke.rs.base.client.reportengines.table.ui;

import java.util.Collection;

import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanelView;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.PreviewViewFactory;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 */
public class TableReportPreviewViewFactory extends PreviewViewFactory{

	/**
	 * The provider used to supply the actual instances
	 */
	private final Provider<TableReportPreviewView> jroProvider;

	@Inject
	public TableReportPreviewViewFactory(Provider<TableReportPreviewView> jroProvider) {
		this.jroProvider = jroProvider;
	}

	
	/**
	 * {@inheritDoc}
	 */
	public ReportExecutorMainPanelView newInstance(ReportDto report, Collection<? extends ReportViewConfiguration> configs) {
		TableReportPreviewView view = jroProvider.get();
		view.setReport(report);
		
		return view;
	}
	

	
	/**
	 * {@inheritDoc}
	 */
	public boolean consumes(ReportDto report) {
		return (report instanceof TableReportDto) && ! ((TableReportDto)report).isCubeFlag();
	}




}
