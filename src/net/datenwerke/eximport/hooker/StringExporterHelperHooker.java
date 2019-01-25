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
 
 
package net.datenwerke.eximport.hooker;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.hooks.BasicObjectExImporterHelperHookImpl;
import net.datenwerke.eximport.obj.ComplexItemProperty;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Handles Dates.
 *
 */
public class StringExporterHelperHooker extends BasicObjectExImporterHelperHookImpl<ComplexItemProperty> {

	
	@Override
	public boolean consumes(Class<?> type) {
		return (null != type && String.class.isAssignableFrom(type));
	}

	@Override
	public void export(ExportSupervisor exportSupervisor, Object value) throws XMLStreamException {
		exportSupervisor.createCDataElement(StringEscapeUtils.escapeXml(value.toString()));
	}

	@Override
	public Object doImport(ComplexItemProperty property) {
		String text = StringEscapeUtils.unescapeXml(property.getElement().getValue());
		return text;
	}
}
