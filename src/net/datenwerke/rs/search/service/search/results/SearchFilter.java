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
 
 
package net.datenwerke.rs.search.service.search.results;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.dtoservices.dtogenerator.annotations.AdditionalField;
import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.rs.search.service.search.results.post.Dto2SearchFilterPost;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.search.client.search.dto",
	createDecorator=true,
	dto2PosoPostProcessors=Dto2SearchFilterPost.class,
	additionalFields = {
		@AdditionalField(name="baseType", type=Dto.class)
	}
)
public class SearchFilter {

	public static final String TAG_BASE_TYPE = "baseType";
	
	@ExposeToClient
	private Set<SearchResultTag> tags = new HashSet<SearchResultTag>();
	
	private Class<?> baseType = Object.class;
	
	@ExposeToClient
	private int limit = 25;
	
	@ExposeToClient
	private int offset;

	public void setTags(Set<SearchResultTag> tags) {
		this.tags = tags;
	}

	public Set<SearchResultTag> getTags() {
		return tags;
	}

	public void setBaseType(Class<?> baseType) {
		this.baseType = baseType;
	}

	public Class<?> getBaseType() {
		return baseType;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
