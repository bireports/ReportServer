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
 
 
package net.datenwerke.gf.service.authenticator;

import java.util.LinkedHashSet;
import java.util.Set;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesService;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.authenticator.AuthenticatorServiceImpl;
import net.datenwerke.security.service.authenticator.CurrentUser;
import net.datenwerke.security.service.authenticator.ReportServerPAM;
import net.datenwerke.security.service.authenticator.RequestUserCache;
import net.datenwerke.security.service.authenticator.RequestUserCacheImpl;
import net.datenwerke.security.service.authenticator.hooks.PAMHook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.ServletScopes;

/**
 *
 */
public class AuthenticatorModule extends AbstractModule {

	private static final String AUTHENTICATORS_PROPERTY_NAME = "rs.authenticator.pams";
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	@Override
	protected void configure() {
		bind(CurrentUser.class).in(ServletScopes.SESSION);
		bind(AuthenticatorService.class).to(AuthenticatorServiceImpl.class);
		bind(RequestUserCache.class).to(RequestUserCacheImpl.class).in(ServletScopes.REQUEST);
	}

	@Inject
	@Provides
	public Set<ReportServerPAM> providePAMs(ApplicationPropertiesService propertiesService, HookHandlerService hookHandlerService, Injector injector){
		String authenticatorsString = propertiesService.getString(AUTHENTICATORS_PROPERTY_NAME);
		
		LinkedHashSet<ReportServerPAM> pams = new LinkedHashSet<ReportServerPAM>();
		
		for(PAMHook ph : hookHandlerService.getHookers(PAMHook.class)){
			ph.beforeStaticPamConfig(pams);
		}
		
		for(String className : authenticatorsString.split(":")){
			try {
				if(!className.trim().isEmpty()){
					Class<?> pamClass = Class.forName(className);
					pams.add((ReportServerPAM) injector.getInstance(pamClass));
				}
			} catch (ClassNotFoundException e) {
				logger.warn( "Failed loading pam", e);
			}
		}
		
		for(PAMHook ph : hookHandlerService.getHookers(PAMHook.class)){
			ph.afterStaticPamConfig(pams);
		}

		return pams;
	}
}
