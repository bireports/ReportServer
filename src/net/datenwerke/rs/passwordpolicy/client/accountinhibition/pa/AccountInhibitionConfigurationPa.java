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
 
 
package net.datenwerke.rs.passwordpolicy.client.accountinhibition.pa;

import java.util.Date;

import net.datenwerke.rs.passwordpolicy.client.accountinhibition.AccountInhibitionConfiguration;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface AccountInhibitionConfigurationPa extends PropertyAccess<AccountInhibitionConfiguration> {
	
	public static final AccountInhibitionConfigurationPa INSTANCE = GWT.create(AccountInhibitionConfigurationPa.class);
	
	public ValueProvider<AccountInhibitionConfiguration, Boolean> inhibitionState();
	public ValueProvider<AccountInhibitionConfiguration, Date> expirationDate();
	public ValueProvider<AccountInhibitionConfiguration, Long> userId();

}
