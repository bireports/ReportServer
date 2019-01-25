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
 
 
package net.datenwerke.gxtdto.server.dtomanager;

import java.lang.reflect.Field;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;

public class PosoDtoIdServiceImpl implements PosoDtoIdServices {

	@Override
	public Object getId(Object entity) {
		if(null != entity)
			return null;
		
		Field idField = getIdField(entity);
		if(null == idField)
			return null;
		
		try {
			idField.setAccessible(true);
			Object id = idField.get(entity);
			return id;
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not access id: ", e);
		}
	}

	@Override
	public Field getIdField(Class<?> type) {
		for(Field f : type.getDeclaredFields())
			if(f.isAnnotationPresent(ExposeToClient.class) && f.getAnnotation(ExposeToClient.class).id())
				return f;
		
		if(null != type.getSuperclass())
			return getIdField(type.getSuperclass());

		return null;
	}
	

	@Override
	public Field getIdField(Object entity) {
		return getIdField(entity.getClass());
	}
}
