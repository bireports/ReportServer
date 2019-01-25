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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleDefaultFrom;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleProperties;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleSMTPHost;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleSMTPPassword;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleSMTPPort;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleSMTPUsername;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleUseAuthentication;
import net.datenwerke.rs.core.service.mail.security.DummySSLSocketFactory;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.security.service.crypto.CryptoService;

import org.apache.commons.configuration.Configuration;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;

/**
 * The mail module provides the ability to send EMails to users.
 * 
 * <h1>Description</h1>
 * <p>
 * The mail module provides simple classes and methods for sending EMails to users.
 * </p>
 * 
 * <h1>Content</h1>
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link MailService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Annotations</h2>
 * <p>
 * <ul>
 * <li>{@link MailModuleDefaultFrom}</li>
 * <li>{@link MailModuleProperties}</li>
 * <li>{@link MailModuleSMTPHost}</li>
 * <li>{@link MailModuleSMTPPassword}</li>
 * <li>{@link MailModuleSMTPPort}</li>
 * <li>{@link MailModuleSMTPUsername}</li>
 * </ul>
 * </p>
 * 
 * <h2>Client Module</h2>
 * <p>
 * <ul>
 * <li>{@link MailUIModule}</li>
 * </ul>
 * </p>
 * 
 * <h1>Extensibility</h1>
 * <h2>Hookability</h2>
 * <p>
 * <ul>
 * <li>{@link PropertyContainerProviderHook}</li>
 * </ul>
 * </p>
 * 
 * <h1>Dependencies</h1>
 * 
 * <h2>Services</h2>
 * <p>
 * <ul>
 * <li>{@link CryptoService}</li>
 * <li>{@link LoggingService}</li>
 * <li>{@link HookHandlerService}</li>
 * </ul>
 * </p>
 * 
 * <h2>3rd-Party</h2>
 * <p>
 * <ul>
 * <li><a href="http://code.google.com/p/google-guice/">Google Guice</a></li>
 * <li><a href="http://www.bouncycastle.org/java.html">Bouncy Castle</a></li>
 * </ul>
 * </p>
 * 
 * <h2>Others</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.utils.juel.SimpleJuel}</li>
 * <li>{@link net.datenwerke.logging.service.logging.LogLevel}</li>
 * <li>{@link net.datenwerke.logging.service.logging.LoggingFactory}</li>
 * <li>{@link net.datenwerke.logging.service.logging.Outcome}</li>
 * <li>{@link net.datenwerke.rs.core.service.mail.exceptions.MailerRuntimeException}</li>
 * </ul>
 * </p>
 */
public class MailModule extends AbstractReportServerModule {

	public static final String CONFIG_FILE = "mail/mail.cf"; //$NON-NLS-1$
	
	public static final String PROPERTY_SMTP_HOST = "smtp.host";
	public static final String PROPERTY_SMTP_PORT = "smtp.port";
	public static final String PROPERTY_SMTP_USERNAME = "smtp.username";
	public static final String PROPERTY_SMTP_PASSWORD = "smtp.password";
	public static final String PROPERTY_SMTP_SSL = "smtp.ssl";
	public static final String PROPERTY_SMTP_TLS_ENABLE = "smtp.tls.enable";
	public static final String PROPERTY_SMTP_TLS_REQUIRE = "smtp.tls.require";
	public static final String PROPERTY_SMTP_AUTH = "smtp.auth";
	
	public static final String PROPERTY_MAIL_SENDER = "mail.sender";

	
	@Override
	protected void configure() {
		/* bind service */
		bind(MailService.class).to(MailServiceImpl.class).in(Scopes.SINGLETON);
	}

	@Provides @Inject
	public Session provideSession(
		@MailModuleProperties Configuration config,	
		@MailModuleSMTPHost String host,
		@MailModuleSMTPPort Integer port,
		@MailModuleUseAuthentication boolean useAuth,
		final @MailModuleSMTPUsername String username,
		final @MailModuleSMTPPassword String password
		){
		
		/* get ssl */
		boolean ssl = config.getBoolean(PROPERTY_SMTP_SSL, false); 
		
		/* get tls */
		boolean enableTLS = config.getBoolean(PROPERTY_SMTP_TLS_ENABLE, false); 
		boolean requireTLS = config.getBoolean(PROPERTY_SMTP_TLS_REQUIRE, false); 
		
		/* prepare properties */
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", String.valueOf(host)); //$NON-NLS-1$
		props.setProperty("mail.smtp.port", String.valueOf(port)); //$NON-NLS-1$
		props.setProperty("mail.smtp.user", null == username ? "" : username); //$NON-NLS-1$
		props.setProperty("mail.smtp.auth", useAuth ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
		if(ssl){
			props.setProperty("mail.smtp.ssl.enable", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		    props.put("mail.smtp.ssl.socketFactory", new DummySSLSocketFactory()); //$NON-NLS-1$
		}
		if(enableTLS)
			props.setProperty("mail.smtp.starttls.enable", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		if(requireTLS)
			props.setProperty("mail.smtp.starttls.required", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		
		/* create authenticator */
		Authenticator auth = new Authenticator() {
		    
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				PasswordAuthentication authenticator = new PasswordAuthentication(null == username ? "" : username, null == password ? "" : password);
				return authenticator;
	        }
		};
		
		/* create session */
		Session session = Session.getInstance(props, auth);
		return session;
	}
	
	@Provides @Inject @MailModuleProperties
	public Configuration providePropertyContainer(ConfigService configService){
		try{
			return configService.getConfig(CONFIG_FILE);
		} catch(Exception e){
			return configService.newConfig();
		}
		
	}

	@Provides @Inject @MailModuleSMTPHost
	public String provideSmtpHost(@MailModuleProperties Configuration config){
		return config.getString(PROPERTY_SMTP_HOST, null);
	}
	
	@Provides @Inject @MailModuleUseAuthentication
	public boolean provideUseAuthentication(@MailModuleProperties Configuration config){
		String password = config.getString(PROPERTY_SMTP_PASSWORD, null);
		return config.getBoolean(PROPERTY_SMTP_AUTH, null != password);
	}
	
	@Provides @Inject @MailModuleSMTPUsername
	public String provideSmtpHostUsername(@MailModuleProperties Configuration config){
		return config.getString(PROPERTY_SMTP_USERNAME, null);
	}
	
	@Provides @Inject @MailModuleSMTPPassword
	public String provideSmtpHostPassword(@MailModuleProperties Configuration config){
		return config.getString(PROPERTY_SMTP_PASSWORD, null);
	}
	
	@Provides @Inject @MailModuleDefaultFrom
	public String provideDefaultFrom(@MailModuleProperties Configuration config){
		return config.getString(PROPERTY_MAIL_SENDER, null);
	}

	@Provides @Inject @MailModuleSMTPPort
	public Integer provideSMTPPort(@MailModuleProperties Configuration config){
		return config.getInteger(PROPERTY_SMTP_PORT, 25);
	}

}
