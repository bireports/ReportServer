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
 
 
package net.datenwerke.rs.saiku.service.hooker;

import java.util.Map;

import javax.inject.Provider;

import com.google.inject.Inject;

import net.datenwerke.rs.saiku.service.hooks.SaikuQueryParameterAdapterHook;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.rs.utils.juel.SimpleJuel;

public class SaikuJuelParameterAdapter implements SaikuQueryParameterAdapterHook {

	private final Provider<SimpleJuel> juelProvider;
	
	@Inject
	public SaikuJuelParameterAdapter(Provider<SimpleJuel> juelProvider) {
		this.juelProvider = juelProvider;
	}



	@Override
	public void adaptParameters(Map<String, String> parameters, SaikuReport report) {
		for(String key : parameters.keySet()){
			String value = parameters.get(key);
			
			SimpleJuel juel = juelProvider.get();
			juel.addReplacement("key", key);
			juel.addReplacement("value", value);
			
			String adapted = juel.parse(value);
			
			parameters.put(key, adapted);
		}
	}

}
