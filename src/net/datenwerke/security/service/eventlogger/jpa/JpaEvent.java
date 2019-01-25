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
 
 
package net.datenwerke.security.service.eventlogger.jpa;

import java.util.Map;

import net.datenwerke.rs.utils.eventbus.ObjectEvent;
import net.datenwerke.rs.utils.jpa.EntityUtils;
import net.datenwerke.security.service.eventlogger.DwLoggedEvent;

import com.google.inject.Inject;

public abstract class JpaEvent extends DwLoggedEvent implements ObjectEvent {

	@Inject
	protected EntityUtils entityUtils;
	
	protected final Object entity;

	public JpaEvent(Object entity, Object... properties){
		super(properties);
		this.entity = entity;
	}
	
	@Override
	public Object getObject() {
		return entity;
	}
	
	@Override
	public Map<String, String> getLoggedProperties() {
		Map<String, String> props =  super.getLoggedProperties();
		
		props.put("entity_type", null != entity ? entity.getClass().toString() : "NULL");
		Object id = entityUtils.getId(entity);
		props.put("entity_id", null != id ? id.toString() : "NULL");
		
		return props;
	}
}
