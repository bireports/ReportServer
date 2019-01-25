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
 
 
package net.datenwerke.eximport.im;

import org.hibernate.proxy.HibernateProxy;


/**
 * 
 *
 */
public abstract class ImportItemConfig {

	protected final String id;
	protected final ImportMode importMode;
	protected final Object referenceObject;
	
	/**
	 * 
	 * @param id configured by this config
	 */
	public ImportItemConfig(String id){
		this.id = id;
		this.importMode = ImportMode.CREATE;
		this.referenceObject = null;
	}
	
	public ImportItemConfig(String id, ImportMode importMode){
		this.id = id;
		this.importMode = importMode;
		this.referenceObject = null;
		if(importMode != ImportMode.CREATE && importMode != ImportMode.IGNORE)
			throw new IllegalArgumentException("Import mode has to be create or ignore if no reference object is submitted.");
	}
	
	public ImportItemConfig(String id, ImportMode importMode, Object referenceObject){
		this.id = id;
		this.importMode = importMode;
		
		if(referenceObject instanceof HibernateProxy)
			referenceObject = ((HibernateProxy)referenceObject).getHibernateLazyInitializer().getImplementation();
		this.referenceObject = referenceObject;
	}
	
	public String getId(){
		return id;
	}
	
	public Object getReferenceObject(){
		return referenceObject;
	}
	
	public ImportMode getImportMode(){
		return importMode;
	}
	
	@Override
	public int hashCode() {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ImportItemConfig))
			return false;
		
		if(null == getId())
			return super.equals(obj);
		
		return getId().equals(((ImportItemConfig)obj).getId());
	}
	
}
