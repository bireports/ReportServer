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
 
 
package net.datenwerke.rs.base.client.parameters.datasource.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.String;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.base.client.parameters.datasource.dto.BoxLayoutModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.BoxLayoutPackModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDataDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDefinitionDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.ModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.MultiSelectionModeDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.SingleSelectionModeDto;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.parameters.dto.DatatypeDto;
import net.datenwerke.rs.core.client.parameters.dto.pa.ParameterDefinitionDtoPA;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.base.service.parameters.datasource.DatasourceParameterDefinition.class)
public interface DatasourceParameterDefinitionDtoPA extends ParameterDefinitionDtoPA {


	public static final DatasourceParameterDefinitionDtoPA INSTANCE = GWT.create(DatasourceParameterDefinitionDtoPA.class);


	/* Properties */
	public ValueProvider<DatasourceParameterDefinitionDto,BoxLayoutModeDto> boxLayoutMode();
	public ValueProvider<DatasourceParameterDefinitionDto,Integer> boxLayoutPackColSize();
	public ValueProvider<DatasourceParameterDefinitionDto,BoxLayoutPackModeDto> boxLayoutPackMode();
	public ValueProvider<DatasourceParameterDefinitionDto,DatasourceContainerDto> datasourceContainer();
	public ValueProvider<DatasourceParameterDefinitionDto,String> format();
	public ValueProvider<DatasourceParameterDefinitionDto,Integer> height();
	public ValueProvider<DatasourceParameterDefinitionDto,ModeDto> mode();
	public ValueProvider<DatasourceParameterDefinitionDto,List<DatasourceParameterDataDto>> multiDefaultValueSimpleData();
	public ValueProvider<DatasourceParameterDefinitionDto,MultiSelectionModeDto> multiSelectionMode();
	public ValueProvider<DatasourceParameterDefinitionDto,String> postProcess();
	public ValueProvider<DatasourceParameterDefinitionDto,DatatypeDto> returnType();
	public ValueProvider<DatasourceParameterDefinitionDto,DatasourceParameterDataDto> singleDefaultValueSimpleData();
	public ValueProvider<DatasourceParameterDefinitionDto,SingleSelectionModeDto> singleSelectionMode();
	public ValueProvider<DatasourceParameterDefinitionDto,Integer> width();


}
