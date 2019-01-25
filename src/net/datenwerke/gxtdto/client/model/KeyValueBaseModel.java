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
 
 
package net.datenwerke.gxtdto.client.model;


public class KeyValueBaseModel<O> implements DwModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3515445093119027017L;

	private String key;
	private O value;
	
	public KeyValueBaseModel(){
	}

	public KeyValueBaseModel(String key, O value) {
		this.key = key;
		this.value = value;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	
	public void setValue(O value) {
		this.value = value;
	}
	public O getValue() {
		return value;
	}
	
	
	
}
