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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl;

import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCSeparator;

import com.sencha.gxt.widget.core.client.container.MarginData;

public class SFFCSeparatorImpl implements SFFCSeparator {

	private final TYPE type;
	private final String text;
	private final MarginData layoutData;
	
	public SFFCSeparatorImpl(TYPE type){
		this(type, null, null);
	}
	
	public SFFCSeparatorImpl(TYPE type, String text){
		this(type, text, null);
	}
	
	public SFFCSeparatorImpl(TYPE type, String text, MarginData layoutData) {
		this.type = type;
		this.text = text;
		this.layoutData = layoutData;
	}

	@Override
	public MarginData getMargins() {
		return layoutData;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public TYPE getType() {
		return type;
	}

}
