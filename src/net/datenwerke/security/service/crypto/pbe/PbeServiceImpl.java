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
 
 
package net.datenwerke.security.service.crypto.pbe;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.crypto.pbe.encrypt.ClientEncryptionService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionServiceImpl;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * 
 *
 */
public class PbeServiceImpl implements PbeService {

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private Provider<PbeConfig> pbeConfig;
	
	
	@Inject
	public PbeServiceImpl(
		Provider<AuthenticatorService> authenticatorServiceProvider,
		Provider<PbeConfig> pbeConfig
		){
		
		/* store parameters */
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.pbeConfig = pbeConfig;
	}


	@Override
	public EncryptionService getEncryptionService() {
		return new EncryptionServiceImpl(pbeConfig.get());
	}

	@Override
	public EncryptionService getEncryptionService(String passphrase) {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return new EncryptionServiceImpl(passphrase, salt, keylength, iterations, cipherAlgorithm, keySpecAlgorithm);
	}
	
	@Override
	public EncryptionService getEncryptionService(String passphrase, String salt) {
		PbeConfig config = pbeConfig.get();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return new EncryptionServiceImpl(passphrase, salt, keylength, iterations, cipherAlgorithm, keySpecAlgorithm);
	}


	@Override
	public EncryptionService getEncryptionService(User user) {
		return getEncryptionService(user.getPassword());
	}
	
	@Override
	public EncryptionService getClientEncryptionService() {
		User user = authenticatorServiceProvider.get().getCurrentUser();
		return getClientEncryptionService(user);
	}

	@Override
	public EncryptionService getClientEncryptionService(User user) {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return new ClientEncryptionService(user.getPassword(), salt, keylength, iterations);
	}
	

	@Override
	public String getSalt() {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return salt;
	}


	@Override
	public int getIterations() {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return iterations;
	}


	@Override
	public int getKeyLength() {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return keylength;
	}
	
	@Override
	public String getCipherAlgorithm() {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return cipherAlgorithm;
	}
	
	@Override
	public String getKeySpecAlgorithm() {
		PbeConfig config = pbeConfig.get();
		String salt = config.getSalt();
		int keylength = config.getKeylength();
		int iterations = config.getIterations();
		String cipherAlgorithm = config.getCipher();
		String keySpecAlgorithm = config.getKeySpec();
		return keySpecAlgorithm;
	}
	

}
