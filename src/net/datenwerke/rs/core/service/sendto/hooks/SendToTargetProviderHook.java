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
 
 
package net.datenwerke.rs.core.service.sendto.hooks;

import java.util.HashMap;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.hookservices.annotations.HookConfig;
import net.datenwerke.rs.core.client.sendto.SendToClientConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;

@HookConfig
public interface SendToTargetProviderHook extends Hook {

	public SendToClientConfig consumes(Report report);

	public String getId();

	public String sendTo(Report report, HashMap<String, String> values, ReportExecutionConfig... executionConfig);
	
	public String sendTo(CompiledReport compiledReport, Report report, String format, HashMap<String, String> values, ReportExecutionConfig... executionConfig);
	
	public void scheduledSendTo(CompiledReport compiledReport, Report report, AbstractJob reportJob, String format, HashMap<String, String> values);
	
	public boolean supportsScheduling();
	
}
