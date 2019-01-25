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
 
 
package net.datenwerke.gxtdto.client.servercommunication.exceptions;

import net.datenwerke.gxtdto.client.locale.BaseMessages;

import com.google.gwt.core.shared.GWT;

/**
 * 
 *
 */
public class ServerCallFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8402471906508984817L;
	private String stacktrace;
	
	public ServerCallFailedException() {
		super();
	}
	
	public ServerCallFailedException(String msg) {
		super(msg);
	}
	
	public ServerCallFailedException(String msg, Throwable e) {
		super(msg, e);
	}

	public ServerCallFailedException(Throwable e) {
		super(e.getMessage());
		initCause(e);
	}

	public void setStackTraceAsString(String stacktrace){
		this.stacktrace = stacktrace;
	}
	
	public String getStackTraceAsString(){
		return stacktrace;
	}

	public String getTitle() {
		if(GWT.isClient())
			return BaseMessages.INSTANCE.encounteredError();
		return "Encountered an error";
	}
	
}
