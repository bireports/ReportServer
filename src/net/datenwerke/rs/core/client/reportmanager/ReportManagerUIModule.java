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
 
 
package net.datenwerke.rs.core.client.reportmanager;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.rs.core.client.reportmanager.helper.reportselector.ReportSelectionField;
import net.datenwerke.rs.core.client.reportmanager.provider.FolderAndReportTreeProvider;
import net.datenwerke.rs.core.client.reportmanager.provider.FolderTreeProvider;
import net.datenwerke.rs.core.client.reportmanager.provider.FullTreeProvider;
import net.datenwerke.rs.core.client.reportmanager.provider.annotations.ReportManagerAdminViewTree;
import net.datenwerke.rs.core.client.reportmanager.provider.annotations.ReportManagerTreeFolders;
import net.datenwerke.rs.core.client.reportmanager.provider.annotations.ReportManagerTreeFoldersAndReports;

/**
 * 
 *
 */
public class ReportManagerUIModule extends AbstractGinModule {
	
	public static final String REPORTMANAGER_FAV_HISTORY_TOKEN = "reportmgr";
	public static final String ADMIN_TREE_MENU_NAME = "reportmanager:admin:tree:menu";
	
	@Override
	protected void configure() {
		/* bind trees */
		bind(UITree.class).annotatedWith(ReportManagerTreeFolders.class).toProvider(FolderTreeProvider.class);
		bind(UITree.class).annotatedWith(ReportManagerTreeFoldersAndReports.class).toProvider(FolderAndReportTreeProvider.class);
		bind(UITree.class).annotatedWith(ReportManagerAdminViewTree.class).toProvider(FullTreeProvider.class).in(Singleton.class);
		
		/* bind service */
		bind(ReportManagerUIService.class).to(ReportManagerUIServiceImpl.class).in(Singleton.class);
		
		/* bind startup class */
		bind(ReportManagerUIStartup.class).asEagerSingleton();
		
		requestStaticInjection(ReportSelectionField.class);
	}



}
