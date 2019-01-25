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
 
 
package net.datenwerke.rs.base.service.dbhelper.querybuilder;

import java.util.Map;

import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValueImpl;

public class QueryReplacementHelper {

	private int nrRep = 0;
	private int nrAlias = 0;
	
	public String nextReplacement(Map<String, ParameterValue> replacementMap, Object data){
		String rep = "_xx_query_replacement_prefix_" + nrRep++; //$NON-NLS-1$
		if(null == data)
			replacementMap.put(rep, new ParameterValueImpl(rep, null, Object.class));
		else
			replacementMap.put(rep, new ParameterValueImpl(rep, data, data.getClass()));
		
		return "$P{" + rep + "}"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public String nextAlias(){
		return "_xx_rs_" + nrAlias++; //$NON-NLS-1$
	}
}
