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
 
 
package net.datenwerke.security.service.crypto.pbe.encrypt;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import net.datenwerke.security.service.crypto.pbe.exception.PbeException;


public class ClientEncryptionService extends EncryptionServiceImpl {

	public static final String KEY_SPEC_ALGORITHM = "PBKDF2WithHmacSHA1";
	public static final String CIPHER_ALGORITHM = "AES/OFB/NOPADDING";
	
	public ClientEncryptionService(
			String passphrase, 
			String salt,
			int keylength, 
			int iterations
		) {
		super(passphrase, 
				salt, 
				keylength, 
				iterations, 
				CIPHER_ALGORITHM,
				KEY_SPEC_ALGORITHM);
	}

	@Override
	public byte[] decrypt(byte[] ciphertextIV) {
		SecretKey key = generateKey(passphrase);

		byte[] iv = Arrays.copyOfRange(ciphertextIV, 0, 16);
		byte[] ciphertext = Arrays.copyOfRange(ciphertextIV, 16, ciphertextIV.length);
		
        Cipher cipher;
		try {
			cipher = Cipher.getInstance(cipherAlgorithm);
	        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
	        return cipher.doFinal(ciphertext);
		} catch (Exception e) {
			PbeException pbeE = new PbeException("Could not decrypt ciphertext");
			pbeE.initCause(e);
			throw pbeE;
		}
	}
}
