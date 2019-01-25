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
 
 
package net.datenwerke.security.service.eventlogger;

import net.datenwerke.security.service.eventlogger.annotations.FireEvent;
import net.datenwerke.security.service.eventlogger.annotations.FireForceRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.aop.FireEventInterceptor;
import net.datenwerke.security.service.eventlogger.aop.ForceRemoveEntityEventInterceptor;
import net.datenwerke.security.service.eventlogger.aop.MergeEntityEventInterceptor;
import net.datenwerke.security.service.eventlogger.aop.PersistEntityEventInterceptor;
import net.datenwerke.security.service.eventlogger.aop.RemoveEntityEventInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class DwEventLoggerModule extends AbstractModule {

	@Override
	protected void configure() {
		PersistEntityEventInterceptor persistInterceptor = new PersistEntityEventInterceptor();
		requestInjection(persistInterceptor);
		bindInterceptor(Matchers.any(),Matchers.annotatedWith(FirePersistEntityEvents.class), persistInterceptor);

		MergeEntityEventInterceptor mergeInterceptor = new MergeEntityEventInterceptor();
		requestInjection(mergeInterceptor);
		bindInterceptor(Matchers.any(),Matchers.annotatedWith(FireMergeEntityEvents.class), mergeInterceptor);

		RemoveEntityEventInterceptor removeInterceptor = new RemoveEntityEventInterceptor();
		requestInjection(removeInterceptor);
		bindInterceptor(Matchers.any(),Matchers.annotatedWith(FireRemoveEntityEvents.class), removeInterceptor);

		ForceRemoveEntityEventInterceptor forceRemoveInterceptor = new ForceRemoveEntityEventInterceptor();
		requestInjection(forceRemoveInterceptor);
		bindInterceptor(Matchers.any(),Matchers.annotatedWith(FireForceRemoveEntityEvents.class), forceRemoveInterceptor);
		
		FireEventInterceptor fireEventInterceptor = new FireEventInterceptor();
		requestInjection(fireEventInterceptor);
		bindInterceptor(Matchers.any(),Matchers.annotatedWith(FireEvent.class), fireEventInterceptor);

	}

}
