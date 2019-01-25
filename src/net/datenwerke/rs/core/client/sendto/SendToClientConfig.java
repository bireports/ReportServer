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
 
 
package net.datenwerke.rs.core.client.sendto;

import net.datenwerke.gxtdto.client.dtomanager.Dto;

public class SendToClientConfig extends Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426742554314661101L;

	private String id;
	private String title;
	private String form;
	private String icon;
	private boolean supportsScheduling;
	private boolean selectFormat;
	
	public SendToClientConfig() {
	}

	public SendToClientConfig(String title) {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIcon() {
		return icon;
	}
	public boolean isSelectFormat() {
		return selectFormat;
	}
	public void setSelectFormat(boolean selectFormat) {
		this.selectFormat = selectFormat;
	}
	public boolean isSupportsScheduling() {
		return supportsScheduling;
	}
	public void setSupportsScheduling(boolean supportsScheduling) {
		this.supportsScheduling = supportsScheduling;
	}
	
	
	
}
