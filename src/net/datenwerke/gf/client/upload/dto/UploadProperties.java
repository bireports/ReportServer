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
 
 
package net.datenwerke.gf.client.upload.dto;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Transient;

import net.datenwerke.gf.client.upload.filter.FileUploadFilter;
import net.datenwerke.gxtdto.client.model.DwModel;

public class UploadProperties implements Serializable, DwModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6014101932803686946L;

	
	
	private String handler;
	private String id;
	private HashMap<String, String> metadata = new HashMap<String, String>();
	
	@Transient
	private FileUploadFilter filter = FileUploadFilter.DUMMY_UPLOAD_FILTER;
	
	public UploadProperties(){
	}
	
	public UploadProperties(String id, String handler) {
		this.id = id;
		this.handler = handler;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(HashMap<String, String> metadata) {
		if(null==metadata)
			metadata = new HashMap<String, String>();
		this.metadata = metadata;
	}

	public void addMetadata(String key, String value){
		this.metadata.put(key,value);
	}

	public FileUploadFilter getFilter() {
		return filter;
	}

	public void setFilter(FileUploadFilter filter) {
		this.filter = filter;
	}
	
	
	
}
