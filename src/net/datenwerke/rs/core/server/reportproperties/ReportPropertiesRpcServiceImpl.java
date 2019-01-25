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
 
 
package net.datenwerke.rs.core.server.reportproperties;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportStringPropertyDto;
import net.datenwerke.rs.core.client.reportproperties.rpc.ReportPropertiesRpcService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportStringProperty;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Write;

/**
 * 
 *
 */
@Singleton
public class ReportPropertiesRpcServiceImpl extends SecuredRemoteServiceServlet
		implements ReportPropertiesRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6592646535785857296L;

	private final DtoService dtoService;
	private final ReportService reportService;
	
	
	@Inject
	public ReportPropertiesRpcServiceImpl(
		DtoService dtoService,
		ReportService reportService
		) {
		
		/* store objects */
		this.dtoService = dtoService;
		this.reportService = reportService;
	}

	@SecurityChecked(
		argumentVerification = {
			@ArgumentVerification(
				name = "report",
				isDto = true,
				verify = @RightsVerification(rights=Write.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public ReportDto updateProperties(@Named("report") ReportDto reportDto,
			List<ReportStringPropertyDto> addedProperties,
			List<ReportStringPropertyDto> modifiedProperties, List<ReportStringPropertyDto> removedProperties) throws ServerCallFailedException {
		Report report = (Report) dtoService.loadPoso(reportDto);
		
		/* remove */
		for(ReportStringPropertyDto propDto : removedProperties){
			ReportStringProperty property = (ReportStringProperty) dtoService.loadPoso(propDto);
			
			if(! report.hasReportProperty(property))
				throw new ServerCallFailedException("Report does not have property");
			
			reportService.remove(report, property);
		}
		
		/* add */
		for(ReportStringPropertyDto propertyDto : addedProperties){
			ReportStringProperty property = new ReportStringProperty();
			property.setName(propertyDto.getName());
			property.setStrValue(propertyDto.getStrValue());
			if(null == property.getName())
				property.setName(getUniqueName(report));
			report.addReportProperty(property);
			reportService.persist(property);
		}
		
		modifiedProperties.removeAll(removedProperties);
		modifiedProperties.removeAll(addedProperties);
		
		/* modify */
		
		for(ReportStringPropertyDto propertyDto : modifiedProperties){
			ReportStringProperty property = (ReportStringProperty) dtoService.loadPoso(propertyDto);
				
			if(! report.hasReportProperty(property))
				throw new ServerCallFailedException("Report does not have property");
				
			/* merge data and store report */
			dtoService.mergePoso(propertyDto, property);
			
		}
		reportService.merge(report);
		
		return (ReportDto) dtoService.createDtoFullAccess(report);
	}
	
	protected String getUniqueName(Report report) {
		String name = "unnamed";
		int i = 0;
		while(hasProperty(report, name))
			name = "unnamed_" + (++i);
		
		return name;
	}

	protected boolean hasProperty(Report report, String name) {
		for(ReportProperty p : report.getReportProperties())
			if(p instanceof ReportStringProperty)
				if(name.equals(p.getName()))
					return true;
		return false;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<String> getPropertyKeys() throws ServerCallFailedException {
		return reportService.getReportStringPropertyKeys();
	}

	

}
