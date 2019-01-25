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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform;

import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.BooleanProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.CustomComponentProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.DateProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.DoubleProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.DtoModelProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.DynamicListProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.IntegerProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.LongProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.SeparatorProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.StaticLabelProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.StaticListProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.StringProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.TextAsListProvider;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SimpleFormStartup {

	@Inject
	public SimpleFormStartup(
		HookHandlerService hookHandler,
		Provider<BooleanProvider> booleanProvider,
		Provider<DtoModelProvider> baseModelProvider,
		Provider<CustomComponentProvider> customComponentProvider,
		Provider<DateProvider> dateProvider,
		Provider<DynamicListProvider> dynamicListProvider,
		Provider<IntegerProvider> integerProvider,
		Provider<LongProvider> longProvider,
		Provider<DoubleProvider> doubleProvider,
		Provider<SeparatorProvider> separatorProvider,
		Provider<StaticLabelProvider> staticLabelProvider,
		Provider<StaticListProvider> staticListProvider,
		Provider<StringProvider> stringProvider,
		Provider<TextAsListProvider> textAsListProvider
		
		){
		
		/* attach hooks */
		hookHandler.attachHooker(FormFieldProviderHook.class, booleanProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, baseModelProvider, HookHandlerService.PRIORITY_LOWER);
		hookHandler.attachHooker(FormFieldProviderHook.class, customComponentProvider);
		hookHandler.attachHooker(FormFieldProviderHook.class, dateProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, dynamicListProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, integerProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, longProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, doubleProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, separatorProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, staticLabelProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, staticListProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, stringProvider, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(FormFieldProviderHook.class, textAsListProvider, HookHandlerService.PRIORITY_LOW);
	}
}
