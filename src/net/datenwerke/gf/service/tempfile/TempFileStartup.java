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
 
 
package net.datenwerke.gf.service.tempfile;

import javax.inject.Provider;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.gf.service.tempfile.annotations.TempDirLocation;
import net.datenwerke.gf.service.tempfile.maintenance.TempFileMaintenance;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;

public class TempFileStartup {

	@Inject
	public TempFileStartup(
		HookHandlerService hookHandler,
		TempFileMaintenance tempFileMaintenance, 
		@TempDirLocation final Provider<String> tmpDir
		){
		
		hookHandler.attachHooker(MaintenanceTask.class, tempFileMaintenance);
		
		/* synchronize tmpdirs */
		hookHandler.attachHooker(LateInitHook.class, new LateInitHook() {
			
			@Override
			public void initialize() {
				System.setProperty("java.io.tmpdir", tmpDir.get());
			}
		}, HookHandlerService.PRIORITY_HIGH);
	}
}
