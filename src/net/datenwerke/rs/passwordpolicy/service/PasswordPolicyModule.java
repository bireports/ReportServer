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
 
 
package net.datenwerke.rs.passwordpolicy.service;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.passwordpolicy.service.lostpassword.LostPasswordModule;
import net.datenwerke.rs.passwordpolicy.service.passwordgenerator.DefaultPasswordGenerator;

public class PasswordPolicyModule extends AbstractReportServerModule {
	
	public static final String CONFIG_FILE = "security/passwordpolicy.cf";


	@Override
	protected void configure() {
		bind(PasswordPolicy.class).to(BsiPasswordPolicy.class);
		bind(BsiPasswordPolicyService.class).to(BsiPasswordPolicyServiceImpl.class).in(Singleton.class);
		bind(PasswordPolicyStartup.class).asEagerSingleton();

		install(new LostPasswordModule());
	}


	@Provides
	public PasswordGenerator providePasswordGeneratorProvider(BsiPasswordPolicyService passwordPolicyService){
		PasswordPolicy pp = passwordPolicyService.getPolicy();
		if(null == pp || null == pp.getPasswordComplexitySpecification())
			return new DefaultPasswordGenerator();

		return pp.getPasswordComplexitySpecification().getPasswordGenerator();
	}


}
