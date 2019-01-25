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
 
 
package net.datenwerke.rs.scheduler.client.scheduler;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.ExportConfigFormFactory;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.pages.SeriesConfigFormFactory;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.ScheduledReportListPanelFactory;

/**
 * 
 *
 */
public class SchedulerUIModule extends AbstractGinModule {

	public static final String USER_PROPERTY_VIEW_VERTICAL_SPLIT = "reportScheduler:view:split";
	
	public static final String PROPERTY_CONTAINER = "SchedulerService_PropertyContainer"; //$NON-NLS-1$
	public static final String PROPERTY_EMAIL_SUBJECT = "scheduler:email:subject";
	public static final String PROPERTY_EMAIL_TEXT = "scheduler:email:text";
	public static final String PROPERTY_EMAIL_ATTACHEMENT_NAME = "scheduler:email:attachment:name";

	
	@Override
	protected void configure() {
		bind(SchedulerUiService.class).to(SchedulerUiServiceImpl.class).in(Singleton.class);
		
		/* create factory */
		install(new GinFactoryModuleBuilder().build(ExportConfigFormFactory.class));
		install(new GinFactoryModuleBuilder().build(SeriesConfigFormFactory.class));
		install(new GinFactoryModuleBuilder().build(ScheduledReportListPanelFactory.class));
		
		
		/* startup */
		bind(SchedulerUIStartup.class).asEagerSingleton();
	}


}
