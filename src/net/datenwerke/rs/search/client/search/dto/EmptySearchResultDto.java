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
 
 
package net.datenwerke.rs.search.client.search.dto;

import java.util.ArrayList;

import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.rs.search.client.search.locale.SearchMessages;


public class EmptySearchResultDto extends SearchResultEntryDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2917907045257236061L;

	public EmptySearchResultDto(){
		this(SearchMessages.INSTANCE.noResultTitle(), SearchMessages.INSTANCE.noResultDesc(), "exclamation");
	}
	
	public EmptySearchResultDto(String title, String desc, String icon){
		super();
		setTitle(title);
		setDescription(desc);
		setIconSmall(icon);
		setLinks(new ArrayList<HistoryLinkDto>());
	}
}
