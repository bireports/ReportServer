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
 
 
package net.datenwerke.rs.base.ext.service.parameters.fileselection.hookers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.datenwerke.rs.base.ext.service.parameters.fileselection.FileSelectionParameterDefinition;
import net.datenwerke.rs.base.ext.service.parameters.fileselection.FileSelectionParameterInstance;
import net.datenwerke.rs.base.ext.service.parameters.fileselection.SelectedParameterFile;
import net.datenwerke.rs.base.ext.service.parameters.fileselection.UploadedParameterFile;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantCreatorHook;

import com.google.inject.Inject;

public class FileSelectionParameterAdjustVariantHooker implements
		VariantCreatorHook {

	private final ReportService reportService;
	
	@Inject
	public FileSelectionParameterAdjustVariantHooker(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public void newVariantCreated(Report referenceReport,
			Report adjustedReport, Report variant) {

	}

	@Override
	public void temporaryVariantCreated(Report referenceReport,
			Report adjustedReport, Report variant) {
		List<FileSelectionParameterDefinition> definitions = referenceReport.getParameterDefinitionsOfType(FileSelectionParameterDefinition.class);
		if(null == definitions || definitions.isEmpty())
			return;
		
		/* get report from which variant is to be created */
		Report originalReport = reportService.getReportById(null == adjustedReport.getId() ? adjustedReport.getOldTransientId() : adjustedReport.getId());
		
		/* ensure that content of uploaded files is correctly set */
		for(FileSelectionParameterDefinition def : definitions){
			FileSelectionParameterInstance instance = (FileSelectionParameterInstance) variant.getParameterInstanceFor(def);
			if(instance.isStillDefault())
				continue;
			
			ParameterInstance<?> referenceInstance = originalReport.getParamInstanceByDefinitionId(instance.getDefinition().getId() == null ? instance.getDefinition().getOldTransientId() : instance.getDefinition().getId());
			if(! (referenceInstance instanceof FileSelectionParameterInstance))
				throw new IllegalStateException("expected an instanceof a FileSelectionParameterInstance");
			FileSelectionParameterInstance refInstance = (FileSelectionParameterInstance) referenceInstance;
			
			/* go over files and fix content, i.e., copy content from ref instance if necessary*/
			List<SelectedParameterFile> selectedFiles = new ArrayList<SelectedParameterFile>(instance.getSelectedFiles());
			for (Iterator<SelectedParameterFile> iterator = selectedFiles.iterator(); iterator.hasNext();) {
				SelectedParameterFile file = iterator.next();
				
				if(null != file.getUploadedFile() && null == file.getUploadedFile().getContent()){
					UploadedParameterFile uploadedFile = file.getUploadedFile();
					
					UploadedParameterFile refFile = refInstance.getUploadedFileById(uploadedFile.getId() == null ? uploadedFile.getOldTransientId() : uploadedFile.getId());
					if(null != refFile)
						uploadedFile.setContent(refFile.getContent());
				}
			}
		}
	}

}
