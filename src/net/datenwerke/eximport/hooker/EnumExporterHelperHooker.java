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
 
 
package net.datenwerke.eximport.hooker;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.ExImportHelperService;
import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.hooks.BasicObjectExImporterHelperHookImpl;
import net.datenwerke.eximport.obj.SimpleItemProperty;
import net.datenwerke.rs.utils.reflection.ReflectionService;

import com.google.inject.Inject;

/**
 * Handles enums.
 *
 */
public class EnumExporterHelperHooker extends BasicObjectExImporterHelperHookImpl<SimpleItemProperty> {

	private final ReflectionService reflectionService;
	private final ExImportHelperService eiHelper;
	
	@Inject
	public EnumExporterHelperHooker(
		ExImportHelperService eiHelper,
		ReflectionService reflectionService
		){
		
		this.eiHelper = eiHelper;
		this.reflectionService = reflectionService;
	}
	
	@Override
	public boolean consumes(Class<?> type) {
		return (null != type && type.isEnum());
	}

	@Override
	public void export(ExportSupervisor exportSupervisor, Object value) throws XMLStreamException {
		eiHelper.setValueAttribute(exportSupervisor.getXmlStream(), value.toString());
	}

	@Override
	public Object doImport(SimpleItemProperty property) {
		Object enumObject = reflectionService.getEnumByString(property.getType(), property.getValue());
		return enumObject;
	}
}
