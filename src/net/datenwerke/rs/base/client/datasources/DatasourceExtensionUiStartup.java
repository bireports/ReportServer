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
 
 
package net.datenwerke.rs.base.client.datasources;

import java.util.ArrayList;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.dialog.error.DetailErrorDialog;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.client.datasources.hooker.ArgumentDatasourceConnectorConfigHooker;
import net.datenwerke.rs.base.client.datasources.hooker.CsvDatasourceConfigProviderHooker;
import net.datenwerke.rs.base.client.datasources.hooker.DatabaseDatasourceConfigProviderHooker;
import net.datenwerke.rs.base.client.datasources.hooker.TextDatasourceConnectorConfigHooker;
import net.datenwerke.rs.base.client.datasources.hooker.UrlDatasourceConnectorConfigHooker;
import net.datenwerke.rs.base.client.datasources.hooks.DatasourceConnectorConfiguratorHook;
import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;

public class DatasourceExtensionUiStartup {

	@Inject
	public DatasourceExtensionUiStartup(
		HookHandlerService hookHandler,
		
		Provider<DatabaseDatasourceConfigProviderHooker> databaseConfigHooker,
		Provider<CsvDatasourceConfigProviderHooker> csvDatasourceConfigHooker,
		
		Provider<TextDatasourceConnectorConfigHooker> textDatasourceConfigHooker,
		Provider<UrlDatasourceConnectorConfigHooker> urlDatasourceConfigHooker,
		Provider<ArgumentDatasourceConnectorConfigHooker> argumentDatasourceConfigHooker,
		
		BaseDatasourceDao baseDatasourceDao,
		final BaseDatasourceUiService datasourcService
		){
		
		/* datasources */
		hookHandler.attachHooker(DatasourceDefinitionConfigProviderHook.class, databaseConfigHooker, 10);
		hookHandler.attachHooker(DatasourceDefinitionConfigProviderHook.class, csvDatasourceConfigHooker, 20);
		
		/* connectors */
		hookHandler.attachHooker(DatasourceConnectorConfiguratorHook.class, textDatasourceConfigHooker, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(DatasourceConnectorConfiguratorHook.class, urlDatasourceConfigHooker, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(DatasourceConnectorConfiguratorHook.class, argumentDatasourceConfigHooker, HookHandlerService.PRIORITY_LOW);
		
		/* call server to get dbhelper */
		baseDatasourceDao.getDBHelperList(new RsAsyncCallback<ArrayList<DatabaseHelperDto>>() {
			@Override
			public void onSuccess(ArrayList<DatabaseHelperDto> result) {
				datasourcService.setDatabaseHelpers(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				new DetailErrorDialog(caught).show();
			}
		});
	}
}
