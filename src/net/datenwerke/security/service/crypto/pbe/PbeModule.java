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

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import net.datenwerke.security.service.crypto.pbe.encrypt.ClientEncryptionService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionService;

/**
 * The PbeModule provides several functions for getting an {@link EncryptionService} instance.
 * 
 * <h1>Description</h1>
 * <p>
 * The PbeModule provides several functions for getting an {@link EncryptionService} instance.
 * </p>
 * 
 * 
 * <h1>Content</h1>
 * <h2>Services:</h2>
 * <p>
 * <ul>
 * <li>{@link PbeService}</li>
 * <li>{@link EncryptionService}</li>
 * <li>{@link ClientEncryptionService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Entities:</h2>
 * <p>
 * <ul>
 * </ul>
 * </p>
 * 
 * 
 * <h2>ClientModule:</h2>
 * <p>
 * <ul>
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
 * </ul>
 * </p>
 * 
 * <h2>Entities:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.security.service.usermanager.entities.User}</li>
 * </ul>
 * </p>
 * 
 * <h2>Services:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.ApplicationPropertiesService.service.reportserver.ReportServerPropertiesService}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.pbe.encrypt.EncryptionService}</li>
 * <li>{@link net.datenwerke.gf.service.authenticator.AuthenticatorService}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.pbe.encrypt.ClientEncryptionService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Others:</h2>
 * <p>
 * <ul>
 * <li>{@link com.google.inject.Inject}</li>
 * <li>{@link com.google.inject.Provides}</li>
 * <li>{@link com.google.inject.Singleton}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.pbe.encrypt.EncryptionServiceImpl}</li>
 * <li>{@link com.google.inject.Provider}</li>
 * <li>{@link net.datenwerke.gf.service.crypto.pbe.exception.PbeException}</li>
 * <li>{@link org.bouncycastle.util.encoders.Hex}</li>
 * </ul>
 * </p>
 */
public class PbeModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(PbeService.class).to(PbeServiceImpl.class).in(Singleton.class);
		bind(PbeConfig.class);
	}
	
	
}
