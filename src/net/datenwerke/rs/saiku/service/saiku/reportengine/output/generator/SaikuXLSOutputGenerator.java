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
 
 
package net.datenwerke.rs.saiku.service.saiku.reportengine.output.generator;

import java.util.List;

import javax.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledRSSaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledXLSSaikuReport;

import org.olap4j.CellSet;
import org.saiku.olap.dto.SaikuDimensionSelection;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.query2.ThinHierarchy;
import org.saiku.olap.util.formatter.ICellSetFormatter;
import org.saiku.service.util.export.ExcelExporter;

public class SaikuXLSOutputGenerator extends SaikuOutputGeneratorImpl {

	@Inject
	public SaikuXLSOutputGenerator(HookHandlerService hookHandler) {
		super(hookHandler);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_EXCEL};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledXLSSaikuReport();
	}


	@Override
	public CompiledRSSaikuReport exportReport(CellDataSet cellDataSet,
			CellSet cellset, List<ThinHierarchy> filters, ICellSetFormatter formatter,
			String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException {

		byte[] excel = ExcelExporter.exportExcel(cellDataSet, formatter, filters);
		return new CompiledXLSSaikuReport(excel);
	}

}
