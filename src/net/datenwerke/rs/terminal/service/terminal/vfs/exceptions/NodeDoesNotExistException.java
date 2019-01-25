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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.exceptions;

import net.datenwerke.rs.terminal.service.terminal.locale.TerminalMessages;

public class NodeDoesNotExistException extends VFSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7017978184337802595L;

	public NodeDoesNotExistException(){
		super(TerminalMessages.INSTANCE.nodeDoesNotExistException());
	}
	
	public NodeDoesNotExistException(String msg){
		super(msg);
	}
}
