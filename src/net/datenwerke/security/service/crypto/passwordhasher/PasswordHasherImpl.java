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
 
 
package net.datenwerke.security.service.crypto.passwordhasher;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.datenwerke.rs.utils.crypto.PasswordHasher;

import org.bouncycastle.util.encoders.Base64;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * 
 *
 */
@Singleton
public class PasswordHasherImpl implements PasswordHasher {

	private static final String SHA1PRNG = "SHA1PRNG";

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	private final Provider<String> passphraseProvider;
	
	@Inject
	public PasswordHasherImpl(@HmacPassphrase Provider<String> passphrase){
		this.passphraseProvider = passphrase;
	}

	@Override
	public String hashPassword(String password){
		return hashPassword(password, generateSalt());
	}
	
	public String hashPassword(String password, String salt){
		if(null == password)
			return ""; //$NON-NLS-1$
		try {
			return salt + calculateRFC2104HMAC(password, passphraseProvider.get() + salt);
		} catch (SignatureException e) {
			throw new RuntimeException("Hashing password failed", e);
		}

	}
	
	public String generateSalt(){
		byte salt[] = new byte[8];
		 SecureRandom saltGen;
		try {
			saltGen = SecureRandom.getInstance(SHA1PRNG);
			saltGen.nextBytes(salt);
			
			return new String(Base64.encode(salt));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed generating salt.", e);
		}
	}
	
	@Override
	public boolean validatePassword(String hashedPassword, String cleartextPassword){
		if(hashedPassword.length() == 28){
			return hashedPassword.equals(hashPassword(cleartextPassword, ""));
		}else if(hashedPassword.length() == 40){
			return hashedPassword.equals(hashPassword(cleartextPassword, hashedPassword.substring(0, 12)));
		}
		
		throw new RuntimeException("This password hasher cannot compare there kind of hash.");
	}

	public String calculateRFC2104HMAC(String data, String key)	throws java.security.SignatureException	{
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());

			return new String(Base64.encode(rawHmac));

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
	}
	
	@Override
	public String getHmacPassphrase() {
		return passphraseProvider.get();
	}

}
