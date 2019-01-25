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
 
 
package net.datenwerke.rs.core.service.sendto.hooks.adapter;

import java.util.HashMap;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.client.sendto.SendToClientConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.sendto.hooks.SendToTargetProviderHook;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.hookservices.HookAdapterProcessor")
public class SendToTargetProviderHookAdapter implements SendToTargetProviderHook {

	@Override
	public SendToClientConfig consumes(Report report)  {
		return null;
	}


	@Override
	public String getId()  {
		return null;
	}


	@Override
	public String sendTo(Report report, HashMap<String, String> values, ReportExecutionConfig... executionConfig)  {
		return "";
	}
	
	@Override
	public String sendTo(CompiledReport compiledReport, Report report,
			String format, HashMap<String, String> values,
			ReportExecutionConfig... executionConfig) {
		return sendTo(report,values);
	}

	@Override
	public void scheduledSendTo(CompiledReport compiledReport, Report report, AbstractJob reportJob, String format, HashMap<String, String> values){
		sendTo(compiledReport, report, format, values);
	}
	
	@Override
	public boolean supportsScheduling(){
		return true;
	}

}
