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
 
 
package net.datenwerke.security.service.crypto;

import java.security.KeyStore;

import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesService;
import net.datenwerke.security.service.crypto.passwordhasher.HmacPassphrase;
import net.datenwerke.security.service.crypto.passwordhasher.PasswordHasherImpl;
import net.datenwerke.security.service.crypto.pbe.PbeModule;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

/**
 * The crypto module provides functions for handling crypto stuff.
 * 
 * <h1>Description</h1>
 * <p>
 * The crypto module provides functions for handling crypto stuff.
 * </p>
 * 
 * 
 * <h1>Content</h1>
 * <h2>Services:</h2>
 * <p>
 * <ul>
 * <li>{@link CryptoService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Entities:</h2>
 * <p>
 * <ul>
 * <li>{@link CertificateEntry}</li>
 * <li>{@link KeyStore}</li>
 * <li>{@link CertificateRequestStates}</li>
 * </ul>
 * </p>
 * 
 * <h2>Annotations:</h2>
 * <p>
 * <ul>
 * <li>{@link RsaKeylength}</li>
 * </ul>
 * </p>
 * 
 * <h2>ClientModule:</h2>
 * <p>
 * <ul>
 * <li>{@link CryptoUIModule}</li>
 * </ul>
 * </p>
 * 
 * <h2>Hookability:</h2>
 * <p>
 * <ul>
 * </ul>
 * </p>
 * 
 * 
 * <h1>Dependencies:</h1>
 * 
 * <h2>Modules:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.core.service.guice.AbstractReportServerModule}</li>
 * <li>{@link net.datenwerke.security.service.crypto.pbe.PbeModule}</li>
 * </ul>
 * </p>
 * 
 * <h2>Entities:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.gf.service.crypto.entities.CertificateEntry}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.entities.CertificateRequestStates}</li>
 * <li>{@link net.datenwerke.security.service.usermanager.entities.User}</li>
 * </ul>
 * </p>
 * 
 * <h2>Services:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.core.service.reportserver.ReportServerPropertiesService}</li>
 * <li>{@link net.datenwerke.rs.core.service.mail.MailService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Others:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.gf.service.crypto.annotations.RsaKeylength}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.passwordhasher.HmacPassphrase}</li>
 * <li>{@link net.datenwerke.rs.utils.crypto.service.crypto.passwordhasher.PasswordHasher}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.passwordhasher.PasswordHasherImpl}</li>
 * <li>{@link com.google.inject.Inject}</li>
 * <li>{@link com.google.inject.Provides}</li>
 * <li>{@link org.bouncycastle.cms.CMSSignedData}</li>
 * <li>{@link net.datenwerke.rs.core.service.mail.SimpleMail}</li>
 * <li>{@link net.datenwerke.rs.utils.crypto.HashUtil}</li>
 * <li>{@link net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery}</li>
 * <li>{@link org.apache.commons.lang.time.DateUtils}</li>
 * <li>{@link org.bouncycastle.asn1.x509.BasicConstraints}</li>
 * <li>{@link org.bouncycastle.asn1.x509.ExtendedKeyUsage}</li>
 * <li>{@link org.bouncycastle.asn1.x509.GeneralName}</li>
 * <li>{@link org.bouncycastle.asn1.x509.GeneralNames}</li>
 * <li>{@link org.bouncycastle.asn1.x509.KeyPurposeId}</li>
 * <li>{@link org.bouncycastle.asn1.x509.KeyUsage}</li>
 * <li>{@link org.bouncycastle.asn1.x509.X509Extensions}</li>
 * <li>{@link org.bouncycastle.cms.CMSSignedDataGenerator}</li>
 * <li>{@link org.bouncycastle.cms.CMSSignedGenerator}</li>
 * <li>{@link org.bouncycastle.util.encoders.Base64}</li>
 * <li>{@link org.bouncycastle.x509.X509V3CertificateGenerator}</li>
 * <li>{@link com.google.inject.Provider}</li>
 * <li>{@link com.google.inject.Singleton}</li>
 * <li>{@link com.google.inject.BindingAnnotation}</li>
 * <li>{@link org.hibernate.annotations.Type}</li>
 * <li>{@link org.hibernate.envers.Audited}</li>
 * <li>{@link org.hibernate.validator.constraints.Length}</li>
 * </ul>
 * </p>
 */
public class CryptoModule extends AbstractModule{
	
	public static final String PASSWORDHASHER_PROPERTY_HMAC_PASSPHRASE = "rs.crypto.passwordhasher.hmac.passphrase";

	@Inject
	@Provides @HmacPassphrase
	protected String provideHmacPassphrase(ApplicationPropertiesService rsService){
		return rsService.getString(PASSWORDHASHER_PROPERTY_HMAC_PASSPHRASE);
	}
	
	@Override
	protected void configure() {
		
		bind(CryptoModuleStartup.class).asEagerSingleton();
		bind(CryptoService.class).to(CryptoServiceImpl.class);
		bind(PasswordHasher.class).to(PasswordHasherImpl.class);
		requestStaticInjection(CryptoServiceImpl.class);
		
		install(new PbeModule());
	}
	
}
