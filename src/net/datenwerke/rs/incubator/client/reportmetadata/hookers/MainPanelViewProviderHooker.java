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
 
 
package net.datenwerke.rs.incubator.client.reportmetadata.hookers;

import java.util.Arrays;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.incubator.client.reportmetadata.ui.ReportMetadataView;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class MainPanelViewProviderHooker implements MainPanelViewProviderHook {

	private final Provider<ReportMetadataView> propertiesViewProvider;
	
	@Inject
	public MainPanelViewProviderHooker(
		Provider<ReportMetadataView> propertiesViewProvider
		){

		/* store objects */
		this.propertiesViewProvider = propertiesViewProvider;
	}
	
	public List<MainPanelView> mainPanelViewProviderHook_getView(AbstractNodeDto node) {
		if(node instanceof ReportDto)
			return getViewForReport();

		return null;
	}

	private List<MainPanelView> getViewForReport() {
		return Arrays.asList(new MainPanelView[]{propertiesViewProvider.get()});
	}

}
