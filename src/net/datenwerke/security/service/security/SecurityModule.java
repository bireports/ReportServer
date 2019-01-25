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
 
 
package net.datenwerke.security.service.security;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

import net.datenwerke.rs.utils.guice.GuiceMatchers;
import net.datenwerke.security.service.genrights.security.GenRightsSecurityModule;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.annotation.UpdateOwner;
import net.datenwerke.security.service.security.aop.SecurityCheckInterceptor;
import net.datenwerke.security.service.security.aop.UpdateOwnerInformationInterceptor;

/**
 * 
 *
 */
public class SecurityModule extends AbstractModule {

	@Override
	protected void configure() {
		/* bind services */
		bind(SecurityService.class).to(SecurityServiceImpl.class).in(Singleton.class);
		
		/* bind security interceptor */
		SecurityCheckInterceptor securityCheckedInterceptor = new SecurityCheckInterceptor();
		requestInjection(securityCheckedInterceptor);
		bindInterceptor(Matchers.any(),Matchers.annotatedWith(SecurityChecked.class), securityCheckedInterceptor);
		bindInterceptor(Matchers.annotatedWith(SecurityChecked.class), Matchers.not(Matchers.annotatedWith(SecurityChecked.class)).and(GuiceMatchers.publicMethod()), securityCheckedInterceptor);
		
		/* bind update owner interceptor */
		UpdateOwnerInformationInterceptor updateOwnerInterceptor = new UpdateOwnerInformationInterceptor();
		requestInjection(updateOwnerInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(UpdateOwner.class), updateOwnerInterceptor);
		
		/* startup */
		bind(SecurityStartup.class).asEagerSingleton();
		
		/* submodules */
		install(new GenRightsSecurityModule());
	}

}
