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
 
 
package net.datenwerke.gf.client.fileselection;

import java.io.Serializable;
import java.util.HashMap;

import net.datenwerke.gxtdto.client.model.DwModel;

public class FileSelectionConfig implements Serializable, DwModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4418656779746516676L;
	
	private String handler;
	private HashMap<String, String> metadata = new HashMap<String, String>();
	
	public FileSelectionConfig(){
	}

	public FileSelectionConfig(String handler) {
		super();
		this.handler = handler;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
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
	

}
