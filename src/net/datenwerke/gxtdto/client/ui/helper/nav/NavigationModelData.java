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
 
 
package net.datenwerke.gxtdto.client.ui.helper.nav;

import java.io.Serializable;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.core.client.ValueProvider;


public class NavigationModelData<M> implements Serializable {
	
	public static ValueProvider<NavigationModelData<?>, String> nameValueProvider = new ValueProvider<NavigationModelData<?>, String>() {
		@Override
		public String getValue(NavigationModelData<?> object) {
			return object.getName();
		}

		@Override
		public void setValue(NavigationModelData<?> object, String value) {
			object.setName(value);
		}

		@Override
		public String getPath() {
			return "name";
		}
	}; 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8783945078009624370L;
	
	private M component;
	private ImageResource icon;
	private String name;
	private int id;

	public NavigationModelData(String name, M component){
		this.name = name; 
		setModel(component);
	}

	public NavigationModelData(String name, ImageResource icon, M component){
		this.name = name; 
		setModel(component);
		setIcon(icon);
	}

	
	public NavigationModelData(int id, String name, ImageResource icon, M component){
		this.id = id; 
		this.name = name; 
		setModel(component);
		setIcon(icon);
	}

	public void setModel(M Model) {
		this.component = Model;
	}

	public M getModel() {
		return component;
	}

	public void setIcon(ImageResource icon) {
		this.icon = icon;
	}

	public ImageResource getIcon() {
		return icon;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
}

