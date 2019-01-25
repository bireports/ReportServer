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
 
 
package net.datenwerke.rs.scripting.service.scripting.extensions.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scripting.client.scripting.dto.AddReportExportFormatProviderDto;
import net.datenwerke.rs.scripting.service.scripting.extensions.AddReportExportFormatProvider;
import net.datenwerke.rs.scripting.service.scripting.extensions.dtogen.AddReportExportFormatProvider2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for AddReportExportFormatProvider
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class AddReportExportFormatProvider2DtoGenerator implements Poso2DtoGenerator<AddReportExportFormatProvider,AddReportExportFormatProviderDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public AddReportExportFormatProvider2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public AddReportExportFormatProviderDto instantiateDto(AddReportExportFormatProvider poso)  {
		AddReportExportFormatProviderDto dto = new AddReportExportFormatProviderDto();
		return dto;
	}

	public AddReportExportFormatProviderDto createDto(AddReportExportFormatProvider poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final AddReportExportFormatProviderDto dto = new AddReportExportFormatProviderDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set description */
			dto.setDescription(StringEscapeUtils.escapeXml(StringUtils.left(poso.getDescription(),8192)));

			/*  set icon */
			dto.setIcon(StringEscapeUtils.escapeXml(StringUtils.left(poso.getIcon(),8192)));

			/*  set outputFormat */
			dto.setOutputFormat(StringEscapeUtils.escapeXml(StringUtils.left(poso.getOutputFormat(),8192)));

			/*  set parentIdWhitelist */
			List<Long> col_parentIdWhitelist = new ArrayList<Long>();
			if( null != poso.getParentIdWhitelist()){
				for(Long obj : poso.getParentIdWhitelist())
					col_parentIdWhitelist.add((Long) obj);
				dto.setParentIdWhitelist(col_parentIdWhitelist);
			}

			/*  set parentKeyWhitelist */
			List<String> col_parentKeyWhitelist = new ArrayList<String>();
			if( null != poso.getParentKeyWhitelist()){
				for(String obj : poso.getParentKeyWhitelist())
					col_parentKeyWhitelist.add((String) obj);
				dto.setParentKeyWhitelist(col_parentKeyWhitelist);
			}

			/*  set reportIdWhitelist */
			List<Long> col_reportIdWhitelist = new ArrayList<Long>();
			if( null != poso.getReportIdWhitelist()){
				for(Long obj : poso.getReportIdWhitelist())
					col_reportIdWhitelist.add((Long) obj);
				dto.setReportIdWhitelist(col_reportIdWhitelist);
			}

			/*  set reportKeyWhitelist */
			List<String> col_reportKeyWhitelist = new ArrayList<String>();
			if( null != poso.getReportKeyWhitelist()){
				for(String obj : poso.getReportKeyWhitelist())
					col_reportKeyWhitelist.add((String) obj);
				dto.setReportKeyWhitelist(col_reportKeyWhitelist);
			}

			/*  set reportType */
			dto.setReportType(poso.getReportType() );

			/*  set title */
			dto.setTitle(StringEscapeUtils.escapeXml(StringUtils.left(poso.getTitle(),8192)));

		}

		return dto;
	}


}
