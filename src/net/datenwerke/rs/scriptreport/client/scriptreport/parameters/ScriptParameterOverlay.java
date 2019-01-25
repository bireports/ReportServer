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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.parameters;

import com.google.gwt.core.client.JavaScriptObject;

public class ScriptParameterOverlay extends JavaScriptObject {

	protected ScriptParameterOverlay() {
	}

	public final native void setEditable(boolean editable)/*-{
		this.editable = editable;
	}-*/;
	
	public final native void setMandatory(boolean mandatory)/*-{
		this.mandatory = mandatory;
	}-*/;

	public final native void isDefault(boolean isDefault)/*-{
		this.isDefault = isDefault;
	}-*/;
	
	public final native void setName(String name)/*-{
		this.name = name;
	}-*/;
	
	public final native void setKey(String key)/*-{
		this.key = key;
	}-*/;
	
	public final native void setValue(String value)/*-{
		this.value = value;
	}-*/;

	public final native void setDefaultValue(String defaultValue)/*-{
		this.defaultValue = defaultValue;
	}-*/;

}
