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
 
 
package net.datenwerke.rs.incubator.service.crypto;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;

import javax.inject.Inject;

import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.security.service.crypto.credentialproviders.KeyStoreCredentialProvider;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileServerKeyStoreKryptoCredentialProvider extends	KeyStoreCredentialProvider {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Inject
	private static TerminalService terminalService;
	
	public FileServerKeyStoreKryptoCredentialProvider(HierarchicalConfiguration conf) {
		super(conf);
	}


	@Override
	public KeyStore getKeyStore(String location, String type, String secret) {
		try {
			Object obj = terminalService.getObjectByLocation(location, false);
			KeyStore keyStore = KeyStore.getInstance(type);
			keyStore.load(new ByteArrayInputStream(((FileServerFile) obj).getData()), secret.toCharArray());
			return keyStore;
		} catch (Exception e) {
			logger.info( "Error loading keystore form location \"" + location + "\"", e);
		} 
		
		return null;
	}



}

