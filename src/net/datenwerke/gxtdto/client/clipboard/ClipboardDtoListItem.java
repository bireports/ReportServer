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
 
 
package net.datenwerke.gxtdto.client.clipboard;

import java.util.List;

import net.datenwerke.gxtdto.client.clipboard.locale.ClipboardMessages;
import net.datenwerke.gxtdto.client.dtomanager.Dto;

public class ClipboardDtoListItem extends ClipboardItem implements ClipboardItemDescriber {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2781270065293838858L;
	
	private List<? extends Dto> list;
	private Object additionalInfo;

	public ClipboardDtoListItem(){
		super();
	}
	
	public ClipboardDtoListItem(List<? extends Dto> list, Class<?> type){
		setType(type);
		this.setList(list);
	}
	
	public ClipboardDtoListItem(List<? extends Dto> list, Class<?> type, Object addInfo){
		setType(type);
		this.setList(list);
		setAdditionalInfo(addInfo);
	}

	public List<? extends Dto> getList() {
		return list;
	}

	public void setList(List<? extends Dto> list) {
		this.list = list;
	}

	@Override
	public String describe() {
		if(null != list)
			return ClipboardMessages.INSTANCE.dtoListCopiedToClipboard(list.size());
		return "";
	}

	public void setAdditionalInfo(Object additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Object getAdditionalInfo() {
		return additionalInfo;
	}
	
}
