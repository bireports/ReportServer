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
 
 
package net.datenwerke.rs.installation;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

/**
 * Handles the installation of report server.
 *
 */
public class ReportServerInstallationServiceImpl {
	@Inject 
	private PrepareDbForReportServer prepareDbTask;
	
	@Inject 
	private List<DbInstallationTask> dbInstallTasks;

	@Inject private Provider<EntityManager> emp;
	
	@Transactional
	public void install() {
		
		if(!dataIsInstalled(emp)){
			prepareDatabase(prepareDbTask);
			runAdditionalDbInstallTasks(dbInstallTasks);
		}

		for(DbInstallationTask task : dbInstallTasks)
			task.executeOnStartup();
	}

	private boolean dataIsInstalled(
			Provider<EntityManager> entityManagerProvider) {
		/* determine if the usertable is empty */
		EntityManager em = entityManagerProvider.get();
		Long countUser = (Long) em.createQuery("select count(*) from User").getSingleResult(); //$NON-NLS-1$
		
		/* determine if propertycontainer is empty */
//		Long countProperty = (Long) em.createQuery("select count(*) from PropertyContainer").getSingleResult(); //$NON-NLS-1$
		
		return (countUser != 0); // || (countProperty != 0);
	}
	
	private void runAdditionalDbInstallTasks(List<DbInstallationTask> dbInstallTasks) {
		for(DbInstallationTask task : dbInstallTasks)
			task.executeOnFirstRun();
	}

	private void prepareDatabase(PrepareDbForReportServer prepareDbTask) {
		prepareDbTask.executeOnFirstRun();
	}
}
