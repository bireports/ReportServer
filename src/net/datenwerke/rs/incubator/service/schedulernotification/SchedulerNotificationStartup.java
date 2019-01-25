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
 
 
package net.datenwerke.rs.incubator.service.schedulernotification;

import javax.inject.Inject;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.scheduler.service.scheduler.SchedulerModule;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerExecutionHook;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerInfoHook;

import com.google.inject.Provider;

public class SchedulerNotificationStartup {

	@Inject
	public SchedulerNotificationStartup(
		final HookHandlerService hookHandler,
		final ConfigService configService,
		final Provider<SchedulerNotificationHooker> notificationHooker
		) {

		hookHandler.attachHooker(LateInitHook.class, new LateInitHook() {
			
			@Override
			public void initialize() {
				if(configService.getConfigFailsafe(SchedulerModule.CONFIG_FILE).getBoolean(SchedulerNotificationModule.NOTIFICATION_ENABLE_PROPERTY,true)){
					hookHandler.attachHooker(SchedulerExecutionHook.class, notificationHooker);
					hookHandler.attachHooker(SchedulerInfoHook.class, notificationHooker);
				}
			}
		});
	}
}
