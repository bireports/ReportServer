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
 
 
package net.datenwerke.rs.globalconstants.service.globalconstants;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSetReplacementProviderImpl;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValueImpl;
import net.datenwerke.rs.globalconstants.service.globalconstants.entities.GlobalConstant;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class GlobalConstantParameterReplacementProvider extends
		ParameterSetReplacementProviderImpl {

	private final GlobalConstantsService globalConstantService;
	
	@Inject
	public GlobalConstantParameterReplacementProvider(
		GlobalConstantsService globalConstantService	
		){
		this.globalConstantService = globalConstantService;
	}
	
	@Override
	public Map<String, ParameterValue> provideReplacements(User user, Report report) {
		Map<String, ParameterValue> replacementMap = new HashMap<String, ParameterValue>();
		
		for(GlobalConstant constant : globalConstantService.getAllGlobalConstants())
			replacementMap.put(constant.getName(), new ParameterValueImpl(constant.getName(), constant.getValue(), String.class));
		
		return replacementMap;
	}
}
