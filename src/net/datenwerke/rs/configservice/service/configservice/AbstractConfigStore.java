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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ExpressionEngine;

public abstract class AbstractConfigStore {
	
	protected HierarchicalConfiguration createBaseConfig(){
		XMLConfiguration config = new XMLConfiguration();

		/* initialize and use localeawareexpressionengine */
		ExpressionEngine originalExpressionEngine = config.getExpressionEngine();
		ExpressionEngine localeAwareExpressionEngine = new LocaleAwareExpressionEngine(originalExpressionEngine);
		config.setExpressionEngine(localeAwareExpressionEngine);
		
		/* don't interpret values as lists */
		config.setDelimiterParsingDisabled(true);
		
		return config;
	}

	public abstract HierarchicalConfiguration loadConfig(String identifier) throws ConfigurationException;

}
