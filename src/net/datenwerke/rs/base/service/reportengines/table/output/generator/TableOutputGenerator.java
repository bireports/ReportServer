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

import java.io.IOException;
import java.io.OutputStream;

import net.datenwerke.rs.base.service.reportengines.table.entities.Column.CellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.output.ReportOutputGenerator;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * An output generator for dynamic lists.
 *  
 *
 */
public interface TableOutputGenerator extends ReportOutputGenerator{


	/**
	 * Initializes the generator before the first data point is provided.
	 * 
	 * @param os The output stream to be used. If no output stream is provided (the object is null), then the report should be generated in memory.
	 * @param td
	 * @param withSubtotals
	 * @param report
	 * @param originalReport
	 * @param cellFormatters
	 * @param parameters 
	 * @param user 
	 * @param configs
	 * @throws IOException
	 */
	public void initialize(OutputStream os, TableDefinition td, boolean withSubtotals, TableReport report, TableReport originalReport, CellFormatter[] cellFormatters, ParameterSet parameters, User user, ReportExecutionConfig... configs) throws IOException;
	
	/**
	 * Informs the generator that the current data row is finished and a new row is about to begin
	 * 
	 * @throws IOException
	 */
	public void nextRow() throws IOException;
	
	/**
	 * Adds a new cell to the output.
	 * 
	 * @param field
	 * @param cellFormatter
	 * @throws IOException
	 */
	public void addField(Object field, CellFormatter cellFormatter) throws IOException;
	
	/**
	 * Informs the generator that the data is completely processed.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;
	
	/**
	 * Is called upon completion to get the final report object. In case an 
	 * outputstream is used (see @{@link #initialize(OutputStream, TableDefinition, boolean, TableReport, TableReport, CellFormatter[], ReportExecutionConfig...)}
	 * the {@link CompiledReport} object should not contain actual report data.
	 * 
	 * @return The finished report
	 */
	public CompiledReport getTableObject();

	/**
	 * In case a report is exported using subtotals, this method is called whenever a group is finished and an aggregation row needs to be added.
	 * 
	 * @param subtotalIndices
	 * @param subtotals
	 * @param subtotalGroupFieldIndices
	 * @param subtotalGroupFieldValues
	 * @param rowSize
	 * @param cellFormatters
	 * @throws IOException
	 */
	public void addGroupRow(int[] subtotalIndices, Object[] subtotals, int[] subtotalGroupFieldIndices, Object[] subtotalGroupFieldValues, int rowSize, CellFormatter[] cellFormatters) throws IOException;

	/**
	 * Return true if the output generator is able to stream data. 
	 * 
	 * @return
	 */
	public boolean supportsStreaming();


}
