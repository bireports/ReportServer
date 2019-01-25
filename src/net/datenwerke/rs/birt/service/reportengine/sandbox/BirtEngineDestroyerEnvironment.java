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
 
 
package net.datenwerke.rs.birt.service.reportengine.sandbox;

import java.util.concurrent.Callable;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.core.internal.registry.RegistryProviderFactory;

public class BirtEngineDestroyerEnvironment implements
		Callable<Object> {

	private final IReportEngine reportEngine;
	
	public BirtEngineDestroyerEnvironment(
		IReportEngine reportEngine
		) {
		this.reportEngine = reportEngine;
	}

	@Override
	public Object call() throws Exception {
		reportEngine.destroy();
		Platform.shutdown();
		RegistryProviderFactory.releaseDefault();

		return null;
	}

}
