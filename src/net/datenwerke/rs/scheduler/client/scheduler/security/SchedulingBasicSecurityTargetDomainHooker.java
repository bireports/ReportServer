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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.security;

import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;

import com.google.gwt.resources.client.ImageResource;

public class SchedulingBasicSecurityTargetDomainHooker implements
		GenericSecurityViewDomainHook {

	public ImageResource genericSecurityViewDomainHook_getIcon() {
		return BaseIcon.CLOCK_O.toImageResource();
	}

	public String genericSecurityViewDomainHook_getName() {
		return SchedulerMessages.INSTANCE.schedulerBasicHeading();
	}
	
	public String genericSecurityViewDomainHook_getDescription() {
		return SchedulerMessages.INSTANCE.schedulerBasicDescription();
	}

	public GenericTargetIdentifier genericSecurityViewDomainHook_getTargetId() {
		return new SchedulingBasicGenericTargetIdentifier();
	}

}
