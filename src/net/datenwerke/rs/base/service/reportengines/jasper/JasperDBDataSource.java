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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper;

import java.sql.Connection;

import org.apache.commons.lang.NotImplementedException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * 
 *
 */
public class JasperDBDataSource implements JRDataSource {

	private Connection connection;
	
	public JasperDBDataSource(Connection connection) {
		super();
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Object getFieldValue(JRField jrField) throws JRException {
		throw new NotImplementedException("This object cannot be used like other JRDataSource objects. Sorry for the inconvenience."); //$NON-NLS-1$
	}

	public boolean next() throws JRException {
		throw new NotImplementedException("This object cannot be used like other JRDataSource objects. Sorry for the inconvenience."); //$NON-NLS-1$
	}

}
