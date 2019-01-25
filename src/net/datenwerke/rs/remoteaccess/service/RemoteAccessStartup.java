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
 
 
package net.datenwerke.rs.remoteaccess.service;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.remoteaccess.service.sftp.SftpService;
import net.datenwerke.rs.remoteaccess.service.sftp.genrights.SftpSecurityTarget;
import net.datenwerke.security.service.security.SecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class RemoteAccessStartup {

	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Inject
	public RemoteAccessStartup(

			SecurityService securityService,
			HookHandlerService hookHandlerService,
			final SftpService sftpService
			){

		securityService.registerSecurityTarget(SftpSecurityTarget.class);

		hookHandlerService.attachHooker(LateInitHook.class, new LateInitHook() {

			@Override
			public void initialize() {
				try{
					sftpService.start();
				}catch(Exception e){
					logger.warn( "Failed to start SFTP Server", e);
				}
			}
		});

	}
}
