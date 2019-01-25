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
 
 
package net.datenwerke.rs.license.service;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import net.datenwerke.rs.license.service.genrights.GenRightsLicenseModule;

public class LicenseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LicenseService.class).to(LicenseServiceImpl.class).in(Singleton.class);
		
		bind(LicenseStartup.class).asEagerSingleton();
		
		/* rights */
		install(new GenRightsLicenseModule());
	}

}
