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
 
 
package net.datenwerke.dbpool.config;

import java.util.Properties;

import com.jolbox.bonecp.BoneCPConfig;

public class DwBoneCpConfig extends BoneCPConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5656567233532827354L;

	private int testConnectionOnAcquireAttempts = 10;
	
	public DwBoneCpConfig() {
		super();
	}
	
	public DwBoneCpConfig(Properties properties) throws Exception{
		super(properties);
	}

	public int getTestConnectionOnAcquireAttempts() {
		return testConnectionOnAcquireAttempts;
	}

	public void setTestConnectionOnAcquireAttempts(
			int testConnectionOnAcquireAttempts) {
		this.testConnectionOnAcquireAttempts = testConnectionOnAcquireAttempts;
	}

	
}
