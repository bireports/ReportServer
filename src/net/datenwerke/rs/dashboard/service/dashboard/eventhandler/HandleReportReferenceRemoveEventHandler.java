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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.eventhandler;

import java.util.Collection;

import net.datenwerke.rs.dashboard.service.dashboard.DadgetService;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.ReportDadget;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.rs.utils.exception.exceptions.NeedForcefulDeleteException;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;

public class HandleReportReferenceRemoveEventHandler implements
		EventHandler<RemoveEntityEvent> {

	private final DadgetService dadgetService;

	@Inject
	public HandleReportReferenceRemoveEventHandler(DadgetService dadgetService) {
		this.dadgetService = dadgetService;
	}

	@Override
	public void handle(RemoveEntityEvent event) {
		TsDiskReportReference node = (TsDiskReportReference) event.getObject();
		
		Collection<ReportDadget> container = dadgetService.getReportDadgetsWith(node);
		if(null != container && ! container.isEmpty()){
			String error = "Report is referenced in dadgets"; 
			throw new NeedForcefulDeleteException(error);
		}
	}

}
