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
 
 
package net.datenwerke.rs.authenticator.cr.service;

import java.security.SecureRandom;
import java.util.Arrays;

import net.datenwerke.rs.authenticator.client.login.dto.ChallengeResponseContainer;
import net.datenwerke.rs.utils.crypto.HashUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ChallengeResponseServiceImpl implements ChallengeResponseService{
	private final HashUtil hashUtil;

	private final Provider<SessionChallengeContainer> challengeContainerProvider;
	
	@Inject
	public ChallengeResponseServiceImpl(
			HashUtil hashUtil,
			Provider<SessionChallengeContainer> challengeContainerProvider) {
		this.hashUtil = hashUtil;
		this.challengeContainerProvider = challengeContainerProvider;
	}
	
	public ChallengeResponseContainer requestChallenge(){
		byte[] challenge = new byte[20];

		SecureRandom sr = new SecureRandom();
		sr.nextBytes(challenge);
	
		ChallengeResponseContainer cr = new ChallengeResponseContainer();
		cr.setChallenge(Base64.encodeBase64(challenge));
		cr.setId(sr.nextLong());
		
		challengeContainerProvider.get().setContainer(cr);
		
		return cr;
	}
	
	
	public boolean validateResponse(ChallengeResponseContainer container, String expectedSecret){
		long id = container.getId();
		
		SessionChallengeContainer sessionChallengeContainer = challengeContainerProvider.get();
		if(null == sessionChallengeContainer.getContainer())
			return false;
		
		try{
			ChallengeResponseContainer refContainer = sessionChallengeContainer.getContainer();
			
			byte[] concatenation = ArrayUtils.addAll(refContainer.getChallenge(), expectedSecret.getBytes());
			byte[] expectedResponse = Base64.encodeBase64(hashUtil.sha1Bytes(concatenation));
			
			return Arrays.equals(container.getResponse(), expectedResponse);
		} finally {
			/* clear container */
			sessionChallengeContainer.clear();
		}
	}

}
