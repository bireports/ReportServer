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
 
 
package net.datenwerke.hookhandler.shared.hookhandler;

import java.util.List;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;

import com.google.inject.Provider;

/**
 * 
 * 
 * 
 *
 */
public interface HookHandlerService {

	public static final int PRIORITY_LOWER = 200;
	public static final int PRIORITY_LOW = 100;
	public static final int PRIORITY_MEDIUM = 50;
	public static final int PRIORITY_HIGH = 0;
	
	/**
	 * Attaches the given hooker to the given {@link Hook} with the given priority
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook The hook to connect the hooker to
	 * @param hooker The hooker
	 * @param priority The priority
	 */
	public <H extends Hook> void attachHooker(Class<? extends H> hook, H hooker, int priority);
	
	/**
	 * Attaches the given hooker to the given {@link Hook} with a priority of <b>PRIORITY_MEDIUM</b>
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook The hook to connect the hooker to
	 * @param hooker The hooker
	 */
	public <H extends Hook> void attachHooker(Class<? extends H> hook, H hooker);
	
	/**
	 * Dettaches the given hooker from the hook
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook
	 * @param hooker
	 */
	public <H extends Hook> void detachHooker(Class<? extends H> hook, H hooker);
	
	/**
	 * Attaches the given hooker to the given {@link Hook} with the given priority
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook The hook to connect the hooker to
	 * @param hookerProvider The provider for the hooker
	 * @param priority The priority
	 */
	public <H extends Hook> void attachHooker(Class<? extends H> hook, Provider<? extends H> hookerProvider, int priority);
	
	/**
	 * Attaches the given hooker to the given {@link Hook} with a priority of <b>PRIORITY_MEDIUM</b>
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook The hook to connect the hooker to
	 * @param hookerProvider The provider for the hooker
	 */
	public <H extends Hook> void attachHooker(Class<? extends H> hook, Provider<? extends H> hookerProvider);
	
	/**
	 * Detaches the given hooker from the given {@link Hook}
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook The hook to disconnect the hooker from
	 * @param hookerProvider The provider for the hooker
	 */
	public <H extends Hook> void dettachHooker(Class<? extends H> hook,  Provider<? extends H> hookerProvider);
	
	/**
	 * Returns a {@link List} of all hookers which are attached to the given hook.
	 * 
	 * @param <H> can be any type extending {@link Hook}
	 * @param hook The {@link Hook} to get the attached hookers from
	 * @return
	 */
	public <H extends Hook> List<H> getHookers(Class<? extends H> hook);


	public HookConfiguration getConfig(Class<? extends Hook> hook);
	
	public void setConfig(HookConfiguration config);

	public <H extends Hook> List<Provider<H>> getRawHookerProviders(Class<? extends H> hook);

	public <H extends Hook> List<H> getRawHookers(Class<? extends H> hook);
}
