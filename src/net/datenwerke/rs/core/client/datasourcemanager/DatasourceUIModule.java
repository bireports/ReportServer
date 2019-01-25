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
 
 
package net.datenwerke.rs.core.client.datasourcemanager;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionFieldFactory;
import net.datenwerke.rs.core.client.datasourcemanager.provider.BasicTreeProvider;
import net.datenwerke.rs.core.client.datasourcemanager.provider.FolderTreeProvider;
import net.datenwerke.rs.core.client.datasourcemanager.provider.FullTreeProvider;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceManagerAdminViewTree;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeBasic;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeFolders;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeFull;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

/**
 * 
 *
 */
public class DatasourceUIModule extends AbstractGinModule {

	public static final String DATASOURCE_FAV_HISTORY_TOKEN = "datasourcemgr";
	
	public static final String PROPERTY_CONTAINER = "Datasource_PropertyContainer";
	public static final String PROPERTY_DEFAULT_DATASOURCE = "datasource:defaultDatasource";

	public static final String ADMIN_TREE_MENU_NAME = "datasource:admin:tree:menu";
	
	@Override
	protected void configure() {
		/* bind trees */
		bind(UITree.class).annotatedWith(DatasourceTreeBasic.class).toProvider(BasicTreeProvider.class);
		bind(UITree.class).annotatedWith(DatasourceTreeFull.class).toProvider(FullTreeProvider.class);
		bind(UITree.class).annotatedWith(DatasourceTreeFolders.class).toProvider(FolderTreeProvider.class);
		bind(UITree.class).annotatedWith(DatasourceManagerAdminViewTree.class).toProvider(FullTreeProvider.class).in(Singleton.class);
		
		/* bind service */
		bind(DatasourceUIService.class).to(DatasourceUIServiceImpl.class).in(Singleton.class);
		
		install(new GinFactoryModuleBuilder().build(DatasourceSelectionFieldFactory.class));
		
		/* bind startup */
		bind(DatasourceUIStartup.class).asEagerSingleton();
	}

}
