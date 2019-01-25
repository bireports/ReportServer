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
 
 
package net.datenwerke.rs.saiku.server.rest.objects.acl.enumeration;

import java.util.List;

/**
 * The list of the available access methods.
 * The NONE access type is the lowest, the GRANT is the highest . 
 * Every method implies the preceding 
 *
 */
public enum AclMethod {
	NONE,
	READ,
	WRITE,
	GRANT;
	
	/**
	 * calculates the higher (more privileges) of two access methods
	 * @param method1 
	 * @param method2
	 * @return
	 */
	public static AclMethod max(AclMethod method1, AclMethod method2 ) {
		if ( method1 == null ) {
			if ( method2 == null ) {
				throw new RuntimeException("cannot compare two null objects");
			}
			return method2;
		}
		if ( toInt(method1) > toInt(method2) ) return method1;
		return method2;
	}
	/**
	 * Calculates the lower ( less privileges ) of two access methods
	 * @param method1
	 * @param method2
	 * @return
	 */
	public static AclMethod min(AclMethod method1, AclMethod method2 ) {
		if ( method1 == null ) {
			if ( method2 == null ) {
				throw new RuntimeException("cannot compare two null objects");
			}
			return method2;
		}
		if ( toInt(method1) < toInt(method2) ) return method1;
		return method2;
	}
	/**
	 * Calculates the higher ( more privileges ) of a list of access methods
	 * @param methods
	 * @return
	 */
	public static AclMethod max(List<AclMethod> methods) {
		if ( methods != null && methods.size() > 0 ) {
			AclMethod method = methods.get(0);
			for ( int i = 1; i < methods.size() ; ++ i ) {
				method = AclMethod.max(methods.get(i), method);
			}
	
			return method;
		} 
		return NONE;
	}

	/**
	 * Calculates the lowest (less privileges ) of a list of access methods
	 * @param methods
	 * @return
	 */
	public static AclMethod min(List<AclMethod> methods) {
		if ( methods != null && methods.size() > 0 ) {
			AclMethod method = methods.get(0);
			for ( int i = 1; i < methods.size() ; ++ i ) {
				method = AclMethod.min(methods.get(i), method);
			}
	
			return method;
		} 
		return NONE;
	}
	
	/**
	 * Associates an integer to every access methods.
	 * <ul>
	 * <li> {@link AclMethod#NONE} : 0 </li> 
	 * <li> {@link AclMethod#READ} : 1</li>
	 * <li> {@link AclMethod#WRITE} : 2</li>
	 * <li> {@link AclMethod#GRANT} : 3</li>
	 * </ul>
	 * @param method
	 * @return
	 */
	public static int toInt(AclMethod method ){
		switch (method) {
			case NONE: return 0;
			case READ: return 1;
			case WRITE: return 2;
			case GRANT: return 3;
		}
		return -1; // this shall never happen
	}
	
}
