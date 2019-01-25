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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;

import net.datenwerke.rs.base.service.reportengines.table.entities.Column.CellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.CompiledJSONTableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * 
 *
 */
public class CompactJSONOutputGenerator extends TableOutputGeneratorImpl{
	

	protected StringBuffer stringBuffer;
	
	protected PrintWriter writer;
	
	protected boolean first = true;
	int cell = 0;
	
	@Override
	public void addField(Object field, CellFormatter formatter) throws IOException {
		if(cell > 0)
			stringBuffer.append(",");
		else
			stringBuffer.append("[");
		
		if(field instanceof Number || field instanceof BigDecimal)
			stringBuffer.append(field);
		else if(field == null)
			stringBuffer.append("null");
		else if(field instanceof Boolean)
			stringBuffer.append(Boolean.TRUE.equals(field) ? "true" : "false");
		else {
			Object value = getValueOf(field, formatter);
			stringBuffer.append("\"").append(String.valueOf(value).replace("\\","\\\\").replace("\"", "\\\"").replaceAll("[\\r\\n\\s]+", " ")).append("\"");
		}
		
		if(null != writer){
			writer.write(stringBuffer.toString());
			stringBuffer.delete(0, stringBuffer.length());
		}
		
		cell++;
	}

	@Override
	public void close() throws IOException {
		stringBuffer.append("]]}");
		
		if(null != writer){
			writer.write(stringBuffer.toString());
			stringBuffer.delete(0, stringBuffer.length());
			
			writer.close();
		}
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_JSON_COMPACT};
	}

	@Override
	public CompiledReport getTableObject() {
		return new CompiledJSONTableReport(stringBuffer.toString());
	}

	@Override
	public void initialize(OutputStream os, TableDefinition td, boolean withSubtotals, TableReport report, TableReport orgReport, CellFormatter[] cellFormatters, ParameterSet parameters, User user, ReportExecutionConfig... configs) throws IOException {
		super.initialize(os, td, withSubtotals, report, orgReport, cellFormatters, parameters, user, configs);

		/* initialize buffer */
		stringBuffer = new StringBuffer();
		if(null != os)
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os, charset)));
		
		stringBuffer.append("{");
		
		stringBuffer.append("\"labels\":[");
		boolean first = true;
		for(String name : td.getColumnNames()){
			if(first)
				first = false;
			else
				stringBuffer.append(",");
			stringBuffer.append("\"").append(name.replace("\"", "\\\"")).append("\"");
		}
			
		stringBuffer.append("],\"types\":[");
		first = true;
		for(Class type : td.getColumnTypes()){
			if(first)
				first = false;
			else
				stringBuffer.append(",");
			stringBuffer.append("\"").append(type.getSimpleName()).append("\"");
		}
		
		stringBuffer.append("],\"data\":[");
	}
	
	@Override
	public void nextRow() throws IOException {
		stringBuffer.append("],");
		
		if(null != writer){
			writer.write(stringBuffer.toString());
			stringBuffer.delete(0, stringBuffer.length());
		}
		
		cell = 0;
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledJSONTableReport(null);
	}

	
}
