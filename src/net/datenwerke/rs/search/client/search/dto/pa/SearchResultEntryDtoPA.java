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
 
 
package net.datenwerke.rs.search.client.search.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.rs.search.client.search.dto.SearchResultEntryDto;
import net.datenwerke.rs.search.client.search.dto.SearchResultTagDto;
import net.datenwerke.rs.search.client.search.dto.decorator.SearchResultEntryDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.search.service.search.results.SearchResultEntry.class)
public interface SearchResultEntryDtoPA extends PropertyAccess<SearchResultEntryDto> {


	public static final SearchResultEntryDtoPA INSTANCE = GWT.create(SearchResultEntryDtoPA.class);

	@Path("dtoId")
	public ModelKeyProvider<SearchResultEntryDto> dtoId();

	/* Properties */
	public ValueProvider<SearchResultEntryDto,String> description();
	public ValueProvider<SearchResultEntryDto,String> iconSmall();
	public ValueProvider<SearchResultEntryDto,Long> id();
	public ValueProvider<SearchResultEntryDto,Date> lastModified();
	public ValueProvider<SearchResultEntryDto,List<HistoryLinkDto>> links();
	public ValueProvider<SearchResultEntryDto,Long> objectId();
	public ValueProvider<SearchResultEntryDto,Set<SearchResultTagDto>> tags();
	public ValueProvider<SearchResultEntryDto,String> title();
	public ValueProvider<SearchResultEntryDto,DwModel> resultObject();


}
