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
 
 
package net.datenwerke.rs.core.service.mail;

import javax.mail.internet.MimeMessage;

import net.datenwerke.rs.core.service.mail.MailServiceImpl.MailSupervisor;


/**
 * 
 *
 */
public interface MailService {

	/**
	 * Creates and returns a new instance of {@link SimpleMail}
	 * 
	 * @return If there is a CA certificate a new instance of
	 * 		   {@link SimpleCryptoMail}, else a new instance of {@link SimpleMail}
	 */
	public SimpleMail newSimpleMail();
	
	/**
	 * Creates and returns a new instance of {@link SimpleMail} configured with the given template
	 * 
	 * @param template The {@link MailTemplate} to use
	 * @return A new instance of {@link SimpleMail} using the given {@link MailTemplate}
	 */
	public SimpleMail newTemplateMail(MailTemplate template);
	
	/**
	 * Spawns a new worker which then sends the message
	 * 
	 * @param message The {@link MimeMessage} to send
	 */
	public void sendMail(MimeMessage message);

	/**
	 * Sends a mail synchronously
	 * 
	 * @param message The {@link MimeMessage} to send
	 */
	void sendMailSync(MimeMessage message);

	void sendMailSync(MimeMessage message, MailSupervisor supervisor);

	void sendMail(MimeMessage message, MailSupervisor supervisor);
}
