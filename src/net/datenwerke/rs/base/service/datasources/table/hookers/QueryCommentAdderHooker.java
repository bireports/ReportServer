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
 
 
package net.datenwerke.rs.base.service.datasources.table.hookers;

import net.datenwerke.rs.base.service.datasources.table.impl.TableDBDataSource;
import net.datenwerke.rs.base.service.datasources.table.impl.hooks.TableDbDatasourceOpenedHook;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class QueryCommentAdderHooker implements TableDbDatasourceOpenedHook {

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	
	@Inject
	public QueryCommentAdderHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider) {
		this.authenticatorServiceProvider = authenticatorServiceProvider;
	}

	@Override
	public void datasourceOpenend(TableDBDataSource tableDBDataSource,
			String executorToken) {
		try{
			tableDBDataSource.addQueryComment("token: " + String.valueOf(executorToken));
			
			User currentUser = authenticatorServiceProvider.get().getCurrentUser();
			if(null != currentUser)
				tableDBDataSource.addQueryComment("currentuser: " + String.valueOf(currentUser.getId()));
		}catch(Exception e){
		}
	}

}
