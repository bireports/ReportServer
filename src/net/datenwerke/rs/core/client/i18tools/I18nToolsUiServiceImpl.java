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
 
 
package net.datenwerke.rs.core.client.i18tools;

import net.datenwerke.gxtdto.client.i18n.I18nToolsUIService;

import com.google.inject.Singleton;

@Singleton
public class I18nToolsUiServiceImpl implements I18nToolsUIService {

	private String userDecimalSeparator;

	public void setUserDecimalSeparator(String separator) {
		this.userDecimalSeparator = separator;
	}
	
	@Override
	public String getUserDecimalSeparator() {
		return userDecimalSeparator;
	}

	@Override
	public String translateNumberFromUserToSystem(String number) {
		if(null == number)
			return null;
		if(null == userDecimalSeparator)
			return number;
		
		return number.replace(getUserDecimalSeparator(), getSystemDecimalSeparator());
	}
	
	@Override
	public String translateNumberFromSystemToUser(String number) {
		if(null == number)
			return null;
		if(null == userDecimalSeparator)
			return number;
		
		return number.replace(getSystemDecimalSeparator(),getUserDecimalSeparator());
	}

	public String getSystemDecimalSeparator() {
		return ".";
	}

}
