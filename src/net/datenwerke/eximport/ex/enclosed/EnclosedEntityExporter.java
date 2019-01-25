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
 
 
package net.datenwerke.eximport.ex.enclosed;

import net.datenwerke.eximport.ExImportHelperService;
import net.datenwerke.eximport.ex.objectexporters.EntityObjectExporterFactory;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class EnclosedEntityExporter extends EnclosedObjectExporter {

	private static final String EXPORTER_ID = "EnclosedEntityExporter";

	@Inject
	public EnclosedEntityExporter(
		EntityObjectExporterFactory exporterFactory,
		ExImportHelperService eiHelper
		){
		super(exporterFactory, eiHelper);
	}

	@Override
	public String getExporterId() {
		return EXPORTER_ID;
	}


}
