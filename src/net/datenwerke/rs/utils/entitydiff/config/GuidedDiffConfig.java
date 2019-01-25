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
 
 
package net.datenwerke.rs.utils.entitydiff.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.rs.utils.entitydiff.annotations.EntityDiffGuide;

public class GuidedDiffConfig implements EntityDiffConfig {

	private final EntityDiffGuide guide;
	private final String guideName;
	
	private final Map<Class<?>, List<String>> whitelist;
	private final Map<Class<?>, List<String>> blacklist;

	public GuidedDiffConfig(Class<?> type, EntityDiffGuide guide, String guideName) {
		this.guide = guide;
		this.guideName = guideName;
		
		whitelist = new HashMap<Class<?>, List<String>>();
		if(guide.whitelist().length > 0)
			whitelist.put(type, Arrays.asList(guide.whitelist()));
		
		blacklist = new HashMap<Class<?>, List<String>>();
		if(guide.blacklist().length > 0)
			blacklist.put(type, Arrays.asList(guide.blacklist()));
	}

	@Override
	public boolean ignoreId() {
		return guide.ignoreId();
	}

	@Override
	public boolean ignoreVersion() {
		return guide.ignoreVersion();
	}

	@Override
	public Map<Class<?>, List<String>> getFieldsToCompareWhiteList() {
		return this.whitelist;
	}

	@Override
	public Map<Class<?>, List<String>> getFieldsToCompareBlackList() {
		return this.blacklist;
	}

	public String getGuideName() {
		return guideName;
	}

}
