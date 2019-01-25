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


public class ExpectedException extends ServerCallFailedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4898107548666705902L;
	
	
	public ExpectedException() {
		super();
	}
	
	public ExpectedException(String msg) {
		super(msg);
	}
	
	public ExpectedException(Throwable e) {
		super(e);
	}
	
	public ExpectedException(String msg, Exception e) {
		super(msg);
		initCause(e);
	}

	public String getTitle() {
		if(GWT.isClient())
			return BaseMessages.INSTANCE.encounteredError();
		return "Encountered an error";
	}
	
}
