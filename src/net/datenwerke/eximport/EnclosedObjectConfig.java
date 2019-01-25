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
 
 
package net.datenwerke.eximport;

import com.google.inject.Inject;

final public class EnclosedObjectConfig {

	@Inject
	private static ExImportIdService idService;
	
	private final Object enclosed;
	
	private String id;
	
	public EnclosedObjectConfig(
		Object enclosed
		){
		
		/* store objects */
		this.enclosed = enclosed;
	}
	
	public Object getEnclosed(){
		return enclosed;
	}
	
	/**
	 * Returns a unique Id 
	 * @return
	 */
	public final String getId(){
		if(null == id){
			id = idService.provideId(enclosed);
			if(null == id)
				throw new IllegalStateException("Could not find id for item " + enclosed.getClass());
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
		if(! (obj instanceof EnclosedObjectConfig))
			return false;
		
		if(null == getId())
			return super.equals(obj);
		
		return getId().equals(((EnclosedObjectConfig)obj).getId());
	}
	
}
