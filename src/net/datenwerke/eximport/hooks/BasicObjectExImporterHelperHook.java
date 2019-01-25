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
 
 
package net.datenwerke.eximport.hooks;

import javax.xml.stream.XMLStreamException;

import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.ex.objectexporters.BasicObjectExporter;
import net.datenwerke.eximport.obj.ItemProperty;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;

/**
 * Allows to help the {@link BasicObjectExporter} by providing custom exporters for simple objects.
 * 
 *
 */
public interface BasicObjectExImporterHelperHook extends Hook {
	
	public boolean consumes(Class<?> type);
	public void export(ExportSupervisor exportSupervisor, Object value) throws XMLStreamException;
	public Object importData(ItemProperty property);
	
}
