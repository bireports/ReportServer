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
 
 
package net.datenwerke.rs.base.service.reportengines.hookers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.datenwerke.rs.base.service.reportengines.jasper.JasperReportEngine;
import net.datenwerke.rs.base.service.reportengines.table.TableReportEngine;
import net.datenwerke.rs.core.service.reportmanager.engine.ReportEngine;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportEngineProviderHook;

public class BaseReportEngineProviderHooker implements ReportEngineProviderHook {

	@Override
	public Collection<? extends Class<? extends ReportEngine>> getReportEngines() {
		Set<Class<? extends ReportEngine>> engines = new HashSet<Class<? extends ReportEngine>>();		
		
		engines.add(JasperReportEngine.class);
		engines.add(TableReportEngine.class);
		
		return engines;
	}

}
