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
 
 
package net.datenwerke.rs.core.service.reportmanager.hookers;

import java.util.Enumeration;

import javax.inject.Inject;

import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.hooks.ConfigureReportViaRequestAndLocationImpl;

import com.google.inject.Injector;

public class ConfigureBaseReportViaRequestHooker extends ConfigureReportViaRequestAndLocationImpl {
	
	private Injector injector;

	@Inject
	public ConfigureBaseReportViaRequestHooker(Injector injector) {
		this.injector = injector;
	}

	@Override
	public void adjustReport(Report report, ParameterProvider req) {
		Enumeration<String> parameterNames = req.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String name = parameterNames.nextElement();
			
			if(name.startsWith("p_") && name.length() > 2){ //$NON-NLS-1$
				String parameterName = name.substring(2);
				for(ParameterInstance instance : report.getParameterInstances()){
					injector.injectMembers(instance);
					
					ParameterDefinition definition = instance.getDefinition();
					if(parameterName.equals(definition.getKey()) && definition.isEditable()){
						instance.parseStringValue(req.getParameter(name));
						break;
					}
				}
			}
		}
		
	}

}
