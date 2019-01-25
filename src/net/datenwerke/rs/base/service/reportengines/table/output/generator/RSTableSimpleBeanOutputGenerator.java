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
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSSimpleTableBean;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * Generates an RSTableModel from a given report.
 * 
 * 
 *
 */
public class RSTableSimpleBeanOutputGenerator extends TableOutputGeneratorImpl {

	private RSSimpleTableBean bean;
	private int fieldInRow = 0;
	
	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_SIMPLE_BEAN};
	}
	
	@Override
	public void initialize(OutputStream os, TableDefinition td, boolean withSubtotals, TableReport report, TableReport orgReport, CellFormatter[] cellFormatters, ParameterSet parameters, User user, ReportExecutionConfig... configs) throws IOException {
		super.initialize(os, td, withSubtotals, report, orgReport, cellFormatters, parameters, user, configs);
		
		bean = new RSSimpleTableBean();
		bean.setDefinition(td, report);
	}

	@Override
	public void nextRow() {
		bean.nextRow();
		fieldInRow = 0;
	}
	
	@Override
	public void addField(Object field, CellFormatter formatter){
		bean.addField(getValueOf(field));
		fieldInRow++;
	}

	@Override
	public CompiledReport getTableObject() {
		return bean;
	}
	
	@Override
	public CompiledReport getFormatInfo() {
		return new RSSimpleTableBean();
	}

	@Override
	public boolean isCatchAll() {
		return false;
	}
	
	@Override
	public void close() {
		bean.close();
	}
	


}
