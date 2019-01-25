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
 
 
package net.datenwerke.rs.core.service.reportmanager.parameters;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;

public class ParameterSetFactory {
	
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<Injector> injectorProvider;
	
	@Inject
	public ParameterSetFactory(
		Provider<AuthenticatorService> authenticatorServiceProvider,
		Provider<Injector> injectorProvider){
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.injectorProvider = injectorProvider;
	}
	
	public ParameterSet create(User user, Report report){
		return doPrepare(new ParameterSet(authenticatorServiceProvider.get(), user, report));
	}
	public ParameterSet create(Report report){
		return doPrepare(new ParameterSet(authenticatorServiceProvider.get(), report));
	}
	
	public ParameterSet create(){
		return doPrepare(new ParameterSet(authenticatorServiceProvider.get()));
	}
	
	public ParameterSet safeCreate(){
		User user = null;
		try{
			AuthenticatorService authenticatorService = authenticatorServiceProvider.get();
			user = authenticatorService.getCurrentUser();
		}catch(Exception e){}
		return doPrepare(new ParameterSet(user));
	}
	
	public ParameterSet safeCreate(User user) {
		return doPrepare(new ParameterSet(user));
	}
	
	private ParameterSet doPrepare(ParameterSet parameterSet) {
		injectorProvider.get().injectMembers(parameterSet);
		return parameterSet;
	}

}
