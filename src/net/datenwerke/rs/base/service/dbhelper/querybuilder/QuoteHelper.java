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
 
 
package net.datenwerke.rs.base.service.dbhelper.querybuilder;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.rs.base.service.dbhelper.DBHelperModule;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.utils.config.ConfigService;

public class QuoteHelper {

	@Inject
	private static Provider<ConfigService> configService;

	private DatabaseHelper databaseHelper;

	private QuoteMode quoteMode;

	private boolean smartModeQuotes = false;

	private enum QuoteMode{
		ALWAYS,
		NEVER,
		SMART
	}

	public QuoteHelper(DatabaseHelper databaseHelper, QueryBuilder builder) {
		this.databaseHelper = databaseHelper;

		String sQuoteMode = configService.get().getConfigFailsafe(DBHelperModule.CONFIG_FILE).getString(DBHelperModule.QUOTE_MODE, "smart");
		quoteMode = QuoteMode.valueOf(sQuoteMode.toUpperCase());

		if(QuoteMode.SMART.equals(quoteMode)){
			try{ 
				this.smartModeQuotes = builder.getInnerQuery().contains(databaseHelper.getIdentifierQuoteChar());

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public String quoteIdentifier(String identifier) {
		switch(quoteMode){
		case ALWAYS:
			return databaseHelper.getIdentifierQuoteChar() + identifier + databaseHelper.getIdentifierQuoteChar();
		case NEVER:
			return identifier;
		case SMART:
			if(smartModeQuotes || null != identifier && identifier.contains(" ")){
				return databaseHelper.getIdentifierQuoteChar() + identifier + databaseHelper.getIdentifierQuoteChar();
			}else{
				return identifier;	
			}
		}
		return identifier;
	}

}
