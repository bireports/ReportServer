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
 
 
package net.datenwerke.eximport.im;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 */
public class ImportResult {

	private final String name;
	private final String description;
	private final Date date;
	private final Map<String, Object> importedObjects;
	private final Set<String> ignoredReferencesWithNoConfig;

	public ImportResult(
			String name, 
			String description, 
			Date date,
			Map<String, Object> importedObjects,
			Set<String> ignoredReferencesWithNoConfig) {
		super();
		
		this.name = name;
		this.description = description;
		this.date = date;
		this.importedObjects = importedObjects;
		this.ignoredReferencesWithNoConfig = ignoredReferencesWithNoConfig;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public Map<String, Object> getImportedObjects() {
		return importedObjects;
	}
	
	public Set<String> getIgnoredReferencesWithNoConfig(){
		return ignoredReferencesWithNoConfig;
	}
	
}
