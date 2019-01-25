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

import net.datenwerke.gxtdto.client.clipboard.locale.ClipboardMessages;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.IdedDto;

public class ClipboardDtoItem extends ClipboardItem implements ClipboardItemDescriber {

	/**
	 * 
	 */
	private static final long serialVersionUID = -790924594059629123L;
	
	private Dto dto;
	private Object id;
	private Object additionalInfo;
	
	public ClipboardDtoItem(){
		super();
	}
	
	public ClipboardDtoItem(Dto dto){
		if(dto instanceof IdedDto)
			setId(((IdedDto)dto).getDtoId());
		setType(dto.getClass());
		this.setDto(dto);
	}
	
	public ClipboardDtoItem(Dto dto, Object addInfo){
		this(dto);
		setAdditionalInfo(addInfo);
	}
	
	public Object getId() {
		return id;
	}
	
	public void setId(Object id) {
		this.id = id;
	}


	public void setDto(Dto dto) {
		this.dto = dto;
	}

	public Dto getDto() {
		return dto;
	}

	@Override
	public String describe() {
		if(null != dto)
			return ClipboardMessages.INSTANCE.dtoCopiedToClipboard(dto.toDisplayTitle());
		return "";
	}

	public void setAdditionalInfo(Object additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Object getAdditionalInfo() {
		return additionalInfo;
	}
	
}
