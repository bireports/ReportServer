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
 
 
package net.datenwerke.rs.tabletemplate.client.tabletemplate.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Long;
import java.lang.String;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.dto.TableReportTemplateDto;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.tabletemplate.service.tabletemplate.entities.TableReportTemplate.class)
public interface TableReportTemplateDtoPA extends PropertyAccess<TableReportTemplateDto> {


	public static final TableReportTemplateDtoPA INSTANCE = GWT.create(TableReportTemplateDtoPA.class);

	@Path("dtoId")
	public ModelKeyProvider<TableReportTemplateDto> dtoId();

	/* Properties */
	public ValueProvider<TableReportTemplateDto,String> contentType();
	public ValueProvider<TableReportTemplateDto,String> description();
	public ValueProvider<TableReportTemplateDto,String> fileExtension();
	public ValueProvider<TableReportTemplateDto,Long> id();
	public ValueProvider<TableReportTemplateDto,String> key();
	public ValueProvider<TableReportTemplateDto,String> name();
	public ValueProvider<TableReportTemplateDto,String> templateType();
	public ValueProvider<TableReportTemplateDto,Long> temporaryId();


}
