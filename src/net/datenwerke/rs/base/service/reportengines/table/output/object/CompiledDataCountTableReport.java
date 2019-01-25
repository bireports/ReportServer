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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;

import org.apache.commons.lang.NotImplementedException;

/**
 * 
 *
 */
public class CompiledDataCountTableReport extends CompiledTableReport implements CompiledReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8668286824874533411L;
	
	final private int report;
	private long executeDuration;
	
	public CompiledDataCountTableReport(){
		report = -1;
	}
	
	public CompiledDataCountTableReport(int report, long executeDuration) {
		this.report = report;
		this.executeDuration = executeDuration;
	}

	public Integer getReport() {
		return report;
	}
	
	public int getDataCount(){
		return report;
	}
	
	public long getExecuteDuration(){
		return executeDuration;
	}

	public String getFileExtension() {
		throw new NotImplementedException();
	}

	public String getMimeType() {
		throw new NotImplementedException();
	}
	
	@Override
	public boolean isStringReport() {
		return false;
	}
}
