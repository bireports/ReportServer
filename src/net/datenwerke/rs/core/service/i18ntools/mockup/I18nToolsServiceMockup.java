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
 
 
package net.datenwerke.rs.core.service.i18ntools.mockup;

import java.text.NumberFormat;

import net.datenwerke.rs.core.service.i18ntools.FormatPatterns;
import net.datenwerke.rs.core.service.i18ntools.I18nToolsService;
import net.datenwerke.security.service.usermanager.entities.User;

public class I18nToolsServiceMockup implements I18nToolsService {

	@Override
	public String getUserDecimalSeparator(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserDecimalSeparator(User u, String separator) {
		// TODO Auto-generated method stub

	}

	@Override
	public NumberFormat getUserNumberFormatter(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberFormat getUserNumberFormatter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberFormat getSystemNumberFormatter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserDecimalSeparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSystemDecimalSeparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String translateNumberFromUserToSystem(String fe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String translateNumberFromSystemToUser(String fe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String translateNumberFromSystemToUser(String valueOf, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateSystemNumber(String number) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDefaultDateFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FormatPatterns getFormatPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

}
