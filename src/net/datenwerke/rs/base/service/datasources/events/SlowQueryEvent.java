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
 
 
package net.datenwerke.rs.base.service.datasources.events;

import java.util.Map;

import net.datenwerke.security.service.eventlogger.DwLoggedEvent;

public class SlowQueryEvent extends DwLoggedEvent {

	public SlowQueryEvent(String statement, long timeInNs, Map<Object, Object> params){
		super(linearize(statement, timeInNs, params));
	}
	
	private static String[] linearize(String statement, long timeInNs, Map<Object, Object> params) {
		String[] props = new String[4 + params.size()*2];
		props[0] = "statement";
		props[1] = statement;
		props[2] = "duration";
		props[3] = String.valueOf(timeInNs);
		for(int i = 0; i < params.size(); i++){
			props[i*2 + 4] = "param" + (i+1);
			try{
				props[i*2 + 5] = params.get(i).toString();
			}catch(Exception e){
				props[i*2 + 5] = "";
			}
		}
		return props;
	}

	@Override
	public String getLoggedAction() {
		return "SLOW_SQL_QUERY";
	}

}
