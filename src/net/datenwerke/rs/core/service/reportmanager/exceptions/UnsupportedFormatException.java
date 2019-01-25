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
 
 
package net.datenwerke.rs.core.service.reportmanager.exceptions;

import java.util.Arrays;


public class UnsupportedFormatException extends ReportExecutorRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5665809762679791270L;

	public UnsupportedFormatException(String[] allowedFormats, String requestedFormat){
		super("Unsupported format " + requestedFormat.toString() + " encountered. The following formats are supported: " + Arrays.toString(allowedFormats)); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
