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
 
 
package net.datenwerke.rs.core.service.reportmanager.metadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.VariableMapper;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportMetadata;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSetReplacementProviderImpl;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValueImpl;
import net.datenwerke.security.service.usermanager.entities.User;

public class ReportMetadataParameterReplacement extends ParameterSetReplacementProviderImpl{
	
	
	private static final String METADATA_REPLACEMENT_FOR_JUEL = "_RS_METADATA";
	private static final String METADATA_PRAEFIX = "_RS_METADATA_";
	private static final String METADATA_PRAEFIX_SUPER = "_RS_METADATA_SUPER_";
	
	@Override
	public void extendJuel(User user, Report report, ExpressionFactory factory, ELContext context) {
		if(null == report || null == report.getReportMetadata())
			return;
		
		VariableMapper vm = context.getVariableMapper();

		HashMap<String, ReportMetadataForJuel> set = new HashMap<String, ReportMetadataForJuel>();
		Set<ReportMetadata> reportMetadata = report.getReportMetadata();
		if(null != reportMetadata){
			for(ReportMetadata rm : reportMetadata){
				set.put(rm.getName(), ReportMetadataForJuel.fromReportMetadata(rm));
			}
		}

		vm.setVariable(METADATA_REPLACEMENT_FOR_JUEL, factory.createValueExpression(set, HashMap.class)); //$NON-NLS-1$
	}
	
	@Override
	public Map<String, ParameterValue> provideReplacements(User user, Report report) {
		Map<String, ParameterValue> replacementMap = new HashMap<String, ParameterValue>();
		
		if(report instanceof ReportVariant){
			addEntriesToMap(((ReportVariant) report).getBaseReport(), replacementMap, METADATA_PRAEFIX_SUPER);
			addEntriesToMap(((ReportVariant) report).getBaseReport(), replacementMap, METADATA_PRAEFIX);
		}

		addEntriesToMap(report, replacementMap, METADATA_PRAEFIX);
		
		return replacementMap;
	}

	private void addEntriesToMap(Report report, Map<String, ParameterValue> replacementMap, String praefix){
		if(null == report || null == report.getReportMetadata())
			return;
		
		Set<ReportMetadata> reportMetadata = report.getReportMetadata();
		if(null != reportMetadata){
			for(ReportMetadata rmd : reportMetadata){
				replacementMap.put(praefix + rmd.getName(), new ParameterValueImpl(praefix + rmd.getName(), rmd.getValue(), String.class));
			}
		}
	}
	
}
