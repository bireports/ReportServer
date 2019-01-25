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
 
 
package net.datenwerke.rs.base.service.reportengines.table;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.utils.jpa.EntityUtils;

import com.google.inject.Inject;

/**
 * Provides helper methods to work with {@link RSTableModel}.
 * 
 *
 */
public class TableModelHelper {

	protected final EntityUtils entityUtils;
	
	@Inject
	public TableModelHelper(EntityUtils entityUtils) {
		this.entityUtils = entityUtils;
	}


	public TableDefinition tableDefinitionFromEntityType(Object entity) {
		return tableDefinitionFromEntityType(entity.getClass());
	}

	public TableDefinition tableDefinitionFromEntityType(Class<?> type) {
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<Class<?>> columnTypes = new ArrayList<Class<?>>();
		
		for(Field f : entityUtils.getPersistantFields(type)){
			columnTypes.add(f.getType());
			columnNames.add(f.getName());
		}
		
		return new TableDefinition(columnNames, columnTypes);
	}
	
	
}
