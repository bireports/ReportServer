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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.lists;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.datenwerke.gxtdto.client.utils.StringEscapeUtils;

public class SFFCEnumList extends SFFCStaticList<Enum> {
	
	private HashMap<String, Enum> values;
	
	public SFFCEnumList(Class<? extends Enum> enumType) {
		this(enumType, false);
	}
	
	public SFFCEnumList(Class<? extends Enum> enumType, boolean nullable) {
		this.values = new LinkedHashMap<String, Enum>();
		
		if(nullable)
			values.put("&nbsp;", null);
		
		for(Enum e : enumType.getEnumConstants())
			values.put(StringEscapeUtils.unescapeHTML(e.toString()), e);
	}
	
	
	@Override
	public Map<String, Enum> getValues() {
		return this.values;
	}
	
	@Override
	public net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.lists.SFFCStaticList.TYPE getType() {
		return TYPE.Dropdown;
	}
	
	@Override
	public boolean allowTextSelection() {
		return false;
	}
}
