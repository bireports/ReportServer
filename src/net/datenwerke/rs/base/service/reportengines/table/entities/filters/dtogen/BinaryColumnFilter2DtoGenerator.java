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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.filters.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.table.dto.BinaryColumnFilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.BinaryOperatorDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.BinaryColumnFilterDtoDec;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.BinaryColumnFilter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.dtogen.BinaryColumnFilter2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for BinaryColumnFilter
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class BinaryColumnFilter2DtoGenerator implements Poso2DtoGenerator<BinaryColumnFilter,BinaryColumnFilterDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public BinaryColumnFilter2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public BinaryColumnFilterDtoDec instantiateDto(BinaryColumnFilter poso)  {
		BinaryColumnFilterDtoDec dto = new BinaryColumnFilterDtoDec();
		return dto;
	}

	public BinaryColumnFilterDtoDec createDto(BinaryColumnFilter poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final BinaryColumnFilterDtoDec dto = new BinaryColumnFilterDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set description */
			dto.setDescription(StringEscapeUtils.escapeXml(StringUtils.left(poso.getDescription(),8192)));

			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set columnA */
			Object tmpDtoColumnDtogetColumnA = dtoServiceProvider.get().createDto(poso.getColumnA(), here, referenced);
			dto.setColumnA((ColumnDto)tmpDtoColumnDtogetColumnA);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoColumnDtogetColumnA, poso.getColumnA(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setColumnA((ColumnDto)refDto);
				}
			});

			/*  set columnB */
			Object tmpDtoColumnDtogetColumnB = dtoServiceProvider.get().createDto(poso.getColumnB(), here, referenced);
			dto.setColumnB((ColumnDto)tmpDtoColumnDtogetColumnB);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoColumnDtogetColumnB, poso.getColumnB(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setColumnB((ColumnDto)refDto);
				}
			});

			/*  set operator */
			Object tmpDtoBinaryOperatorDtogetOperator = dtoServiceProvider.get().createDto(poso.getOperator(), referenced, referenced);
			dto.setOperator((BinaryOperatorDto)tmpDtoBinaryOperatorDtogetOperator);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoBinaryOperatorDtogetOperator, poso.getOperator(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setOperator((BinaryOperatorDto)refDto);
				}
			});

		}

		return dto;
	}


}
