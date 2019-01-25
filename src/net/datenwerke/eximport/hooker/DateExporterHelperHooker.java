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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.ExImportHelperService;
import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.exceptions.ImportException;
import net.datenwerke.eximport.hooks.BasicObjectExImporterHelperHookImpl;
import net.datenwerke.eximport.obj.SimpleItemProperty;

import com.google.inject.Inject;

/**
 * Handles Dates.
 *
 */
public class DateExporterHelperHooker extends BasicObjectExImporterHelperHookImpl<SimpleItemProperty> {

	private final ExImportHelperService eiHelper;
	
	@Inject
	public DateExporterHelperHooker(
		ExImportHelperService eiHelper
		){
		
		this.eiHelper = eiHelper;
	}
	
	@Override
	public boolean consumes(Class<?> type) {
		return (null != type && Date.class.isAssignableFrom(type));
	}

	@Override
	public void export(ExportSupervisor exportSupervisor, Object value) throws XMLStreamException {
		eiHelper.setValueAttribute(exportSupervisor.getXmlStream(), (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss Z").format(value)));
	}

	@Override
	public Object doImport(SimpleItemProperty property) {
		if("".equals(property.getValue()))
			return null;
		
		try {
			return (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss Z")).parseObject(property.getValue());
		} catch (ParseException e) {
			throw new ImportException("old value: " + property.getValue(),e);
		}
	}

}
