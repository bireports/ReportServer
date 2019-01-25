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
 
 
package net.datenwerke.rs.remoteaccess.service.sftp;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.datenwerke.rs.remoteaccess.service.RemoteAccessStartup;
import net.datenwerke.rs.remoteaccess.service.sftp.annotations.KeyLocation;
import net.datenwerke.rs.remoteaccess.service.sftp.annotations.SftpPort;
import net.datenwerke.rs.utils.config.ConfigService;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class SftpModule extends AbstractModule {
	
	private static final String CONFIG_FILE = "misc/misc.cf";
	private static final String KEY_LOCATION_KEY = "remoteaccess.sftp.keylocation";
	private static final String KEY_PORT = "remoteaccess.sftp.port";

	@Override
	protected void configure() {
		bind(SftpService.class).to(SftpServiceImpl.class).in(Singleton.class);
		
		bind(RemoteAccessStartup.class).asEagerSingleton();
	}

	@Inject @Provides @KeyLocation
	public String provideKeyLocation(ConfigService configService) {
		return configService.getConfigFailsafe(CONFIG_FILE).getString(KEY_LOCATION_KEY, null);
	}
	
	@Inject @Provides @SftpPort
	public int providePort(ConfigService configService){
		return configService.getConfigFailsafe(CONFIG_FILE).getInt(KEY_PORT, 8022);
	}

}
