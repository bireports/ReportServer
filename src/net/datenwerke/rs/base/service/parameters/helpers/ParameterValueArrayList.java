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
 
 
package net.datenwerke.rs.base.service.parameters.helpers;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

public class ParameterValueArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5028584023075590226L;

	@Override
	public String toString() {
		return toString("\'");
	}
	
	
	public String toString(String quote) {
		if(isEmpty())
			return "";
		
		StringBuilder builder = new StringBuilder();
		
		Iterator<E> it = iterator();
		
		E first = it.next();
		if(first instanceof String)
			builder.append(quote).append(StringEscapeUtils.escapeSql(first.toString())).append(quote);
		else
			builder.append(first.toString());
		
		while(it.hasNext()){
			E val = it.next();
			
			builder.append(", ");
			if(val instanceof String)
				builder.append(quote).append(StringEscapeUtils.escapeSql(val.toString())).append(quote);
			else
				builder.append(StringEscapeUtils.escapeSql(val.toString()));
		}
		
		return builder.toString();
	}
	
}
