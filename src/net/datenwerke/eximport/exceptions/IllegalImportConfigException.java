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
 
 
package net.datenwerke.eximport.exceptions;


public class IllegalImportConfigException extends ImportException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7159839444159564248L;

	public IllegalImportConfigException(){
		super("Configuration is illegal.");
	}
	
	public IllegalImportConfigException(String msg){
		super(msg);
	}
	
	public IllegalImportConfigException(Throwable cause){
		super("Configuration is illegal.", cause);
	}
	
	public IllegalImportConfigException(String msg, Throwable cause){
		super(msg, cause);
	}

}
