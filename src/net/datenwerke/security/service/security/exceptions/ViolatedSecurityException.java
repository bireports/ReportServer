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
 
 
package net.datenwerke.security.service.security.exceptions;

import org.aopalliance.intercept.MethodInvocation;

public class ViolatedSecurityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6708769716826141524L;

	public ViolatedSecurityException(){
		super();
	}
	
	public ViolatedSecurityException(String message){
		super(message);
	}
	
	public ViolatedSecurityException(Exception e){
		super(e);
	}

	public ViolatedSecurityException(MethodInvocation invocation) {
		this(invocation, ""); //$NON-NLS-1$
	}
	
	public ViolatedSecurityException(MethodInvocation invocation, String reason) {
		super("Violated security. Excecution of method " +  //$NON-NLS-1$
			  invocation.getMethod().getName() + " in class " +  //$NON-NLS-1$
			  invocation.getMethod().getDeclaringClass().getName() + 
			  "(target: " + (null != invocation.getThis() ? invocation.getThis().getClass().getName() : "null") +  //$NON-NLS-1$ //$NON-NLS-2$
			  ") was prohibited. " + reason); //$NON-NLS-1$
	}
}
