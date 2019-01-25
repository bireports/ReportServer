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
 
 
package net.datenwerke.security.service.crypto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class CryptoCredentials {
	
	private X509Certificate x509Certificate;
	private PrivateKey privateKey;
	private List<X509Certificate> certificateChain = new ArrayList<X509Certificate>();

	public CryptoCredentials() {
	}
	
	public X509Certificate getX509Certificate() {
		return x509Certificate;
	}

	public void setX509Certificate(X509Certificate x509Certificate) {
		this.x509Certificate = x509Certificate;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	public PublicKey getPublicKey() {
		return x509Certificate.getPublicKey();
	}

	public void setCertificateChain(List<X509Certificate> certificateChain) {
		this.certificateChain = certificateChain;
	}
	
	public void addCertificateToChain(X509Certificate certificate) {
		certificateChain.add(certificate);
	}
	
	public List<X509Certificate> getCertificateChain() {
		return certificateChain;
	}
	
}
