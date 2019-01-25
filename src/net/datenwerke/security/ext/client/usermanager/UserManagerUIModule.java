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
 
 
package net.datenwerke.security.ext.client.usermanager;


import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.security.ext.client.usermanager.provider.BasicTreeProvider;
import net.datenwerke.security.ext.client.usermanager.provider.FolderTreeProvider;
import net.datenwerke.security.ext.client.usermanager.provider.FullTreeProvider;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerAdminViewTree;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeBasic;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeBasicSingleton;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeFolders;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeFull;
import net.datenwerke.security.ext.client.usermanager.utils.UserIconMapping;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class UserManagerUIModule extends AbstractGinModule {

	public static final String USERMANAGER_FAV_HISTORY_TOKEN = "usermgr";
	
	public static final String ADMIN_TREE_MENU_NAME = "usermanager:admin:tree:menu";
	
	@Override
	protected void configure() {
		/* bind service */
		bind(UserManagerUIService.class).to(UserManagerUIServiceImpl.class).in(Singleton.class);
		
		/* bind trees */
		bind(UITree.class).annotatedWith(UserManagerTreeBasic.class).toProvider(BasicTreeProvider.class);
		bind(UITree.class).annotatedWith(UserManagerTreeBasicSingleton.class).toProvider(BasicTreeProvider.class).in(Singleton.class);
		bind(UITree.class).annotatedWith(UserManagerTreeFull.class).toProvider(FullTreeProvider.class);
		bind(UITree.class).annotatedWith(UserManagerTreeFolders.class).toProvider(FolderTreeProvider.class);
		bind(UITree.class).annotatedWith(UserManagerAdminViewTree.class).toProvider(FullTreeProvider.class).in(Singleton.class);
		
		/* bind service */
		bind(UserManagerUIStartup.class).asEagerSingleton();

		/* request static injection */
		requestStaticInjection(UserIconMapping.class);
	}
	
}
