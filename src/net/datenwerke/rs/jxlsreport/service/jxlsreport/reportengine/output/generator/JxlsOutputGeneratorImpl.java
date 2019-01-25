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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.object.CompiledJxlsXlsReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.object.CompiledJxlsXlsmReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.object.CompiledJxlsXlsxReport;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.inject.Inject;

public class JxlsOutputGeneratorImpl implements JxlsOutputGenerator {

	@Inject
	public JxlsOutputGeneratorImpl(){
	}
	
	@Override
	public boolean isCatchAll() {
		return false;
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_EXCEL};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return null;
	}

	@Override
	public CompiledReport exportReport(OutputStream os, byte[] template, Connection connection, ParameterSet parameters, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
		Workbook workbook = processWorkbook(parameters, template, connection);
		
    	/* write out file */
    	try{
	    	byte[] content = null;
			if(null == os) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				content = bos.toByteArray();
			} else {
				workbook.write(os);
				os.close();
			}
			
			/* new excel */
			if(workbook instanceof XSSFWorkbook){
				if(((XSSFWorkbook)workbook).isMacroEnabled()) 
					return new CompiledJxlsXlsmReport(content);
				else
					return new CompiledJxlsXlsxReport(content);
			} 
			
			/* old excel */
			return new CompiledJxlsXlsReport(content);
    	} catch (Exception e) {
			throw new ReportExecutorException(e);
		}
	}

	protected Workbook processWorkbook(ParameterSet parameters, byte[] template,
			Connection connection) throws ReportExecutorException {
		/* prepare parameters */
		Map<String, ParameterValue> parameterMap = parameters.getParameterMap();
		Map<String, Object> simpleParams = parameters.getParameterMapSimple();
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("_parameters", parameterMap);
		beans.put("parameters", simpleParams);
		
		XLSTransformer transformer = new XLSTransformer();
		
    	ByteArrayInputStream is = new ByteArrayInputStream(template);
		
    	JxlsSqlExecutor rm = new JxlsSqlExecutor(connection, beans, parameters);
    	beans.put("sql", rm);
    	beans.put("rm", rm);
    	beans.put("StringUtils", new StringUtils());
    	beans.put("StringEscapeUtils", new StringEscapeUtils());
    	
    	try {	
    		return transformer.transformXLS(is, beans);	
		} catch (Exception e) {
			throw new ReportExecutorException(e);
		}
	}



}
