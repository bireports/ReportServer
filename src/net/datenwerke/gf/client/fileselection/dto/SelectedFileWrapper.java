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
 
 
package net.datenwerke.gf.client.fileselection.dto;

import net.datenwerke.gxtdto.client.dtomanager.Dto;

public class SelectedFileWrapper extends Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5958665141713618455L;

	private String name;
	private String id;
	private Dto originalDto;
	private String type; 
	
	public SelectedFileWrapper(){
	}
	
	public SelectedFileWrapper(String type, Dto dto, String name) {
		setType(type);
		setOriginalDto(dto);
		setName(name);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Dto getOriginalDto() {
		return originalDto;
	}
	public void setOriginalDto(Dto originalDto) {
		this.originalDto = originalDto;
	}
	
	
}
