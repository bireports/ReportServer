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
 
 
package net.datenwerke.rs.globalconstants.service.globalconstants.hookers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterSetReplacementProviderHook;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSetReplacementProvider;
import net.datenwerke.rs.globalconstants.service.globalconstants.GlobalConstantParameterReplacementProvider;

public class ParameterSetReplacementProviderHooker implements
		ParameterSetReplacementProviderHook {

	private final GlobalConstantParameterReplacementProvider globalConstantProvider;
	
	@Inject
	public ParameterSetReplacementProviderHooker(
		GlobalConstantParameterReplacementProvider globalConstantProvider
		){
		
		this.globalConstantProvider = globalConstantProvider;
	}
	
	@Override
	public Collection<? extends ParameterSetReplacementProvider> getProviders() {
		Set<ParameterSetReplacementProvider> providers = new HashSet<ParameterSetReplacementProvider>();
		
		providers.add(globalConstantProvider);
		
		return providers;
	}

}
