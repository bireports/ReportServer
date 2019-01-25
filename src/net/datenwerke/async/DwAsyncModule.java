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
 
 
package net.datenwerke.async;

import net.datenwerke.async.annotations.WaitBeforeForcedShutdown;
import net.datenwerke.async.helpers.TransactionalRunnableFactory;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class DwAsyncModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DwAsyncService.class).to(DwAsyncServiceImpl.class).asEagerSingleton();
		
		bind(Long.class).annotatedWith(WaitBeforeForcedShutdown.class).toInstance(1000l);

		/* factories */
		install(new FactoryModuleBuilder().build(TransactionalRunnableFactory.class));
	}

}
