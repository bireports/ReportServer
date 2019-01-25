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
 
 
package net.datenwerke.rs.terminal.service.terminal.obj;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.terminal.client.terminal.dto"
)
public class CreMessage extends CommandResultExtension {

	@ExposeToClient
	private String title;
	
	@ExposeToClient
	private String windowTitle;
	
	@ExposeToClient
	private String text;
	
	@ExposeToClient(disableHtmlEncode=true)
	private String html;
	
	@ExposeToClient
	private int width;

	@ExposeToClient
	private int height;
	
	public CreMessage(){
		super();
	}
	
	public CreMessage(String title) {
		super();
		this.title = title;
	}
	
	public CreMessage(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}

	public CreMessage(String title, String windowTitle, String text,
			int width, int height) {
		this.title = title;
		this.windowTitle = windowTitle;
		this.text = text;
		this.width = width;
		this.height = height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getHtml() {
		return html;
	}
	
	public void setHtml(String html) {
		this.html = html;
	}
	
	
}
