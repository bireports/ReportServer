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
 
 
package net.datenwerke.rs.utils.misc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class PropertiesUtils {

    /**
     * Load a properties file from the classpath
     * @param name
     * @return Properties
     * @throws Exception
     */
    public Configuration load(String name) throws ConfigurationException {
        return new PropertiesConfiguration(name);
    }

    /**
     * Load a Properties File
     * 
     * @param file
     * @return Properties
     * @throws IOException
     * @throws ConfigurationException 
     */
    public Configuration load(File file) throws ConfigurationException {
    	return new PropertiesConfiguration(file);
    }

}
