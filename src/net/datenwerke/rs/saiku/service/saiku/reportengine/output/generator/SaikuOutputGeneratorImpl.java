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
 
 
package net.datenwerke.rs.saiku.service.saiku.reportengine.output.generator;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;

import org.saiku.olap.util.formatter.FlattenedCellSetFormatter;
import org.saiku.olap.util.formatter.ICellSetFormatter;

public abstract class SaikuOutputGeneratorImpl implements SaikuOutputGenerator {

	protected final HookHandlerService hookHandler;
	protected SaikuReport report;
	
	@Override
	public void initialize(SaikuReport report) {
		this.report = report;
		
	}
	
	public SaikuOutputGeneratorImpl(HookHandlerService hookHandler){
		this.hookHandler = hookHandler;
	}
	
	@Override
	public boolean isCatchAll() {
		return false;
	}
	
	@Override
	public ICellSetFormatter getCellSetFormatter() {
		return new FlattenedCellSetFormatter();
	}

	protected <R extends ReportExecutionConfig> R getConfig(Class<? extends R> type, ReportExecutionConfig... configs){
		for(ReportExecutionConfig config : configs)
			if(type.isAssignableFrom(config.getClass()))
				return (R) config;
		return null;
	}
}
