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
 
 
package net.datenwerke.security.client.security.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.datenwerke.security.client.security.GenericTargetIdentifier;

public class GenericSecurityTargetContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7234701403435513124L;

	
	private Map<String, Collection<RightDto>> availableRightsPerTarget = new HashMap<String, Collection<RightDto>>();
	
	public void addRight(GenericTargetIdentifier targetIdentifier, RightDto right){
		String key = targetIdentifier.getClass().getName();
		if(! availableRightsPerTarget.containsKey(key))
			availableRightsPerTarget.put(key, new HashSet<RightDto>());
		availableRightsPerTarget.get(key).add(right);
	}

	public Collection<RightDto> getRights(Class<? extends GenericTargetIdentifier> targetIdentifier) {
		String key = targetIdentifier.getName();
		if(! availableRightsPerTarget.containsKey(key))
			return new HashSet<RightDto>();
		return availableRightsPerTarget.get(key);
	}
}
