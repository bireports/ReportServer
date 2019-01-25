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
 
 
package net.datenwerke.rs.incubator.service.jaspertotable;

import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasourceConfig;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportVariant;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.incubator.service.jaspertotable.entities.JasperToTableConfig;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;

public class JasperToTableServiceImpl implements JasperToTableService {
	private final JasperUtilsService jasperUtils;
	
	@Inject
	public JasperToTableServiceImpl(
		JasperUtilsService jasperUtils	
		){
		
		/* store objects */
		this.jasperUtils = jasperUtils;
	}
	
	@Override
	public TableReport transformToTableReport(JasperReport report) {
		ReportProperty property = report.getReportProperty(JasperToTableModule.PROPERTY_NAME);
		if(null == property && report instanceof JasperReportVariant){
			JasperReport parent = (JasperReport) report.getParent();
			property = parent.getReportProperty(JasperToTableModule.PROPERTY_NAME);
		}

		if(null == property)
			throw new IllegalArgumentException("Report is not to be transformed to a table report");

		
		TableReport tReport = new TableReport();
		
		/* connection */
		if(null != report.getMasterFile()){
			DatasourceContainer dsContainer = new DatasourceContainer();
			tReport.setDatasourceContainer(dsContainer);
			
			dsContainer.setDatasource(report.getDatasourceContainer().getDatasource());

			if(null == ((JasperToTableConfig)property).getDatasourceContainer() || null ==  ((JasperToTableConfig)property).getDatasourceContainer().getDatasource() ){
				String query = jasperUtils.getQueryFromJRXML(report.getMasterFile().getContent());
				if(null != query)
					query = StringUtils.chomp(query);
				DatasourceDefinitionConfig config = report.getDatasourceContainer().createDatasourceConfig();
				if(config instanceof DatabaseDatasourceConfig)
					((DatabaseDatasourceConfig)config).setQuery(query);
				dsContainer.setDatasourceConfig(config);
			} else {
				tReport.setDatasourceContainer(((JasperToTableConfig)property).getDatasourceContainer());
			}
		}
		
		/* parameters */
		tReport.setParameterDefinitions(report.getParameterDefinitions());
		tReport.setParameterInstances(report.getParameterInstances());
		tReport.setSelectAllColumns(true);
		
		return tReport;
	}

}
