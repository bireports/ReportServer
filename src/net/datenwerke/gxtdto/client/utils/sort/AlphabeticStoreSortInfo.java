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
 
 
package net.datenwerke.gxtdto.client.utils.sort;

import java.util.Comparator;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.FolderDto;
import net.datenwerke.gxtdto.client.dtomanager.stores.TreeDto;

import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;

public class AlphabeticStoreSortInfo<X extends Dto> extends StoreSortInfo<X> {
	
	public AlphabeticStoreSortInfo() {
		this(SortDir.ASC);
	}
	
	public AlphabeticStoreSortInfo(SortDir dir) {
		super(new Comparator<X>() {
			@Override
			public int compare(X o1, X o2) {
				if(o1 instanceof TreeDto && o2 instanceof TreeDto){
					if(o1 instanceof FolderDto && ! (o2 instanceof FolderDto) )
						return -1;
					else if(o2 instanceof FolderDto && ! (o1 instanceof FolderDto) )
						return 1;
					
					String o1Title = o1.toDisplayTitle();
					String o2Title = o2.toDisplayTitle();
					
					if(null != o1Title && null != o2Title)
						return o1Title.compareToIgnoreCase(o2Title);
					if(null == o1Title && null == o2Title)
						return 0;
					if(null == o1Title)
						return 1;
					else
						return -1;
				}
				return 0;
			}
		}, dir);
	}
	
}
