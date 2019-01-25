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
 
 
package net.datenwerke.rs.base.ext.service.parameters.fileselection;

import javax.persistence.EntityManager;

import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport__;
import net.datenwerke.rs.utils.simplequery.annotations.Join;
import net.datenwerke.rs.utils.simplequery.annotations.Predicate;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class FileSelectionParameterServiceImpl implements FileSelectionParameterService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public FileSelectionParameterServiceImpl(
			Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	@FirePersistEntityEvents
	public void persist(UploadedParameterFile file) {
		entityManagerProvider.get().persist(file);
	}
	
	@Override
	@FirePersistEntityEvents
	public void persist(SelectedParameterFile file) {
		entityManagerProvider.get().persist(file);
	}
	
	@Override
	@SimpleQuery(from=FileSelectionParameterInstance.class,join=@Join(joinAttribute=FileSelectionParameterInstance__.selectedFiles, where=@Predicate(value="file")))
	public FileSelectionParameterInstance getParameterInstanceWithFile(@Named("file") SelectedParameterFile file){
		return null; // magic
	}
	
	@Override
	@QueryById
	public SelectedParameterFile getSelectedFileById(Long id) {
		return null; // magic
	}
}
