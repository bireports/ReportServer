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
 
 
package net.datenwerke.rs.base.service.dbhelper;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.dbhelper.annotations.QueryConditionInMaxSize;
import net.datenwerke.rs.base.service.dbhelper.hooks.DatabaseHelperProviderHook;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ManagedQuery;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QuoteHelper;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.utils.config.ConfigService;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

public class DBHelperModule extends AbstractReportServerModule {

	public static final String CONFIG_FILE = "datasources/sql.cf";
	public static final String IN_MAX_SIZE = "sql.incondition.maxsize";
	public static final String QUOTE_MODE = "sql.quoteIdentifiers";
	
	@Override
	protected void configure() {
		bind(DBHelperService.class).to(DBHelperServiceImpl.class).in(Scopes.SINGLETON);
		
		bind(DbHelperStartup.class).asEagerSingleton();
		
		/* create factory */
		bind(ManagedQueryFactory.class).toProvider(FactoryProvider.newFactory(ManagedQueryFactory.class, ManagedQuery.class));
		
		requestStaticInjection(
			DatabaseHelper.class,
			QuoteHelper.class, 
			QueryBuilder.class
		);
	}
	
	@Inject @Provides
	public Set<DatabaseHelper> provideDbHelpers(HookHandlerService hookHandler){
		Set<DatabaseHelper> helpers = new HashSet<DatabaseHelper>();
		
		for(DatabaseHelperProviderHook provider : hookHandler.getHookers(DatabaseHelperProviderHook.class))
			helpers.addAll(provider.provideDatabaseHelpers());
		
		return helpers;
	}
	
	@Inject @Provides @QueryConditionInMaxSize
	public int provideInMaxSize(ConfigService configService){
		return configService.getConfig(CONFIG_FILE).getInt(IN_MAX_SIZE, 1000);
	}

}
