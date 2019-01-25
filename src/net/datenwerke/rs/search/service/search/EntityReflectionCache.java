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
 
 
package net.datenwerke.rs.search.service.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.rs.utils.reflection.ReflectionService;

public class EntityReflectionCache {

	private ReflectionService reflectionService;
	
	private HashMap<Class, List<Field>> fieldmap = new HashMap<Class, List<Field>>();
	
	@Inject
	public EntityReflectionCache(ReflectionService reflectionService) {
		this.reflectionService = reflectionService;
	}
		
	public List<Field> getFields(Class type){
		if(!fieldmap.containsKey(type))
			scanType(type);
		
		return fieldmap.get(type);
	}

	private void scanType(Class type) {
		List<Field> res = reflectionService.getFieldsByAnnotation(type, net.datenwerke.gf.base.service.annotations.Field.class);
		for(Field f : res)
			f.setAccessible(true);
		fieldmap.put(type, res);
	}
}
