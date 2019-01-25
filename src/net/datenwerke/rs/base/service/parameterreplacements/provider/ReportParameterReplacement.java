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
 
 
package net.datenwerke.rs.base.service.parameterreplacements.provider;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.VariableMapper;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSetReplacementProviderImpl;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValueImpl;
import net.datenwerke.security.service.usermanager.entities.User;

public class ReportParameterReplacement extends
		ParameterSetReplacementProviderImpl {
	public static final String REPORT_REPLACEMENT_FOR_JUEL = "_RS_REPORT";
	
	public static final String REPORT_REPLACEMENT_NAME = "_RS_REPORT_NAME";
	public static final String REPORT_REPLACEMENT_DESCRIPTION = "_RS_REPORT_DESCRIPTION";
	public static final String REPORT_REPLACEMENT_KEY = "_RS_REPORT_KEY";
	public static final String REPORT_REPLACEMENT_ID = "_RS_REPORT_ID";

	@Override
	public void extendJuel(User user, Report report, ExpressionFactory factory, ELContext context) {
		if(null == report)
			return;
		
		VariableMapper vm = context.getVariableMapper();
		
		ReportForJuel juelObject = new ReportForJuel(report);

		vm.setVariable(REPORT_REPLACEMENT_FOR_JUEL, factory.createValueExpression(juelObject, ReportForJuel.class)); //$NON-NLS-1$
	}
	
	@Override
	public Map<String, ParameterValue> provideReplacements(User user, Report report) {
		Map<String, ParameterValue> reps = new HashMap<String, ParameterValue>();
		
		reps.put(REPORT_REPLACEMENT_NAME, new ParameterValueImpl(REPORT_REPLACEMENT_NAME, (report != null && report.getName() != null) ? report.getName() : "", String.class));
		reps.put(REPORT_REPLACEMENT_DESCRIPTION, new ParameterValueImpl(REPORT_REPLACEMENT_DESCRIPTION,(report != null && report.getDescription() != null) ? report.getDescription() : "", String.class));
		reps.put(REPORT_REPLACEMENT_KEY, new ParameterValueImpl(REPORT_REPLACEMENT_KEY,(report != null && report.getKey() != null) ? report.getKey() : "", String.class));
		reps.put(REPORT_REPLACEMENT_ID, new ParameterValueImpl(REPORT_REPLACEMENT_ID, null == report ? null : report.getId() != null ? report.getId() : report.getOldTransientId() != null ? report.getOldTransientId() : null, Long.class));
		
		return reps;
	}
}
