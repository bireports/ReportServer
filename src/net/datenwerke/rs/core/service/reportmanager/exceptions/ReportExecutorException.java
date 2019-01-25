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

import net.datenwerke.rs.core.service.reportmanager.locale.ReportManagerMessages;

public class ReportExecutorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3738087665438361986L;

	public ReportExecutorException(String msg){
		super(msg);
	}
	
	public ReportExecutorException(Throwable cause){
		super(ReportManagerMessages.INSTANCE.exceptionReportCouldNotBeExecuted(null != cause ? cause.getMessage() : ""), cause);
	}
	
	public ReportExecutorException(String msg, Throwable cause){
		super(msg, cause);
	}
}
