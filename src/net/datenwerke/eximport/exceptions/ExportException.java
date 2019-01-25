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
 
 
package net.datenwerke.eximport.exceptions;

public class ExportException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5943762831800696410L;

	public ExportException(){
		super("An error occured during export.");
	}
	
	public ExportException(String msg){
		super(msg);
	}
	
	public ExportException(Throwable cause){
		super("An error occured during export: " + cause.getMessage(), cause);
	}
	
	public ExportException(String msg, Throwable cause){
		super(msg, cause);
	}
}
