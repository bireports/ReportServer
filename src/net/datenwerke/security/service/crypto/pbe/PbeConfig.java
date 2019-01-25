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
 
 
package net.datenwerke.security.service.crypto.pbe;

import javax.inject.Inject;

import net.datenwerke.rs.utils.properties.ApplicationPropertiesService;

public class PbeConfig {
	
	public static final String PBE_PROPERTY_SALT_KEY = "rs.crypto.pbe.salt";
	public static final String PBE_PROPERTY_PASSPHRASE_KEY = "rs.crypto.pbe.passphrase";
	public static final String PBE_PROPERTY_KEY_LENGTH = "rs.crypto.pbe.keylength";
	
	public static final String PBE_PROPERTY_KEY_SPEC = "rs.crypto.pbe.keyspec";
	public static final String PBE_PROPERTY_CIPHER_ALGORITHM = "rs.crypto.pbe.cipher";
	public static final String PBE_PROPERTY_ITERATIONS = "rs.crypto.pbe.iterations";

	
	private ApplicationPropertiesService propertiesService;

	@Inject
	public PbeConfig(ApplicationPropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}
	
	public String getKeySpec(){
		return propertiesService.getString(PBE_PROPERTY_KEY_SPEC, "PBKDF2WithHmacSHA1");
	}

	public String getCipher(){
		return propertiesService.getString(PBE_PROPERTY_CIPHER_ALGORITHM, "AES/CBC/ISO7816-4Padding");
	}
	
	public int getIterations(){
		return propertiesService.getInteger(PBE_PROPERTY_ITERATIONS, 2000);
	}

	public String getSalt(){
		return propertiesService.getString(PBE_PROPERTY_SALT_KEY);
	}
	
	public String getPassphrase(){
		return propertiesService.getString(PBE_PROPERTY_PASSPHRASE_KEY);
	}
	
	public int getKeylength(){
		return propertiesService.getInteger(PBE_PROPERTY_KEY_LENGTH, 128);
	}
}
