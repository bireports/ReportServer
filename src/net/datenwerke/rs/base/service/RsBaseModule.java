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
 
 
package net.datenwerke.rs.base.service;

import com.google.inject.AbstractModule;

import net.datenwerke.rs.base.service.datasources.DatasourceExtensionModule;
import net.datenwerke.rs.base.service.parameterreplacements.BaseParameterReplacementModule;
import net.datenwerke.rs.base.service.parameters.BaseParametersModule;
import net.datenwerke.rs.base.service.parameters.datetime.dtogen.DateTimeParameterInstance2DtoPostProcessor;
import net.datenwerke.rs.base.service.parameters.datetime.dtogen.DateTimeParameterInstance2PosoPostProcessor;
import net.datenwerke.rs.base.service.reportengines.BaseReportEnginesModule;
import net.datenwerke.rs.core.service.internaldb.InternalDbModule;

public class RsBaseModule extends AbstractModule {

	@Override
	protected void configure() {
		/* install sub modules */
		install(new DatasourceExtensionModule());
		
		install(new BaseReportEnginesModule());

		install(new BaseParametersModule());
		
		install(new BaseParameterReplacementModule());
		
		install(new InternalDbModule());
		
		requestStaticInjection(DateTimeParameterInstance2PosoPostProcessor.class);
		requestStaticInjection(DateTimeParameterInstance2DtoPostProcessor.class);
	}

}
