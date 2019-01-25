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
 
 
package net.datenwerke.gxtdto.client.model;

import java.util.ArrayList;

public class SuccessIndicatorBaseModel implements DwModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511693173829022226L;

	private boolean success;
	private ArrayList<KeyValueBaseModel<String>> data = new ArrayList<KeyValueBaseModel<String>>();

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public ArrayList<KeyValueBaseModel<String>> getData() {
		return data;
	}

	public void setData(ArrayList<KeyValueBaseModel<String>> data) {
		this.data = data;
	}
	
	public void addData(KeyValueBaseModel<String> data) {
		this.data.add(data);
	}
	

}
