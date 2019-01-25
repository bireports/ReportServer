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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport__;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.simplequery.annotations.Join;
import net.datenwerke.rs.utils.simplequery.annotations.Predicate;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.rs.utils.xml.XMLUtilsService;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * 
 *
 */
public class JasperUtilsServiceImpl implements JasperUtilsService {

	private static final String CONFIG_FILE = "reportengines/reportengines.cf";
	private final Provider<EntityManager> entityManagerProvider;
	private final XMLUtilsService xmlUtils;
	private ConfigService configService;
	
	@Inject
	public JasperUtilsServiceImpl(
		ConfigService configService,	
		Provider<EntityManager> entityManagerProvider,
		XMLUtilsService xmlUtils	
		){
		
		/* store objects */
		this.configService = configService;
		this.entityManagerProvider = entityManagerProvider;
		this.xmlUtils = xmlUtils;
	}
	
	@Override
	public List<JasperParameterProposal> extractParameters(String jrxml) {
		return extractParameters(getJRXMLDocument(jrxml));
	}

	@Override
	public List<JasperParameterProposal> extractParameters(Document jrxml) {
		List<JasperParameterProposal> parameters = new ArrayList<JasperParameterProposal>();
		
		/* find parameter tags */
		NodeList parameterTags = jrxml.getElementsByTagName("parameter"); //$NON-NLS-1$
		for(int i = 0; i < parameterTags.getLength(); i++){
			Element parameterElement = (Element)parameterTags.item(i);
		
			JasperParameterProposal proposal = JasperParameterProposal.createFrom(parameterElement);
			if(! "net.sf.jasperreports.engine.JasperReport".equals(proposal.getType()))
				parameters.add(proposal);
		}
		
		return parameters;
	}

	@Override
	public String getQueryFromJRXML(String jrxml) {
		return getQueryFromJRXML(getJRXMLDocument(jrxml));
	}

	@Override
	public String getQueryFromJRXML(Document jrxml) {
		NodeList list = jrxml.getElementsByTagName("queryString");
		if(list.getLength() > 0){
			Element queryStringEl = (Element) list.item(0);
			return queryStringEl.getTextContent();
		}
		return null;
	}
	
	private Document getJRXMLDocument(String jrxml){
		try{
			Document jrxmlDoc = xmlUtils.readStringIntoJAXPDoc(jrxml, "UTF-8");
			return jrxmlDoc;
		} catch(Exception e){
			IllegalArgumentException iae = new IllegalArgumentException("Could not parse jrxml."); //$NON-NLS-1$
			iae.initCause(e);
			throw iae;
		}
	}

	@Override
	public JasperReport getReportWithJRXMLFile(JasperReportJRXMLFile file){
		JasperReport report = getReportWithJRXMLMaster(file);
		if(null != report)
			return report;
		return getReportWithJRXMLSubfile(file);
	}
	
	@QueryByAttribute(where=JasperReport__.masterFile)
	public JasperReport getReportWithJRXMLMaster(JasperReportJRXMLFile file){
		return null; // magic
	}
	
	@SimpleQuery(from=JasperReport.class,join=@Join(joinAttribute=JasperReport__.subFiles, where=@Predicate(value="file")))
	public JasperReport getReportWithJRXMLSubfile(@Named("file") JasperReportJRXMLFile file){
		return null; // magic
	}
	
	
	@Override
	@FireRemoveEntityEvents
	public JasperReport removeJRXMLFile(JasperReportJRXMLFile file) {
		JasperReport report = getReportWithJRXMLSubfile(file);
		if(null != report)
			report.getSubFiles().remove(file);
		else {
			report = getReportWithJRXMLMaster(file);
			if(null != report)
				report.setMasterFile(null);
		}
		
		/* remove */
		EntityManager em = entityManagerProvider.get();
		em.remove(em.find(file.getClass(), file.getId()));
		
		return report;
	}

	@Override
	@FirePersistEntityEvents
	public void persist(JasperReportJRXMLFile jrxmlFile) {
		entityManagerProvider.get().persist(jrxmlFile);
	}
	
	@Override
	@FireMergeEntityEvents
	public JasperReportJRXMLFile merge(JasperReportJRXMLFile jrxmlFile) {
		 return entityManagerProvider.get().merge(jrxmlFile);
	}
	
	@Override
	@QueryById
	public JasperReportJRXMLFile getJRXMLFileById(Long id) {
		return null; // magic
	}
	
	@Override
	public boolean isJasperEnabled(){
		return configService.getConfig(CONFIG_FILE).getBoolean("reportengine.jasper.enable", true);
	}
	
	@Override
	public List<String> getJasperAllowedLanguages(){
		return ((List)configService.getConfig(CONFIG_FILE).getList("security.jasper.allowedlanguages"));
	}
	

}
