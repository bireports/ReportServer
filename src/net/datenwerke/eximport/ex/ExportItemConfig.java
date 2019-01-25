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
 
 
package net.datenwerke.eximport.ex;

import net.datenwerke.eximport.ExImportIdService;

import org.hibernate.proxy.HibernateProxy;

import com.google.inject.Inject;

/**
 * 
 *
 * @param <I>
 */
public abstract class ExportItemConfig<I> {

	@Inject
	protected static ExImportIdService idService;
	
	protected final I item;
	protected String id;
	
	public ExportItemConfig(I item){
		if(item instanceof HibernateProxy)
			item = (I) ((HibernateProxy)item).getHibernateLazyInitializer().getImplementation();
		this.item = item;
	}
	
	public I getItem(){
		return item;
	}
	
	/**
	 * Returns a unique Id 
	 * @return
	 */
	public final String getId(){
		if(null == id){
			id = idService.provideId(item);
			if(null == id)
				throw new IllegalStateException("Could not find id for item " + item.getClass());
		}
		
		return id;
	}
	
	@Override
	public int hashCode() {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ExportItemConfig<?>))
			return false;
		
		if(null == getId())
			return super.equals(obj);
		
		return getId().equals(((ExportItemConfig<?>)obj).getId());
	}
	
}
