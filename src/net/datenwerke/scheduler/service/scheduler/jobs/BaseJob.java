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
 
 
package net.datenwerke.scheduler.service.scheduler.jobs;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.BaseProperty;

import org.apache.commons.io.IOUtils;

@MappedSuperclass
public abstract class BaseJob extends AbstractJob {

	@EnclosedEntity
	@OneToMany(cascade=CascadeType.ALL)
	private Set<BaseProperty> baseProperties = new HashSet<BaseProperty>();

	public void setBaseProperties(Set<BaseProperty> baseProperties) {
		this.baseProperties = baseProperties;
	}

	public Set<BaseProperty> getBaseProperties() {
		return baseProperties;
	}
	
	public void setProperty(BaseProperty property){
		if(null == property || null == property.getKey())
			throw new IllegalArgumentException();
		
		if(isProperty(property.getKey()))
			getProperty(property.getKey()).setValue(property.getValue());
		else
			baseProperties.add(property);
	}
	
	public void setProperty(String key, String value){
		if(null == key)
			throw new IllegalArgumentException();
		
		if(isProperty(key))
			getProperty(key).setValue(value);
		else
			baseProperties.add(new BaseProperty(key, value));
	}
	
	public boolean isProperty(String key){
		return null != getProperty(key);
	}
	
	public BaseProperty getProperty(String key){
		if(null == key)
			return null;
		for(BaseProperty prop : baseProperties)
			if(key.equals(prop.getKey()))
				return prop;
		return null;
	}
	
	public boolean hasProperty(String key) {
		return null != getProperty(key);
	}
	
	public void setComplexProperty(String key, Object bean){
		String data = null;
		
		if(null != bean){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLEncoder e = new XMLEncoder(out);
			e.writeObject(bean);
			e.close();
			data = out.toString();
		}
		
		if(isProperty(key))
			getProperty(key).setValue(data);
		else
			baseProperties.add(new BaseProperty(key, data));
	}
	
	public <B> B getComplexProperty(String key){
		BaseProperty prop = getProperty(key);
		if(null == prop)
			return null;
		String data = prop.getValue();
		
		XMLDecoder dec = new XMLDecoder(IOUtils.toInputStream(data));
		return (B) dec.readObject();
	}

}
