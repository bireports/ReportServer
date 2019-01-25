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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.inject.Inject;

import net.datenwerke.gf.service.config.ClientConfigExposerHook;
import net.datenwerke.rs.saiku.service.saiku.SaikuModule;
import net.datenwerke.rs.utils.config.ConfigService;

public class SaikuClientConfigExposerHooker implements ClientConfigExposerHook {

	private final ConfigService configService;
	
	@Inject
	public SaikuClientConfigExposerHooker(ConfigService configService) {
		this.configService = configService;
	}



	@Override
	public Map<String, String> exposeConfig(String identifier) {
		if(! "saiku.properties".equals(identifier))
			return null;
		
		Map<String,String> map = new HashMap<>();
		
		Configuration c = configService.getConfigFailsafe(SaikuModule.CONFIG_FILE);
		if(c instanceof XMLConfiguration){
			XMLConfiguration config = (XMLConfiguration) c;
			List<HierarchicalConfiguration> subconfigs = config.configurationsAt("saiku.settings.property");
			if(null != subconfigs){
				for(HierarchicalConfiguration property : subconfigs){
					String key = property.getString("[@key]");
					String value = property.getString("[@value]");
					
					map.put(key, value);
				}
			}
		}
		
		
		return map;
	}

}
