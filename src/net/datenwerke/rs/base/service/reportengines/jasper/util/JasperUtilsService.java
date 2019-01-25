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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.util;

import java.util.List;

import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;

import org.w3c.dom.Document;

public interface JasperUtilsService {

	/**
	 * Returns the 'queryString' tag of the given JRXML
	 * 
	 * @param jrxml The JRXML as a {@link String}
	 * @return The 'queryString' tag
	 */
	public String getQueryFromJRXML(String jrxml);
	
	/**
	 * Returns the 'queryString' tag of the given JRXML
	 * 
	 * @param jrxml The JRXML as a {@link Document}
	 * @return The 'queryString' tag
	 */
	public String getQueryFromJRXML(Document jrxml);
	
	/**
	 * Returns a {@link List} of {@link JasperParameterProposal}s holding all 'parameter' tags of the given JRXML.
	 * 
	 * @param jrxml The JRXML as a {@link String}
	 * @return A {@link List} of {@link JasperParameterProposal}s
	 */
	public List<JasperParameterProposal> extractParameters(String jrxml);
	
	/**
	 * Returns a {@link List} of {@link JasperParameterProposal}s holding all 'parameter' tags of the given JRXML.
	 * 
	 * @param jrxml The JRXML as a {@link Document}
	 * @return A {@link List} of {@link JasperParameterProposal}s
	 */
	public List<JasperParameterProposal> extractParameters(Document jrxml);
	
	/**
	 * Removes JRXML file and adapts corresponding report.
	 *  
	 * @param file
	 * @return The corresponding report
	 */
	public JasperReport removeJRXMLFile(JasperReportJRXMLFile file);

	public void persist(JasperReportJRXMLFile jrxmlFile);
	
	/**
	 * Finds the report that hosts the given jrxml file
	 * @param file
	 * @return
	 */
	public JasperReport getReportWithJRXMLFile(JasperReportJRXMLFile file);
	
	public JasperReportJRXMLFile getJRXMLFileById(Long id);

	public JasperReportJRXMLFile merge(JasperReportJRXMLFile realFile);

	List<String> getJasperAllowedLanguages();

	boolean isJasperEnabled();
}
