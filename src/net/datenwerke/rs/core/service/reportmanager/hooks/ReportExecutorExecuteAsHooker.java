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

import java.util.Collection;

import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.security.service.usermanager.entities.User;

public interface ReportExecutorExecuteAsHooker extends Hook {

	public interface ExecuteConfig{
		public String getFormat();
		public Collection<ReportExecutionConfig> getConfig();
	};
	
	boolean consumes(ReportDto report, String format);
	
	ExecuteConfig getConfig(ReportDto report, User currentUser, String format, DwModel config);

	DwModel adjustResult(ReportDto reportDto, User currentUser, Collection<ReportExecutionConfig> config, Object res);

	ReportDto adjustReport(ReportDto report, User currentUser, String format, Collection<ReportExecutionConfig> config);
}
