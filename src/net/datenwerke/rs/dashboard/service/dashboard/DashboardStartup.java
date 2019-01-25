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
 
 
package net.datenwerke.rs.dashboard.service.dashboard;


import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardFolder;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardNode;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleDadgetNodeForceRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleDadgetNodeRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleDashboardNodeForceRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleDashboardNodeRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleDiskNodeRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleReportReferenceForceRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleReportReferenceRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleReportRemoveEventHandler;
import net.datenwerke.rs.dashboard.service.dashboard.eventhandler.HandleUserRemoveEventHandler;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DashboardStartup {

	@Inject
	public DashboardStartup(
		HookHandlerService hookHandler,
		EventBus eventBus,
		final Provider<SecurityService> securityServiceProvider,
		
		HandleUserRemoveEventHandler handleUserRemoveEvents,
		HandleDiskNodeRemoveEventHandler handleDiskNodeRemoveEvents,
		HandleReportRemoveEventHandler handleReportRemoveEvents,
		
		HandleReportReferenceRemoveEventHandler handleReportReferenceRemoveEvents,
		HandleReportReferenceForceRemoveEventHandler handleReportReferenceForceRemoveEvents,
		
		HandleDadgetNodeRemoveEventHandler handleDadgetNodeRemoveEvents,
		HandleDadgetNodeForceRemoveEventHandler handleDadgetNodeForceRemoveEvents,
		
		HandleDashboardNodeRemoveEventHandler handleDashboardNodeRemoveEvents,
		HandleDashboardNodeForceRemoveEventHandler handleDashboardNodeForceRemoveEvents
		
		){
		
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, User.class, handleUserRemoveEvents);
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, AbstractTsDiskNode.class, handleDiskNodeRemoveEvents);
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, Report.class, handleReportRemoveEvents);
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, TsDiskReportReference.class, handleReportReferenceRemoveEvents);
		eventBus.attachObjectEventHandler(ForceRemoveEntityEvent.class, TsDiskReportReference.class, handleReportReferenceForceRemoveEvents);
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, DadgetNode.class, handleDadgetNodeRemoveEvents);
		eventBus.attachObjectEventHandler(ForceRemoveEntityEvent.class, DadgetNode.class, handleDadgetNodeForceRemoveEvents);
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, DashboardNode.class, handleDashboardNodeRemoveEvents);
		eventBus.attachObjectEventHandler(ForceRemoveEntityEvent.class, DashboardNode.class, handleDashboardNodeForceRemoveEvents);
		
		/* register security targets */
		securityServiceProvider.get().registerSecurityTarget(DadgetNode.class);
		securityServiceProvider.get().registerSecurityTarget(DashboardNode.class);
		securityServiceProvider.get().registerSecurityTarget(DashboardFolder.class);
	}
}
