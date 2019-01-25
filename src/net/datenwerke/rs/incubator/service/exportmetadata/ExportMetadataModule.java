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
 
 
package net.datenwerke.rs.incubator.service.exportmetadata;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleAuthor;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleCreator;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleProperties;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleTitle;
import net.datenwerke.rs.utils.config.ConfigService;

import org.apache.commons.configuration.Configuration;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * 
 *
 */
public class ExportMetadataModule extends AbstractReportServerModule {

	public static final String CONFIG_FILE = "exportfilemd/metadata.cf"; //$NON-NLS-1$
	public static final String PROPERTY_TITLE = "exportmetadata.title"; //$NON-NLS-1$
	public static final String PROPERTY_AUTHOR = "exportmetadata.author"; //$NON-NLS-1$
	public static final String PROPERTY_CREATOR = "exportmetadata.creator"; //$NON-NLS-1$

	@Override
	protected void configure() {
		/* bind service */
		bind(ExportMetadataService.class).to(ExportMetadataServiceImpl.class).in(Singleton.class);
		
		/* bind startup */
		bind(ExportMetadataStartup.class).asEagerSingleton();
	}

	@Provides @Inject @ExportMetadataModuleProperties
	public Configuration providePropertyContainer(ConfigService configService){
		try{
			return configService.getConfig(CONFIG_FILE);
		} catch(Exception e){
			return configService.newConfig();
		}
	}
	
	@Provides @Inject @ExportMetadataModuleTitle
	public String provideTitle(@ExportMetadataModuleProperties Configuration config){
		return config.getString(PROPERTY_TITLE, "");
	}

	@Provides @Inject @ExportMetadataModuleAuthor
	public String provideAuthor(@ExportMetadataModuleProperties Configuration config){
		return config.getString(PROPERTY_AUTHOR, "");
	}
	
	@Provides @Inject @ExportMetadataModuleCreator
	public String provideCreator(@ExportMetadataModuleProperties Configuration config){
		return config.getString(PROPERTY_CREATOR, "");
	}
}
