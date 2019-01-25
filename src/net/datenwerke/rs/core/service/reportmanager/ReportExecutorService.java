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
 
 
package net.datenwerke.rs.core.service.reportmanager;

import java.io.OutputStream;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledReportMetadata;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;


/**
 * Service providing functionality to execute reports.
 * 
 *
 */
public interface ReportExecutorService {

	public static final String OUTPUT_FORMAT_PDF = "PDF"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_EXCEL = "EXCEL"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_WORD = "WORD"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_HTML = "HTML"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_PNG = "PNG"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_CSV = "CSV"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_JSON = "JSON"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_JSON_COMPACT = "JSONC"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_RTF = "RTF"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_TABLE = "RS_TABLE"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_METADATA = "RS_METADATA"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_DATACOUNT = "RS_DATACOUNT"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT_SIMPLE_BEAN = "RS_SIMPLE_BEAN";
	public static final String OUTPUT_FORMAT_CHART_DATA = "RS_CHART_DATA";
	public static final String OUTPUT_FORMAT_TEXT = "TEXT";
	public static final String OUTPUT_FORMAT_XML = "XML";
	public static final String METADATA_FORMAT_PLAIN = "METADATA_PLAIN";
	
	
	public void isExecutable(Report report, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	
	public void isExecutable(Report report, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	
	public void isExecutable(Report report, ParameterSet additionalParameters, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	

	public CompiledReport execute(Report report, String outputFormat) throws ReportExecutorException;
	
	public CompiledReport execute(Report report, ParameterSet parameterSet, String outputFormat) throws ReportExecutorException;
	
	public CompiledReport execute(Report report, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	
	public CompiledReport execute(Report report, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	
	public CompiledReport execute(Report report, ParameterSet parameterSet, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	
	public CompiledReport execute(Report report, ParameterSet parameterSet, User user, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException;
	
	public CompiledReportMetadata exportMetadata(Report report, String outputFormat) throws ReportExecutorException;;
	
	public CompiledReportMetadata exportMetadata(Report report, User user, ParameterSet parameterSet, String outputFormat) throws ReportExecutorException;

	CompiledReport execute(OutputStream os, Report report,
			ParameterSet parameterSet, User user, String outputFormat,
			ReportExecutionConfig... configs) throws ReportExecutorException;

	public CompiledReport executeDry(Report report, ParameterSet backLinkSet,
			User user, String outputFormat,
			ReportExecutionConfig... reportExecutorConfigs) throws ReportExecutorException;

	boolean supportsStreaming(Report report, ParameterSet parameterSet,
			User user, String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException;
}
