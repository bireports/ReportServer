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
 
 
package net.datenwerke.rs.remoteaccess.service.sftp;

import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.common.keyprovider.AbstractKeyPairProvider;
import org.apache.sshd.common.util.SecurityUtils;
import org.bouncycastle.openssl.PEMReader;

public class UrlKeyPairProvider extends AbstractKeyPairProvider {
	
	private URL[] ulrs;

	public UrlKeyPairProvider(URL... ulrs) {
		this.ulrs = ulrs;
		
	}

	@Override
	protected KeyPair[] loadKeys() {
		 if (!SecurityUtils.isBouncyCastleRegistered()) {
	            throw new IllegalStateException("BouncyCastle must be registered as a JCE provider");
	        }
	        List<KeyPair> keys = new ArrayList<KeyPair>();
	        for (int i = 0; i < ulrs.length; i++) {
	            try {
	                PEMReader r = new PEMReader(new InputStreamReader(ulrs[i].openStream()), null);
	                try {
	                    Object o = r.readObject();
	                    if (o instanceof KeyPair) {
	                        keys.add((KeyPair) o);
	                    }
	                } finally {
	                    r.close();
	                }
	            } catch (Exception e) {
	                log.info("Unable to read key {}: {}", ulrs[i], e);
	            }
	        }
	        return keys.toArray(new KeyPair[keys.size()]);
	}

}
