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

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;

public class ReportServerInstallationModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
	}
	
	@Provides
	@Inject
	protected List<DbInstallationTask> provideDbInstallationTasks(
		ApplicationPropertiesService propertiesService,
		Provider<InstallBaseDataTask> baseDataTaskProvider,
		Provider<ExecutePackagedScriptsTask> executePackagedScriptsTask,
		Provider<DemoDataInstallTask> demoDbInstallTask, 
		Provider<DemoContentInstallTask> demoContentInstallTask,
		Provider<InitConfigTask> initConfigTask
		){
		List<DbInstallationTask> tasks = new ArrayList<DbInstallationTask>();
		
		if("true".equals(propertiesService.getString("rs.install.basedata"))){
			tasks.add(baseDataTaskProvider.get());
			tasks.add(executePackagedScriptsTask.get());
		}
		
		
		if("true".equals(propertiesService.getString("rs.install.demodata", "true"))){
			tasks.add(demoDbInstallTask.get());
			tasks.add(demoContentInstallTask.get());
		};
		
		tasks.add(initConfigTask.get());
				
		return tasks;
	}

}
