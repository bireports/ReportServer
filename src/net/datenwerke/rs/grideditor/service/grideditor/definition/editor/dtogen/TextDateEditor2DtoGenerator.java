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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.editor.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.grideditor.client.grideditor.dto.TextDateEditorDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.TextDateEditorDtoDec;
import net.datenwerke.rs.grideditor.service.grideditor.definition.editor.TextDateEditor;
import net.datenwerke.rs.grideditor.service.grideditor.definition.editor.dtogen.TextDateEditor2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for TextDateEditor
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TextDateEditor2DtoGenerator implements Poso2DtoGenerator<TextDateEditor,TextDateEditorDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public TextDateEditor2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TextDateEditorDtoDec instantiateDto(TextDateEditor poso)  {
		TextDateEditorDtoDec dto = new TextDateEditorDtoDec();
		return dto;
	}

	public TextDateEditorDtoDec createDto(TextDateEditor poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final TextDateEditorDtoDec dto = new TextDateEditorDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set dateFormat */
			dto.setDateFormat(StringEscapeUtils.escapeXml(StringUtils.left(poso.getDateFormat(),8192)));

		}

		return dto;
	}


}
