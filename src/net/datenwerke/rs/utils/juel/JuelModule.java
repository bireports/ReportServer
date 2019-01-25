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
 
 
package net.datenwerke.rs.utils.juel;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.juel.annotations.JuelExpressionBlacklist;
import net.datenwerke.rs.utils.juel.wrapper.SimpleDateWrapper;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * The juel module provides basic functionality for handling juel related tasks.
 * 
 * <h1>Description</h1>
 * <p>
 * The juel module provides basic functionality for handling juel related tasks.
 * </p>
 * 
 * <h1>Content</h1>
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link JuelService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Singletons</h2>
 * <p>
 * <ul>
 * <li>{@link JuelService}</li>
 * </ul>
 * </p>
 * 
 * <h1>Dependencies</h1>
 * 
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link SimpleDateWrapper}</li>
 * </ul>
 * </p>
 * 
 * <h2>3rd-Party</h2>
 * <p>
 * <ul>
 * <li><a href="http://code.google.com/p/google-guice/">Google Guice</a></li>
 * <li><a href="http://juel.sourceforge.net/index.html">Juel</a></li>
 * </ul>
 * </p>
 */

public class JuelModule extends AbstractModule {

	public static final String BLACKLIST_PROPERTY = "juel.expression.blacklist";
	private static final String CONFIG_FILE = "security/misc.cf";

	@Override
	protected void configure() {
		bind(JuelService.class).to(JuelServiceImpl.class).in(Singleton.class);
	}

	@Provides @JuelExpressionBlacklist @Inject
	public Collection<String> provideJuelBlacklist(ConfigService configService){
		Collection<String> bl = new HashSet<String>();
		//List<String> list = propertyService.getList(BLACKLIST_PROPERTY);
		List list = configService.getConfigFailsafe(CONFIG_FILE).getList(BLACKLIST_PROPERTY, (List)Arrays.asList("getClass",".class","forName"));
		if(null != list)
			bl.addAll(list);
		return bl;
		
	}
}
