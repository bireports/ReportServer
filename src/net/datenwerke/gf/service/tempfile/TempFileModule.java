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

import java.io.File;
import java.util.UUID;

import net.datenwerke.gf.service.tempfile.annotations.TempDirLocation;
import net.datenwerke.gf.service.tempfile.annotations.TempFileLifeTime;
import net.datenwerke.rs.utils.config.ConfigService;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

public class TempFileModule extends AbstractModule {

	private static final String CONFIG_FILE = "main/main.cf";

	public static final String TEMP_DIR_PROP = "tempdir";
	
	public static final String RS_TEMPFILE_LIFETIME = "tempfile.lifetime";
	
	@Override
	protected void configure() {
		bind(TempFileStartup.class).asEagerSingleton();
	}

	@Inject @Provides @TempDirLocation
	public String provideTempDir(ConfigService configService){
		String tmpDir = null;
		tmpDir = configService.getConfigFailsafe(CONFIG_FILE).getString(TEMP_DIR_PROP);
		
		if(null == tmpDir || ! canWriteIn(tmpDir))
			tmpDir = System.getProperty("java.io.tmpdir");
		
		return tmpDir;
	}
	
	private boolean canWriteIn(String path){
		File f = new File(path, UUID.randomUUID().toString());
		try{
			f.createNewFile();
			f.delete();
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	@Inject @Provides @TempFileLifeTime
	public int provideTempFileLifeSpan(ConfigService configService){
		return configService.getConfigFailsafe(CONFIG_FILE).getInt(RS_TEMPFILE_LIFETIME, 10800);
	}
}
