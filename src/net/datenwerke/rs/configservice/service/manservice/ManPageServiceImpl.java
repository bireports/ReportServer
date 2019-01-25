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
 
 
package net.datenwerke.rs.configservice.service.manservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.annotations.DefaultLocale;
import net.datenwerke.rs.utils.man.ManPageNotFoundException;
import net.datenwerke.rs.utils.man.ManPageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ManPageServiceImpl implements ManPageService{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final Map<String, String> cache = new ConcurrentHashMap<String, String>(); 
	
	private final TerminalService terminalService;

	private final LocalizationServiceImpl localizationService;

	private final Provider<String> defaultLocale;
	
	
	@Inject
	public ManPageServiceImpl(
		TerminalService terminalService, 
		LocalizationServiceImpl localizationService, 
		@DefaultLocale Provider<String> defaultLocale
		) {

		/* store objects */
		this.terminalService = terminalService;
		this.localizationService = localizationService;
		this.defaultLocale = defaultLocale;
	}

	

	@Override
	public String getManPageFailsafe(String identifier) {
		try{
			return getManPage(identifier);
		}catch(Exception e){
			logger.warn( "Manpage " + identifier + " could not be loaded.");
			return "";
		}
	}
	
	@Override
	public String getManPage(String identifier) {
		return getManPage(identifier, true);
	}
	
	@Override
	public String getManPage(String identifier, boolean useCache) {
		identifier = identifier.trim();
		if(identifier.startsWith("/"))
			throw new ManPageNotFoundException("Could not find manpage for " + identifier + ". Identifier may not start with /.");
		
		if(identifier.contains(".."))
			throw new ManPageNotFoundException("Could not find manpage for " + identifier + ". Identifier may not contain '..'.");
		
		
		try {
			if(useCache && cache.containsKey(identifier))
				return cache.get(identifier);
			
			String currentUserLocale = localizationService.getLocale().getLanguage();
			String[] locales = new String[]{currentUserLocale, defaultLocale.get(), ""};
			
			Object object = null;
			for(String loc : locales) {
				object = terminalService.getObjectByLocation("/fileserver/doc/" + identifier + (loc.isEmpty() ? "" : "." + loc) + ".man", false);
				if(null != object) {
					break;
				}
			}
			
			if(null == object || ! (object instanceof FileServerFile))
				throw new ManPageNotFoundException("Could not find manpage for " + identifier);
			
			FileServerFile file = (FileServerFile) object;
			
			byte[] data = file.getData();
			if(null == data)
				throw new ManPageNotFoundException("manpage file is empty " + identifier);
			String strData = new String(data);
			
			cache.put(identifier, strData);
			
			return strData;
		} catch (ObjectResolverException e) {
			throw new ManPageNotFoundException("Could not find manpage for " + identifier, e);
		}
	}

	@Override
	public void clearCache(String identifier) {
		cache.remove(identifier);
	}
	
	@Override
	public void clearCache() {
		cache.clear();
	}
}
