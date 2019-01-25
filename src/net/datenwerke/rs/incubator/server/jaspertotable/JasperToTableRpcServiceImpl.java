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
 
 
package net.datenwerke.rs.incubator.server.jaspertotable;

import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.JasperToTableConfigDto;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.decorator.JasperToTableConfigDtoDec;
import net.datenwerke.rs.incubator.client.jaspertotable.rpc.JasperToTableRpcService;
import net.datenwerke.rs.incubator.service.jaspertotable.JasperToTableModule;
import net.datenwerke.rs.incubator.service.jaspertotable.entities.JasperToTableConfig;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class JasperToTableRpcServiceImpl extends SecuredRemoteServiceServlet
		implements JasperToTableRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7621761376118773720L;
	
	private final DtoService dtoService;
	private final ReportService reportService;
	
	@Inject
	public JasperToTableRpcServiceImpl(
		DtoService dtoService,
		ReportService reportService
		){
		
		/* store objects */
		this.dtoService = dtoService;
		this.reportService = reportService;
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public JasperToTableConfigDto getConfig(JasperReportDto reportDto)
			throws ServerCallFailedException {
		JasperReport report = (JasperReport) dtoService.loadPoso(reportDto);
		
		ReportProperty property = report.getReportProperty(JasperToTableModule.PROPERTY_NAME);
		if(null == property || ! (property instanceof JasperToTableConfig))
			return null;

		return (JasperToTableConfigDto) dtoService.createDto(property, DtoView.ALL, DtoView.MINIMAL);
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public void setConfig(JasperReportDto reportDto, JasperToTableConfigDto configDto)
			throws ServerCallFailedException {
		JasperReport report = (JasperReport) dtoService.loadPoso(reportDto);
		
		ReportProperty property = report.getReportProperty(JasperToTableModule.PROPERTY_NAME);
		
		boolean isActive = (null != configDto && Boolean.TRUE.equals(((JasperToTableConfigDtoDec)configDto).isActive())); 

		/* remove */
		if(! isActive && null != property)
			reportService.remove(report, property);
		else if(isActive) {
			if(null == property){
				property = new JasperToTableConfig();
				property.setName(JasperToTableModule.PROPERTY_NAME);
				report.addReportProperty(property);
				reportService.persist(property);
			}
			
			if(null != configDto.getDatasourceContainer())
				((JasperToTableConfig)property).setDatasourceContainer((DatasourceContainer) dtoService.loadAndMergePoso(configDto.getDatasourceContainer()));
		}
		
		reportService.merge(report);
	}

}
