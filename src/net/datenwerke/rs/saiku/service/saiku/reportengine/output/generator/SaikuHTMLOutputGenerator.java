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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledHTMLSaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledRSSaikuReport;

import org.olap4j.CellSet;
import org.saiku.olap.dto.SaikuDimensionSelection;
import org.saiku.olap.dto.resultset.AbstractBaseCell;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.dto.resultset.DataCell;
import org.saiku.olap.dto.resultset.MemberCell;

public class SaikuHTMLOutputGenerator extends SaikuOutputGeneratorImpl {
	
	
	@Inject
	public SaikuHTMLOutputGenerator(HookHandlerService hookHandler) {
		super(hookHandler);
	}

	@Override
	public CompiledRSSaikuReport exportReport(CellDataSet cellDataSet,
			CellSet cellset, List<SaikuDimensionSelection> filters,
			String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("<table style=\"border: 1px solid grey\">");
		for(AbstractBaseCell[] r : cellDataSet.getCellSetHeaders()){
			pw.print("<tr>");
			for(AbstractBaseCell c : r){
				pw.print("<th>" + c + "</th>");
			}
			pw.print("</tr>");
		}

		for(AbstractBaseCell[] r : cellDataSet.getCellSetBody()){
			pw.println("<tr>");
			for(AbstractBaseCell c : r){
				if(c instanceof MemberCell){
					MemberCell m = (MemberCell) c;
					pw.print("<td>" +m.getFormattedValue() + "</td>");

					
				}else if(c instanceof DataCell){
					DataCell d = (DataCell) c;
					
					pw.print("<td>" +c.getFormattedValue() + " " + "</td>");
				}
				
			}
			pw.println("</tr>");
		}
		
		
		pw.println("</table>");

		return new CompiledHTMLSaikuReport(sw.getBuffer().toString() );
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_HTML};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledHTMLSaikuReport();
	}

}
