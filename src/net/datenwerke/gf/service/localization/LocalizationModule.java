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
 
 
package net.datenwerke.gf.service.localization;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;

import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.annotations.AvailableLocales;
import net.datenwerke.rs.utils.localization.annotations.CurrentLocale;
import net.datenwerke.rs.utils.localization.annotations.DefaultLocale;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;

/**
 * The localization module offers localization similar to the default GWT i18n
 * implementation, but server side.
 * 
 * <h1>Description</h1>
 * <p>
 * The localization module offers localization similar to the default GWT i18n
 * implementation, but server side.
 * </p>
 * 
 * <h1>Content</h1>
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link LocalizationServiceImpl}</li>
 * </ul>
 * </p>
 * 
 * <h2>Static Injections</h2>
 * <p>
 * <ul>
 * <li>{@link LocalizationServiceImpl}</li>
 * </ul>
 * </p>
 * 
 * <h1>Dependencies</h1>
 * 
 * <h2>Entities</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.security.service.usermanager.entities.User}</li>
 * </ul>
 * </p>
 * 
 * <h2>3rd-Party</h2>
 * <p>
 * <ul>
 * <li><a href="http://code.google.com/p/google-guice/">Google Guice</a></li>
 * <li><a href="http://cglib.sourceforge.net/">CGLib</a></li>
 * <li><a href="http://code.google.com/intl/de-DE/webtoolkit/">Google GWT</a></li>
 * </ul>
 * </p>
 * 
 * <h2>Others</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.security.service.authenticator.authenticator.AuthenticatorService}</li>
 * <li>{@link net.datenwerke.rs.ApplicationPropertiesService.service.reportserver.ReportServerPropertiesService}</li>
 * </ul>
 * </p>
 */
public class LocalizationModule extends AbstractModule {

	public static final String CONFIG_FILE = "main/localization.cf";
	public static final String USER_LOCAL_PROPERTY_NAME = "localization:locale";
	
	
	
	@Override
	protected void configure() {
		bind(LocalizationStartup.class).asEagerSingleton();
		
		requestStaticInjection(LocalizationServiceImpl.class);
	}
	
	@Provides @Inject @DefaultLocale
	public String provideDefaultLocale(ConfigService configService){
		return configService.getConfigFailsafe(CONFIG_FILE).getString("localization.default", "en");
	}
	
	@Provides @Inject @AvailableLocales
	public Collection<String> provideLocales(ConfigService configService, RemoteMessageService remoteMsgService){
		String fallback = "en,de";

		/* Check remote message service */
		Collection<String> availableLanguages = remoteMsgService.getAvailableLanguages();
		if(null != availableLanguages && !availableLanguages.isEmpty()){
			return availableLanguages;
		}
		
		/* check config */
		String cfgVal = configService.getConfigFailsafe(CONFIG_FILE).getString("localization.locales", fallback);
		
		return Arrays.asList(cfgVal.split(","));
	}

	@Provides @Inject @CurrentLocale
	public String provideDefaultLocale(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<UserPropertiesService> userPropertiesServiceProvider){
		try{
			Locale locale = LocalizationServiceImpl.getUserLocal();
			if(null != locale)
				return locale.toLanguageTag();
		} catch(Exception e){}
		
		return userPropertiesServiceProvider.get().getPropertyValue(authenticatorServiceProvider.get().getCurrentUser(), USER_LOCAL_PROPERTY_NAME);
	}
}
