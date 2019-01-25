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
 
 
package net.datenwerke.gxtdto.client.objectinformation.hooks;

import java.util.Date;

import com.google.gwt.resources.client.ImageResource;


public abstract class ObjectInfoKeyInfoProviderImpl<A> implements  ObjectInfoKeyInfoProvider {

	@Override
	final public String getName(Object object){
		return doGetName((A)object);
	}
	
	protected abstract String doGetName(A object);

	@Override
	final public String getDescription(Object object){
		return doGetDescription((A)object);
	}

	protected abstract String doGetDescription(A object);
	
	@Override
	final public String getType(Object object){
		return doGetType((A)object);
	}
	
	protected abstract String doGetType(A object);
	
	@Override
	final public Date getLastUpdatedOn(Object object){
		return doGetLastUpdatedOn((A)object);
	}
	
	protected abstract Date doGetLastUpdatedOn(A object);
	
	@Override
	final public Date getCreatedOn(Object object){
		return doGetCreatedOn((A)object);
	}
	
	protected abstract Date doGetCreatedOn(A object);
	
	
	@Override
	public ImageResource getIconSmall(Object object) {
		return doGetIconSmall((A)object);
	}

	abstract protected ImageResource doGetIconSmall(A object);
}
