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
 
 
package net.datenwerke.gxtdto.client.clipboard.processor;

import net.datenwerke.gxtdto.client.clipboard.ClipboardDtoItem;
import net.datenwerke.gxtdto.client.clipboard.ClipboardDtoListItem;
import net.datenwerke.gxtdto.client.clipboard.ClipboardItem;

public abstract class ClipboardDtoPasteProcessor implements ClipboardPasteProcessor {

	private Class<?> type;

	public ClipboardDtoPasteProcessor(Class<?> type){
		this.type = type;
	}
	
	@Override
	public void paste(ClipboardItem item) {
		if(item instanceof ClipboardDtoItem){
			ClipboardDtoItem dtoItem = (ClipboardDtoItem) item;
			if(null != dtoItem.getDto()){
				Class<?> dtoType = dtoItem.getDto().getClass();
				while(null != dtoType){
					if(dtoType.equals(type)){
						doPaste((ClipboardDtoItem)dtoItem);
						break;
					}
					dtoType = dtoType.getSuperclass();
				}
			}
		} else  if(item instanceof ClipboardDtoListItem){
			ClipboardDtoListItem listItem = (ClipboardDtoListItem) item;
			if(null != listItem.getList() && ! listItem.getList().isEmpty()){
				Class<?> dtoType = listItem.getType();
				while(null != dtoType){
					if(dtoType.equals(type)){
						doPaste(listItem);
						break;
					}
					dtoType = dtoType.getSuperclass();
				}
			}
		}
		
	}

	protected void doPaste(ClipboardDtoListItem listItem) {
	}

	protected void doPaste(ClipboardDtoItem dtoItem){
	}

}
