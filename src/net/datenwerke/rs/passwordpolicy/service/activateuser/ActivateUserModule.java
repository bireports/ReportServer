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
 
 
package net.datenwerke.rs.passwordpolicy.service.activateuser;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;

public class ActivateUserModule extends AbstractReportServerModule{
	
	public static final String CONFIG_FILE = "security/activateuser.cf"; //$NON-NLS-1$
	public static final String PROPERTY_EMAIL_SUBJECT = "security.activateaccount.email.subject";
	public static final String PROPERTY_EMAIL_TEXT = "security.activateaccount.email.text";

	
	@Override
	protected void configure() {
		/* bind service */
		bind(ActivateUserService.class).to(ActivateUserServiceImpl.class);
	}
	
}
