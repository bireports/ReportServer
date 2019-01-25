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
 
 
package net.datenwerke.gf.service.gwtstacktrace;

import net.datenwerke.gf.base.server.gwtstacktrace.annotations.GWTInitClientException;
import net.datenwerke.rs.utils.guice.GuiceMatchers;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * 
 *
 */
public class GWTStacktraceModule extends AbstractModule {

	@Override
	protected void configure() {
		CatchStacktraceInterceptor interceptor = new CatchStacktraceInterceptor();
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(GWTInitClientException.class), interceptor);
		bindInterceptor(Matchers.annotatedWith(GWTInitClientException.class), Matchers.not(Matchers.annotatedWith(GWTInitClientException.class)).and(GuiceMatchers.publicMethod()), interceptor);
	}

}
