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
 
 
package net.datenwerke.rs.core.service.reportmanager.hooks;

import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public abstract class ConfigureReportViaRequestAndLocationImpl implements
		ConfigureReportViaHistoryLocationHook,
		ConfigureReportViaHttpRequestHook {

	protected interface ParameterProvider{
		Enumeration<String> getParameterNames();
		String getParameter(String key);
	}
	
	@Override
	public final void adjustReport(Report report, final HttpServletRequest req) {
		adjustReport(report, new ParameterProvider() {
			
			@Override
			public Enumeration<String> getParameterNames() {
				return req.getParameterNames();
			}
			
			@Override
			public String getParameter(String key) {
				return req.getParameter(key);
			}
		});
	}

	@Override
	public void adjustReport(Report report, final HistoryLocation location) {
		adjustReport(report, new ParameterProvider() {
			
			@Override
			public Enumeration<String> getParameterNames() {
				return Collections.enumeration(location.getParameterNames());
			}
			
			@Override
			public String getParameter(String key) {
				return location.getParameterValue(key);
			}
		});
	}
	
	protected abstract void adjustReport(Report report, ParameterProvider parameterProvider);

}
