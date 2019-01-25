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
 
 
package net.datenwerke.rs.birt.service.reportengine.output.generator;

import java.io.ByteArrayOutputStream;

import net.datenwerke.rs.birt.service.reportengine.output.object.CompiledHTMLBirtReport;
import net.datenwerke.rs.birt.service.reportengine.output.object.CompiledRSBirtReport;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorRuntimeException;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

public class BirtHTMLOutputGenerator extends BirtOutputGeneratorImpl {
	
	@Override
	public CompiledRSBirtReport exportReport(
			Object oRunAndRenderTask, String outputFormat,
			ReportExecutionConfig... configs) {
		
		try {
			IRunAndRenderTask runAndRenderTask = (IRunAndRenderTask) oRunAndRenderTask;
			HTMLRenderOption options = new HTMLRenderOption();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			options.setOutputStream(bos);
			options.setOutputFormat("html");
			options.setImageHandler(new Base64ImageHandler());

			runAndRenderTask.setRenderOption(options);
			runAndRenderTask.run();

			/* create return object */
			CompiledHTMLBirtReport cbReport = new CompiledHTMLBirtReport();

			/* add report to object */
			cbReport.setReport(new String(bos.toByteArray()));

			/* return compiled report */
			return cbReport;

		} catch (EngineException e) {
			ReportExecutorRuntimeException rere = new ReportExecutorRuntimeException();
			rere.initCause(e);
			throw rere;
		}
		
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_HTML};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledHTMLBirtReport();
	}

}
