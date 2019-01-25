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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.generator;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.hooks.JasperExportHook;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledRSJasperReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.security.service.usermanager.entities.User;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.design.JasperDesign;

public abstract class JasperOutputGeneratorImpl implements JasperOutputGenerator {

	protected final HookHandlerService hookHandler;
	
	public JasperOutputGeneratorImpl(HookHandlerService hookHandler){
		this.hookHandler = hookHandler;
	}
	
	public boolean hasConfig(Class<? extends ReportExecutionConfig> type, ReportExecutionConfig... configs){
		return null != getConfig(type, configs);
	}
	
	public <C extends ReportExecutionConfig> C getConfig(Class<C> type, ReportExecutionConfig... configs){
		if(null == configs || configs.length == 0)
			return null;
		
		for(ReportExecutionConfig config : configs)
			if(type.isAssignableFrom(config.getClass()))
				return (C) config;
			
		return null;
	}
	
	@Override
	public void adjustDesign(JasperDesign jasperDesign, String outputFormat,
			ReportExecutionConfig... configs) {
	}

	protected void callPostHooks(String outputFormat,
			JRAbstractExporter exporter, JasperReport report, CompiledRSJasperReport cjrReport, User user) {
		for(JasperExportHook cb: hookHandler.getHookers(JasperExportHook.class)) {
			if(null != cb.getFormats() && cb.getFormats().contains(outputFormat))
				cb.afterExport(outputFormat, exporter, report, cjrReport, user);
		}
	}

	protected void callPreHooks(String outputFormat, JRAbstractExporter exporter, JasperReport report, User user) {
		for(JasperExportHook cb: hookHandler.getHookers(JasperExportHook.class)) {
			if(null != cb.getFormats() && cb.getFormats().contains(outputFormat))
				cb.beforeExport(outputFormat, exporter, report, user);
		}
	}
	
	@Override
	public boolean isCatchAll() {
		return false;
	}

}
