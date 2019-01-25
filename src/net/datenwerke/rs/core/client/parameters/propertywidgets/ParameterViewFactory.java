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
 
 
package net.datenwerke.rs.core.client.parameters.propertywidgets;

import java.util.Collection;

import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanelView;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewFactory;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

public class ParameterViewFactory implements ReportViewFactory{

	public ReportExecutorMainPanelView newInstance(ReportDto report, Collection<? extends ReportViewConfiguration> configs) {
		return new ParameterView(report);
	}

	public boolean consumes(ReportDto report) {
		if (null != report.getParameterDefinitions() && report.getParameterDefinitions().size() > 0 && !report.isConfigurationProtected()){
			for(ParameterDefinitionDto def :report.getParameterDefinitions())
				if(! def.isHidden())
					return true;
		}
		return false;
	}

	@Override
	public String getViewId() {
		return ParameterView.VIEW_ID;
	}
}
