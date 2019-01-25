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
 
 
package net.datenwerke.rs.core.client.parameters.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.decorator.ParameterDefinitionDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition.class)
public interface ParameterDefinitionDtoPA extends PropertyAccess<ParameterDefinitionDto> {


	public static final ParameterDefinitionDtoPA INSTANCE = GWT.create(ParameterDefinitionDtoPA.class);

	@Path("dtoId")
	public ModelKeyProvider<ParameterDefinitionDto> dtoId();

	/* Properties */
	public ValueProvider<ParameterDefinitionDto,List<ParameterDefinitionDto>> dependsOn();
	public ValueProvider<ParameterDefinitionDto,String> description();
	public ValueProvider<ParameterDefinitionDto,Boolean> displayInline();
	public ValueProvider<ParameterDefinitionDto,Boolean> editable();
	public ValueProvider<ParameterDefinitionDto,Boolean> hidden();
	public ValueProvider<ParameterDefinitionDto,Long> id();
	public ValueProvider<ParameterDefinitionDto,String> key();
	public ValueProvider<ParameterDefinitionDto,Integer> labelWidth();
	public ValueProvider<ParameterDefinitionDto,Boolean> mandatory();
	public ValueProvider<ParameterDefinitionDto,Integer> n();
	public ValueProvider<ParameterDefinitionDto,String> name();


}
