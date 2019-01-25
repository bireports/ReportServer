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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.generator;

import java.io.IOException;
import java.io.OutputStream;

import net.datenwerke.rs.base.service.reportengines.table.entities.Column.CellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.CompiledDataCountTableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates an {@link RSTableModel} from an report with only the metaInformation set.
 * 
 *
 */
public class DataCountOutputGenerator extends TableOutputGeneratorImpl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private int count = 0;
	private long start;

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_DATACOUNT};
	}
	
	@Override
	public void initialize(OutputStream os, TableDefinition td, boolean withSubtotals, TableReport report, TableReport originalReport, CellFormatter[] cellFormatters, ParameterSet parameters, User user,ReportExecutionConfig... configs) throws IOException {
		super.initialize(os, td, withSubtotals, report, originalReport, cellFormatters, parameters, user, configs);
		this.start = System.currentTimeMillis();
	}

	@Override
	public void nextRow() {
	}
	
	@Override
	public void addField(Object field, CellFormatter formatter){
		try{
			count = Integer.parseInt(String.valueOf(field));
		} catch(Exception e){
			logger.warn( "unexpected data", e);
		}
	}

	@Override
	public void close() {
	}

	@Override
	public CompiledDataCountTableReport getTableObject() {
		return new CompiledDataCountTableReport(count, System.currentTimeMillis() - start);
	}
	
	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledDataCountTableReport();
	}

	@Override
	public boolean isCatchAll() {
		return false;
	}

	@Override
	public void addGroupRow(int[] subtotalIndices, Object[] subtotals, int[] subtotalGroupFieldIndices, Object[] subtotalGroupFieldValues,
			int rowSize, CellFormatter[] cellFormatters) {
		
	}
	
}
