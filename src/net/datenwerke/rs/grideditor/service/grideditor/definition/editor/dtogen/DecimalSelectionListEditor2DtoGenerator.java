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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.editor.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.grideditor.client.grideditor.dto.DecimalSelectionListEditorDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.DecimalSelectionListEditorDtoDec;
import net.datenwerke.rs.grideditor.service.grideditor.definition.editor.DecimalSelectionListEditor;
import net.datenwerke.rs.grideditor.service.grideditor.definition.editor.dtogen.DecimalSelectionListEditor2DtoGenerator;

/**
 * Poso2DtoGenerator for DecimalSelectionListEditor
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class DecimalSelectionListEditor2DtoGenerator implements Poso2DtoGenerator<DecimalSelectionListEditor,DecimalSelectionListEditorDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public DecimalSelectionListEditor2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public DecimalSelectionListEditorDtoDec instantiateDto(DecimalSelectionListEditor poso)  {
		DecimalSelectionListEditorDtoDec dto = new DecimalSelectionListEditorDtoDec();
		return dto;
	}

	public DecimalSelectionListEditorDtoDec createDto(DecimalSelectionListEditor poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final DecimalSelectionListEditorDtoDec dto = new DecimalSelectionListEditorDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set forceSelection */
			dto.setForceSelection(poso.isForceSelection() );

			/*  set valueMap */
			dto.setValueMap(poso.getValueMap() );

			/*  set values */
			List<BigDecimal> col_values = new ArrayList<BigDecimal>();
			if( null != poso.getValues()){
				for(BigDecimal obj : poso.getValues())
					col_values.add((BigDecimal) obj);
				dto.setValues(col_values);
			}

		}

		return dto;
	}


}
