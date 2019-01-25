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
 
 
package net.datenwerke.rs.scripting.client.scripting.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.String;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scripting.client.scripting.dto.AddReportExportFormatProviderDto;
import net.datenwerke.rs.terminal.client.terminal.dto.pa.CommandResultExtensionDtoPA;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.scripting.service.scripting.extensions.AddReportExportFormatProvider.class)
public interface AddReportExportFormatProviderDtoPA extends CommandResultExtensionDtoPA {


	public static final AddReportExportFormatProviderDtoPA INSTANCE = GWT.create(AddReportExportFormatProviderDtoPA.class);


	/* Properties */
	public ValueProvider<AddReportExportFormatProviderDto,String> description();
	public ValueProvider<AddReportExportFormatProviderDto,String> icon();
	public ValueProvider<AddReportExportFormatProviderDto,String> outputFormat();
	public ValueProvider<AddReportExportFormatProviderDto,List<Long>> parentIdWhitelist();
	public ValueProvider<AddReportExportFormatProviderDto,List<String>> parentKeyWhitelist();
	public ValueProvider<AddReportExportFormatProviderDto,List<Long>> reportIdWhitelist();
	public ValueProvider<AddReportExportFormatProviderDto,List<String>> reportKeyWhitelist();
	public ValueProvider<AddReportExportFormatProviderDto,ReportDto> reportType();
	public ValueProvider<AddReportExportFormatProviderDto,String> title();


}
