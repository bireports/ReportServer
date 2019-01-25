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
 
 
package net.datenwerke.rs.birt.service.datasources.birtreport.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.birt.client.datasources.dto.BirtReportDatasourceConfigDto;
import net.datenwerke.rs.birt.client.datasources.dto.BirtReportDatasourceTargetTypeDto;
import net.datenwerke.rs.birt.client.reportengines.dto.BirtReportDto;
import net.datenwerke.rs.birt.service.datasources.birtreport.entities.BirtReportDatasourceConfig;
import net.datenwerke.rs.birt.service.datasources.birtreport.entities.dtogen.BirtReportDatasourceConfig2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for BirtReportDatasourceConfig
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class BirtReportDatasourceConfig2DtoGenerator implements Poso2DtoGenerator<BirtReportDatasourceConfig,BirtReportDatasourceConfigDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public BirtReportDatasourceConfig2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public BirtReportDatasourceConfigDto instantiateDto(BirtReportDatasourceConfig poso)  {
		BirtReportDatasourceConfigDto dto = new BirtReportDatasourceConfigDto();
		return dto;
	}

	public BirtReportDatasourceConfigDto createDto(BirtReportDatasourceConfig poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final BirtReportDatasourceConfigDto dto = new BirtReportDatasourceConfigDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set queryWrapper */
			dto.setQueryWrapper(StringEscapeUtils.escapeXml(StringUtils.left(poso.getQueryWrapper(),8192)));

			/*  set report */
			Object tmpDtoBirtReportDtogetReport = dtoServiceProvider.get().createDto(poso.getReport(), referenced, referenced);
			dto.setReport((BirtReportDto)tmpDtoBirtReportDtogetReport);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoBirtReportDtogetReport, poso.getReport(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setReport((BirtReportDto)refDto);
				}
			});

			/*  set target */
			dto.setTarget(StringEscapeUtils.escapeXml(StringUtils.left(poso.getTarget(),8192)));

			/*  set targetType */
			Object tmpDtoBirtReportDatasourceTargetTypeDtogetTargetType = dtoServiceProvider.get().createDto(poso.getTargetType(), referenced, referenced);
			dto.setTargetType((BirtReportDatasourceTargetTypeDto)tmpDtoBirtReportDatasourceTargetTypeDtogetTargetType);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoBirtReportDatasourceTargetTypeDtogetTargetType, poso.getTargetType(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setTargetType((BirtReportDatasourceTargetTypeDto)refDto);
				}
			});

		}

		return dto;
	}


}
