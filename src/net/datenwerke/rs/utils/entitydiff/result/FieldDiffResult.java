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
 
 
package net.datenwerke.rs.utils.entitydiff.result;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public abstract class FieldDiffResult {

	protected final Field field;
	protected final Object valueA;
	protected final Object valueB;
	
	public FieldDiffResult(Field field, Object valueA, Object valueB) {
		super();
		this.field = field;
		this.valueA = valueA;
		this.valueB = valueB;
	}

	public Field getField() {
		return field;
	}

	public Object getValueA() {
		return valueA;
	}

	public Object getValueB() {
		return valueB;
	}

	public boolean isEqual() {
		if(null == valueA && null == valueB)
			return true;
		if(null != valueA && null == valueB)
			return false;
		if(null == valueA && null != valueB)
			return false;
		
		if(valueA instanceof Collection && valueB instanceof Collection){
			return CollectionUtils.isEqualCollection((Collection)valueA, (Collection)valueB);
		} else if(valueA instanceof Map && valueB instanceof Map){
			Map mapA = (Map) valueA;
			Map mapB = (Map) valueB;
			
			Set keySetA = mapA.keySet();
			Set keySetB = mapB.keySet();
			
			if(! CollectionUtils.isEqualCollection(keySetA, keySetB))
				return false;
			
			for(Object key : keySetA){
				Object a = mapA.get(key);
				Object b = mapB.get(key);
				
				if(! new FieldDiffResult(null, a, b){}.isEqual())
					return false;
			}
		}
		
		return valueA.equals(valueB);
	}

}
