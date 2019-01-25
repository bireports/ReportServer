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
 
 
package net.datenwerke.rs.base.service.parameterreplacements.provider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSetReplacementProviderImpl;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValueImpl;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.annotations.DefaultLocale;
import net.datenwerke.security.service.usermanager.UserManagerModule;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;

public class LocaleInfoParameterReplacement extends ParameterSetReplacementProviderImpl {

	public static final String RS_LOCALE_SYS = "_RS_LOCALE_SYS";
	public static final String RS_LOCALE = "_RS_LOCALE";
	public static final String RS_LOCALE2 = "_RS_LOCALE2";
	
	private LocalizationServiceImpl localizationService;
	private Provider<String> defaultLocale;
	private UserPropertiesService userPropertiesService;

	@Inject
	public LocaleInfoParameterReplacement(
			@DefaultLocale Provider<String> defaultLocale, 
			LocalizationServiceImpl localizationService,
			UserPropertiesService userPropertiesService) {
				this.defaultLocale = defaultLocale;
				this.localizationService = localizationService;
				this.userPropertiesService = userPropertiesService;
	}
	
	@Override
	public Map<String, ParameterValue> provideReplacements(User user, Report report) {
		Map<String, ParameterValue> reps = new HashMap<String, ParameterValue>();
		
		if(null != user){
			String locale = userPropertiesService.getPropertyValue(user, UserManagerModule.USER_PROPERTY_USER_LOCALE);
			reps.put("_RS_LOCALE_USER", new ParameterValueImpl("_RS_LOCALE_USER", null == locale ? localizationService.getLocale().toLanguageTag() : locale, String.class));
		}
		reps.put(RS_LOCALE_SYS, new ParameterValueImpl(RS_LOCALE_SYS, defaultLocale.get(), String.class));
		reps.put(RS_LOCALE, new ParameterValueImpl(RS_LOCALE, localizationService.getLocale().toLanguageTag(), String.class));
		
		return reps;
	}
}
