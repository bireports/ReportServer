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
 
 
package net.datenwerke.rs.saiku.service.saiku;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.datenwerke.rs.utils.config.ConfigService;

public class SaikuServiceImpl implements SaikuService {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final ConfigService configService;

	@Inject
	public SaikuServiceImpl(
		ConfigService configService
		){
		this.configService = configService;
		
		logger.info("loaded saiku service");
	}
	
	@Override
	public long getContextMaxSize(){
		Configuration cf = configService.getConfigFailsafe(SaikuModule.CONFIG_FILE);
		return cf.getLong("saiku.context.maxsize", 20);
	}
	
	@Override
	public long getContextExpiresAfter(){
		Configuration cf = configService.getConfigFailsafe(SaikuModule.CONFIG_FILE);
		return cf.getLong("saiku.context.expiresafter", 60);
	}
	
}
