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
 
 
package net.datenwerke.rs.xml;

import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.xml.XMLUtilsService;
import net.datenwerke.rs.utils.xml.XMLUtilsServiceImpl;
import net.datenwerke.rs.utils.xml.annotations.DisableXMLValidation;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;

public class XmlRsUtilsModule extends AbstractModule {

	public static final String DISABLE_XML_VALIDATION_PROPERTY = "xmlutils.disablevalidation";
	
	@Override
	protected void configure() {
		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
		
		bind(XMLUtilsService.class).to(XMLUtilsServiceImpl.class).in(Scopes.SINGLETON);
	}
	
	@Provides @Inject @DisableXMLValidation
	public boolean providerDisableXMLValidation(ConfigService configService){
		return configService.getConfigFailsafe("misc/misc.cf").getBoolean(DISABLE_XML_VALIDATION_PROPERTY, false);
		
	}

}
