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
 
 
package net.datenwerke.rs.base.service.parameterreplacements.hookers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.datenwerke.rs.base.service.parameterreplacements.provider.LocaleInfoParameterReplacement;
import net.datenwerke.rs.base.service.parameterreplacements.provider.ReportParameterReplacement;
import net.datenwerke.rs.base.service.parameterreplacements.provider.UserParameterReplacement;
import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterSetReplacementProviderHook;
import net.datenwerke.rs.core.service.reportmanager.metadata.ReportMetadataParameterReplacement;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSetReplacementProvider;


public class ParameterSetReplacementProviderHooker implements
		ParameterSetReplacementProviderHook {

	private final UserParameterReplacement userParameterProvider;
	private final ReportParameterReplacement reportParameterProvider;
	private final ReportMetadataParameterReplacement reportMetadataParameterReplacement;
	private final LocaleInfoParameterReplacement localeInfoParameterReplacement;
	
	@Inject
	public ParameterSetReplacementProviderHooker(
		UserParameterReplacement userParameterProvider,
		ReportParameterReplacement reportParameterProvider,
		ReportMetadataParameterReplacement reportMetadataParameterReplacement,
		LocaleInfoParameterReplacement localeInfoParameterReplacement
		){
		
		this.userParameterProvider = userParameterProvider;
		this.reportParameterProvider = reportParameterProvider;
		this.reportMetadataParameterReplacement = reportMetadataParameterReplacement;
		this.localeInfoParameterReplacement = localeInfoParameterReplacement;
	}
	
	@Override
	public Collection<? extends ParameterSetReplacementProvider> getProviders() {
		Set<ParameterSetReplacementProvider> providers = new HashSet<ParameterSetReplacementProvider>();
		
		providers.add(userParameterProvider);
		providers.add(reportParameterProvider);
		providers.add(reportMetadataParameterReplacement);
		providers.add(localeInfoParameterReplacement);
		
		return providers;
	}

}
