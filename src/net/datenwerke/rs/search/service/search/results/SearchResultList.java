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

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import com.sencha.gxt.data.shared.loader.PagingLoadResult;


@GenerateDto(
	dtoPackage="net.datenwerke.rs.search.client.search.dto",
	dtoImplementInterfaces=PagingLoadResult.class,
	createDecorator=true
)
public class SearchResultList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280168035307157321L;

	@ExposeToClient
	@EnclosedEntity
	private List<SearchResultEntry> data;
	
	@ExposeToClient
	private int offset;
	
	@ExposeToClient
	private int totalLength;
	
	@ExposeToClient
	@EnclosedEntity
	private Set<SearchResultTag> tags = new HashSet<SearchResultTag>();

	public SearchResultList(){
	}
	
	public SearchResultList(List<SearchResultEntry> resultList,
			int nrOfResults) {
		this.data = resultList;
		this.totalLength = nrOfResults;
	}

	public List<SearchResultEntry> getData() {
		return data;
	}

	public void setData(List<SearchResultEntry> data) {
		this.data = data;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}

	public void setTags(Set<SearchResultTag> tags) {
		this.tags = tags;
	}

	public Set<SearchResultTag> getTags() {
		return tags;
	}

	public void addTag(SearchResultTag tag){
		tags.add(tag);
	}
	
}
