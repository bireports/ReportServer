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
 
 
package net.datenwerke.treedb.service.treedb.exceptions;


public class MultipleRootException extends TreeDBRuntimeException {

	public MultipleRootException(){
		super("Tried to add second root to TreeDBTree that does not allow multiple roots"); //$NON-NLS-1$
	}
	
	public MultipleRootException(Class<?> treeClass){
		super("Tried to add second root to TreeDBTree (" + treeClass.getSimpleName() + ") that does not allow multiple roots"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
