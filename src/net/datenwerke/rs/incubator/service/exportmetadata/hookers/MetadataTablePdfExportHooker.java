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
 
 
package net.datenwerke.rs.incubator.service.exportmetadata.hookers;

import java.util.Collection;
import java.util.Collections;

import net.datenwerke.rs.base.service.reportengines.table.hooks.TableExportHook;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.TableOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.LegacyTablePDFOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.incubator.service.exportmetadata.ExportMetadataService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.lowagie.text.Document;

public class MetadataTablePdfExportHooker implements TableExportHook {

	private final ExportMetadataService exportMetadataService;
	
	@Inject
	public MetadataTablePdfExportHooker(
		ExportMetadataService exportMetadataService
		) {
		
		/* store objects */
		this.exportMetadataService = exportMetadataService;
	}
	
	@Override
	public Collection<String> getFormats() {
		return Collections.singletonList(ReportExecutorService.OUTPUT_FORMAT_PDF);
	}

	@Override
	public void afterExport(Report report, User user, CompiledReport compiledReport,
			TableOutputGenerator outputGenerator, String outputFormat) {

	}

	@Override
	public void beforeExport(Report report, User user, TableDefinition td,
			TableOutputGenerator outputGenerator, String outputFormat) {
		if(outputGenerator instanceof LegacyTablePDFOutputGenerator){
			Document document = ((LegacyTablePDFOutputGenerator)outputGenerator).getDocument();
			
			document.addAuthor(exportMetadataService.getAuthor(report, user));
			document.addCreator(exportMetadataService.getCreator(report, user));
			document.addTitle(exportMetadataService.getTitle(report, user));
		}
	}

}
