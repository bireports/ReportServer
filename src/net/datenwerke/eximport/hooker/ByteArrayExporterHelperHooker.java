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

import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.hooks.BasicObjectExImporterHelperHookImpl;
import net.datenwerke.eximport.obj.ComplexItemProperty;

import org.apache.commons.codec.binary.Base64;

public class ByteArrayExporterHelperHooker extends BasicObjectExImporterHelperHookImpl<ComplexItemProperty> {

	@Override
	public boolean consumes(Class<?> type) {
		return Byte[].class.equals(type) || byte[].class.equals(type);
	}

	@Override
	public void export(ExportSupervisor exportSupervisor, Object value) throws XMLStreamException {
		byte[] bValue = (byte[]) value;
		exportSupervisor.createCDataElement(new String(Base64.encodeBase64(bValue)));
	}

	@Override
	protected Object doImport(ComplexItemProperty property) {
		String text = property.getElement().getValue();
		return Base64.decodeBase64(text.getBytes());
	}

}
