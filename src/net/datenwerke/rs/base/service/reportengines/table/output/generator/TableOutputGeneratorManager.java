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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.generator;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.table.hooks.TableOutputGeneratorProviderHook;
import net.datenwerke.rs.core.service.reportmanager.output.AbstractReportOutputGeneratorManager;

import com.google.inject.Inject;


/**
 * Manages the output generators for table reports.
 * 
 * 
 * <p>
 * Currently only one instance of each output generator is created. This might
 * lead to problems and should be investigated.
 * </p>
 *
 *
 */
public class TableOutputGeneratorManager extends AbstractReportOutputGeneratorManager<TableOutputGenerator> {
	
	@Inject
	public TableOutputGeneratorManager(
			HookHandlerService hookHandler) {
		super(hookHandler, TableOutputGeneratorProviderHook.class);
	}

}
