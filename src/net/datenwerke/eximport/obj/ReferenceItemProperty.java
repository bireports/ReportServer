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
 
 
package net.datenwerke.eximport.obj;

import java.util.Collection;
import java.util.Collections;

import nu.xom.Element;


/**
 * 
 *
 */
public class ReferenceItemProperty extends ItemProperty {

	protected final String referenceId;
	protected final Class<?> exporterType;
	protected final boolean optional;
	
	public ReferenceItemProperty(String name, Class<?> type, String referenceId, Class<?> exporterType, boolean isOptional, Element el) {
		super(name, type, el);
		
		/* store object */
		this.referenceId = referenceId;
		this.exporterType = exporterType;
		this.optional = isOptional;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public Class<?> getExporterType() {
		return exporterType;
	}
	
	public Collection<String> getReferencedIds() {
		if(! isOptional())
			return Collections.singleton(referenceId);
		return Collections.emptySet();
	}

	public boolean isOptional() {
		return optional;
	}
	
	
}
