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

import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.olap4j.CellSet;
import org.saiku.olap.dto.SaikuDimensionSelection;
import org.saiku.olap.dto.resultset.CellDataSet;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.saiku.server.rest.objects.resultset.QueryResult;
import net.datenwerke.rs.saiku.server.rest.util.RestUtil;
import net.datenwerke.rs.saiku.service.saiku.SaikuModule;
import net.datenwerke.rs.saiku.service.saiku.reportengine.config.RECSaikuChart;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledHTMLSaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledRSSaikuReport;

public class SaikuChartHTMLOutputGenerator extends SaikuOutputGeneratorImpl {

	
	@Inject
	public SaikuChartHTMLOutputGenerator(HookHandlerService hookHandler) {
		super(hookHandler);
	}

	@Override
	public CompiledRSSaikuReport exportReport(CellDataSet cellDataSet,
			CellSet cellset, List<SaikuDimensionSelection> filters,
			String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException {
		
		RECSaikuChart config = getConfig(RECSaikuChart.class, configs);
		String type = "bar";
		if(null != config){
			type = config.getType();
		}
		
		QueryResult queryResult = RestUtil.convert(cellDataSet);

		ObjectMapper om = new ObjectMapper();
		String json;
		try {
			json = om.writeValueAsString(queryResult);
		} catch (Exception e) {
			throw new ReportExecutorException(e);
		} 
		
		try {
			String tplfile = "resources/saiku/charttemplate.html";
			URL resource = getClass().getClassLoader().getResource("reportserver.properties");
			URI tpluri = resource.toURI().resolve("../../" + tplfile);
			
			String tpl = IOUtils.toString(tpluri.toURL().openStream());
			tpl = tpl.replace("/*##DATA##*/", json);
			tpl = tpl.replace("/*##TYPE##*/", type);
			
			return new CompiledHTMLSaikuReport(tpl);
		} catch (Exception e) {
			throw new ReportExecutorException(e);
		}
	}

	@Override
	public String[] getFormats() {
		return new String[]{SaikuModule.OUTPUT_FORMAT_CHART_HTML};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledHTMLSaikuReport();
	}

}
