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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.ui;

import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.safehtml.shared.SafeHtml;

class DadgetProcessorModel {
	private String title;
	private String description;
	private BaseIcon icon;
	private DadgetProcessorHook processor;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SafeHtml getIcon() {
		return icon.toSafeHtml(3);
	}
	public void setIcon(BaseIcon icon) {
		this.icon = icon;
	}
	public void setIcon(SafeHtml icon){
		
	}
	public DadgetProcessorHook getProcessor() {
		return processor;
	}
	public void setProcessor(DadgetProcessorHook processor) {
		this.processor = processor;
	}
}