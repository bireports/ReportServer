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
 
 
package net.datenwerke.security.client.security;

import net.datenwerke.security.client.security.dto.GenericSecurityTargetContainer;
import net.datenwerke.security.client.security.dto.RightDto;

public interface SecurityUIService {

	public static final String REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED = "REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED"; //$NON-NLS-1$
	
	void setGenericSecurityContainer(
			GenericSecurityTargetContainer genericSecurityContainer);

	public boolean hasRight(Class<? extends GenericTargetIdentifier> identifier, Class<? extends RightDto> toCheck);

	boolean isSuperUser();
}
