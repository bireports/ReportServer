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
 
 
package net.datenwerke.gf.client.upload.dto;

import java.io.Serializable;

public class FileToUpload implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4738338258610882160L;
	
	private String name;
	private long size;
	private String b64Data;
	
	public FileToUpload() {
		name = null;
		size = 0;
		b64Data = null;
	}
	
	public FileToUpload(String name, long size, String b64Data) {
		this.name = name;
		this.size = size;
		this.b64Data = b64Data;
	}
	public String getName() {
		return name;
	}
	public long getSize() {
		return size;
	}
	public String getB64Data() {
		return b64Data;
	}
}