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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ExecutedReportFileReferenceDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.decorator.ExecutedReportFileReferenceDtoDec;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.ExecutedReportFileReference;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.dtogen.ExecutedReportFileReference2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for ExecutedReportFileReference
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ExecutedReportFileReference2DtoGenerator implements Poso2DtoGenerator<ExecutedReportFileReference,ExecutedReportFileReferenceDtoDec> {

	private final net.datenwerke.treedb.service.treedb.dtogen.AbstractNode2DtoPostProcessor postProcessor_1;
	private final net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post.AbstractTsDiskNode2DtoPost postProcessor_2;
	private final net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post.GeneralReference2DtoPost postProcessor_3;
	private final net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.post.ExecuteReportFileReference2DtoPost postProcessor_4;
	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public ExecutedReportFileReference2DtoGenerator(
		Provider<DtoService> dtoServiceProvider,
		net.datenwerke.treedb.service.treedb.dtogen.AbstractNode2DtoPostProcessor postProcessor_1,
		net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post.AbstractTsDiskNode2DtoPost postProcessor_2,
		net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post.GeneralReference2DtoPost postProcessor_3,
		net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.post.ExecuteReportFileReference2DtoPost postProcessor_4	){
		this.dtoServiceProvider = dtoServiceProvider;
		this.postProcessor_1 = postProcessor_1;
		this.postProcessor_2 = postProcessor_2;
		this.postProcessor_3 = postProcessor_3;
		this.postProcessor_4 = postProcessor_4;
	}

	public ExecutedReportFileReferenceDtoDec instantiateDto(ExecutedReportFileReference poso)  {
		ExecutedReportFileReferenceDtoDec dto = new ExecutedReportFileReferenceDtoDec();

		/* post processing */
		this.postProcessor_1.dtoInstantiated(poso, dto);
		this.postProcessor_2.dtoInstantiated(poso, dto);
		this.postProcessor_3.dtoInstantiated(poso, dto);
		this.postProcessor_4.dtoInstantiated(poso, dto);
		return dto;
	}

	public ExecutedReportFileReferenceDtoDec createDto(ExecutedReportFileReference poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final ExecutedReportFileReferenceDtoDec dto = new ExecutedReportFileReferenceDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set description */
			dto.setDescription(StringEscapeUtils.escapeXml(StringUtils.left(poso.getDescription(),8192)));

			/*  set id */
			dto.setId(poso.getId() );

			/*  set name */
			dto.setName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getName(),8192)));

			/*  set position */
			dto.setPosition(poso.getPosition() );

		}
		if(here.compareTo(DtoView.LIST) >= 0){
			/*  set createdOn */
			dto.setCreatedOn(poso.getCreatedOn() );

			/*  set lastUpdated */
			dto.setLastUpdated(poso.getLastUpdated() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set flags */
			dto.setFlags(poso.getFlags() );

			/*  set outputFormat */
			dto.setOutputFormat(StringEscapeUtils.escapeXml(StringUtils.left(poso.getOutputFormat(),8192)));

		}

		/* post processing */
		this.postProcessor_1.dtoCreated(poso, dto);
		this.postProcessor_2.dtoCreated(poso, dto);
		this.postProcessor_3.dtoCreated(poso, dto);
		this.postProcessor_4.dtoCreated(poso, dto);

		return dto;
	}


}
