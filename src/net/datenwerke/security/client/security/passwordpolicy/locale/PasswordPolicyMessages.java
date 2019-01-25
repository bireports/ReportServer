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
 
 
package net.datenwerke.security.client.security.passwordpolicy.locale;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface PasswordPolicyMessages extends Messages{
	
	public static final PasswordPolicyMessages INSTANCE = GWT.create(PasswordPolicyMessages.class);
	
	String accountLockedMessage(Date date);
	String accountLockedHeading();
	String passwordExpiration();
	String passwordExpirationWarning(int expiresIn);
	String passwordExpired();
	String activateButtonLabel();
	String activateButtonTooltip();
	String accountInhibitedMessage();
	String accountInhibitedHeading();
}
