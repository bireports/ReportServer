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

import com.google.gwt.core.client.JavaScriptObject;

public class SendToJsonConfig extends JavaScriptObject {
	protected SendToJsonConfig(){}

	public final native int getWidth() /*-{ return null == this.width ? 640 : this.width; }-*/;
	public final native int getHeight() /*-{ return null == this.height ? 480 : this.height; }-*/;
	public final native JavaScriptObject getForm() /*-{ return this.form }-*/;
}