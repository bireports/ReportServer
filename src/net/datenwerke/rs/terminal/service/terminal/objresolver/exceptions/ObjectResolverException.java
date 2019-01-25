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
 
 
package net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions;

import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;

public class ObjectResolverException extends TerminalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3333418348907883991L;

	public ObjectResolverException(){
		super();
	}
	
	public ObjectResolverException(String msg) {
		super(msg);
	}

	public ObjectResolverException(Throwable e) {
		super(e);
	}
	
	public ObjectResolverException(String msg, Throwable e) {
		super(msg, e);
	}
}
