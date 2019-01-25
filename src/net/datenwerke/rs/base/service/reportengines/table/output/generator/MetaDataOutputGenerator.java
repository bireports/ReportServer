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

import java.io.OutputStream;

import net.datenwerke.rs.base.service.reportengines.table.entities.Column.CellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * Generates an {@link RSTableModel} from an report with only the metaInformation set.
 * 
 *
 */
public class MetaDataOutputGenerator implements TableOutputGenerator {

	private RSTableModel tableModel;

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_METADATA};
	}
	
	@Override
	public void initialize(OutputStream os, TableDefinition td, boolean withSubtotals, TableReport report,  TableReport orgReport, CellFormatter[] cellFormatters, ParameterSet parameters, User user, ReportExecutionConfig... configs) {
		/* create empty table model */
		tableModel = new RSTableModel(td);
	}

	@Override
	public void nextRow() {
	}
	
	@Override
	public void addField(Object field, CellFormatter formatter){
	}

	@Override
	public void close() {
	}

	@Override
	public CompiledReport getTableObject() {
		return tableModel;
	}
	
	@Override
	public CompiledReport getFormatInfo() {
		return new RSTableModel();
	}

	
	@Override
	public boolean isCatchAll() {
		return false;
	}

	@Override
	public void addGroupRow(int[] subtotalIndices, Object[] subtotals, int[] subtotalGroupFieldIndices, Object[] subtotalGroupFieldValues,
			int rowSize, CellFormatter[] cellFormatters) {
		
	}
	
	@Override
	public boolean supportsStreaming() {
		return true;
	}

}
