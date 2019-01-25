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
 
 
package net.datenwerke.rs.passwordpolicy.service.lostpassword;

import java.util.HashMap;

import javax.persistence.NoResultException;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.rs.core.service.mail.MailService;
import net.datenwerke.rs.core.service.mail.MailTemplate;
import net.datenwerke.rs.core.service.mail.SimpleMail;
import net.datenwerke.rs.passwordpolicy.service.PasswordGenerator;
import net.datenwerke.rs.passwordpolicy.service.events.UserLostPasswordEvent;
import net.datenwerke.rs.passwordpolicy.service.locale.PasswordPolicyMessages;
import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.ext.client.crypto.rpc.CryptoRpcService;
import net.datenwerke.security.service.security.locale.SecurityMessages;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class LostPasswordServiceImpl implements LostPasswordService{

	private final static String PROPERTY_USER = "user";
	private final static String PROPERTY_TMP_PASSWORD = "password";
	
	private final SecurityMessages messages = LocalizationServiceImpl.getMessages(SecurityMessages.class);

	private final UserManagerService userManagerService;
	private final PasswordHasher passwordHasher;
	private final Provider<PasswordGenerator> passwordGenerator;
	private final MailService mailService;
	private final EventBus eventBus;
	private final UserPropertiesService userPropertiesService;
	private final CryptoRpcService cryptoRpcService;
	
	@Inject
	public LostPasswordServiceImpl(
			EventBus eventBus,
			PasswordHasher passwordHasher, 
			UserManagerService userManagerService, 
			Provider<PasswordGenerator> passwordGenerator, 
			UserPropertiesService userPropertiesService, 
			MailService mailService,
			CryptoRpcService cryptoRpcService
	) {
		this.eventBus = eventBus;
		this.passwordHasher = passwordHasher;
		this.userManagerService = userManagerService;
		this.passwordGenerator = passwordGenerator;
		this.userPropertiesService = userPropertiesService;
		this.mailService = mailService;
		this.cryptoRpcService = cryptoRpcService;
	}
	
	@Override
	public void requestNewPassword(String username) throws ExpectedException{
		try{
			User user = userManagerService.getUserByName(username);
			
			if(null == user)
				throw new ExpectedException(PasswordPolicyMessages.INSTANCE.exceptionUserNotExists(username));
			
			PasswordGenerator pwdGen = passwordGenerator.get();
			
			if(null == pwdGen)
				throw new RuntimeException("No known password generator");
				
			/* generate temporary password */
			String randomPassword = pwdGen.newPassword();
			
			/* use the user's salt */
			String salt = cryptoRpcService.getUserSalt(user.getUsername());

			/* store the password in the users properties */
			userPropertiesService.setPropertyValue(user, LostPasswordModule.USER_PROPERTY_TMP_PASSWORD, passwordHasher.hashPassword(randomPassword, salt));
			userPropertiesService.setPropertyValue(user, LostPasswordModule.USER_PROPERTY_TMP_PASSWORD_DATE, Long.toString(System.currentTimeMillis()));
			
			userManagerService.merge(user);

			/* prepare value map for template */
			HashMap<String, Object> replacements = new HashMap<String, Object>();
			replacements.put(PROPERTY_TMP_PASSWORD, randomPassword);
			replacements.put(PROPERTY_USER, user);
			
			/* fill email temlate*/
			MailTemplate template = new MailTemplate();
			template.setMessageTemplate(messages.lostPasswordMessageTemplate());
			template.setSubjectTemplate(messages.lostPasswordMessageSubject());
			template.setDataMap(replacements);
			
			/* create and send message */
			try{
				SimpleMail mailMessage = mailService.newTemplateMail(template);
				mailMessage.setToRecipients(user.getEmail());
				mailService.sendMailSync(mailMessage);
			} catch(Exception e){
				throw new ExpectedException(PasswordPolicyMessages.INSTANCE.exceptionCouldNotSendPassword(e.getMessage()),e);
			}
			
			/* fire event */
			eventBus.fireEvent(new UserLostPasswordEvent(user));
			
		}catch(NoResultException e){
			throw new ExpectedException(PasswordPolicyMessages.INSTANCE.exceptionUserNotExists(username));
		}
	}

}
