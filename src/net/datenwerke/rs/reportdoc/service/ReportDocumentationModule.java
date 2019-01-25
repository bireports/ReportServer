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
 
 
package net.datenwerke.rs.reportdoc.service;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.reportdoc.client.ReportDocumentationUIModule;

import com.google.inject.Singleton;

/**
 * The BerichtsdokuModule provides functionality for handling Berichtsdoku {@link Reports}.
 * 
 * <h1>Description</h1>
 * <p>
 * The BerichtsdokuModule provides functionality for handling Berichtsdoku {@link Reports}.
 * </p>
 * 
 * 
 * <h1>Content</h1>
 * <h2>Services:</h2>
 * <p>
 * <ul>
 * <li>{@link BerichtsdokuInstallService}</li>
 * <li>{@link ReportDocumentationService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Entities:</h2>
 * <p>
 * <ul>
 * </ul>
 * </p>
 * 
 * <h2>Annotations:</h2>
 * <p>
 * <ul>
 * <li>{@link BerichtsdokuModuleProperties}</li>
 * </ul>
 * </p>
 * 
 * <h2>ClientModule:</h2>
 * <p>
 * <ul>
 * <li>{@link ReportDocumentationUIModule}</li>
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
 * <li>{@link net.datenwerke.rs.reportdoc.service.ReportDocumentationModule}</li>
 * </ul>
 * </p>
 * 
 * <h2>Entities:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer}</li>
 * <li>{@link net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition}</li>
 * <li>{@link net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder}</li>
 * <li>{@link net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport}</li>
 * <li>{@link net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile}</li>
 * <li>{@link net.datenwerke.properties.service.properties.entities.PropertyContainer}</li>
 * <li>{@link net.datenwerke.rs.core.service.reportmanager.entities.reports.Report}</li>
 * <li>{@link net.datenwerke.properties.service.properties.entities.Property}</li>
 * </ul>
 * </p>
 * 
 * <h2>Services:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.core.service.reportmanager.ReportService}</li>
 * <li>{@link net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService}</li>
 * <li>{@link net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService}</li>
 * </ul>
 * </p>
 * 
 * <h2>Others:</h2>
 * <p>
 * <ul>
 * <li>{@link net.datenwerke.rs.base.service.parameters.Datatype}</li>
 * <li>{@link net.datenwerke.rs.base.service.parameters.string.TextParameterDefinition}</li>
 * <li>{@link org.apache.commons.io.IOUtils}</li>
 * <li>{@link com.google.inject.Inject}</li>
 * <li>{@link net.datenwerke.rs.services.datenwerke.berichtsdoku.annotations.BerichtsdokuModuleProperties}</li>
 * <li>{@link com.google.inject.Provides}</li>
 * <li>{@link com.google.inject.Singleton}</li>
 * <li>{@link net.datenwerke.rs.services.datenwerke.berichtsdoku.exceptions.BerichtsdokuNotConfiguredException}</li>
 * <li>{@link com.google.inject.Provider}</li>
 * <li>{@link net.datenwerke.rs.archived.stuff.service.reportrequest.hookers.services.datenwerke.berichtsdoku.hookers.PropertyContainerProvider}</li>
 * <li>{@link net.datenwerke.properties.service.properties.hooks.PropertyContainerProviderHook}</li>
 * <li>{@link com.google.inject.BindingAnnotation}</li>
 * </ul>
 * </p>
 */
public class ReportDocumentationModule extends AbstractReportServerModule {

	public static final String CONFIG_FILE = "reportdoku/doku.cf"; //$NON-NLS-1$
	public static final String PROPERTY_REPORT_ID = "report.id"; //$NON-NLS-1$
	public static final String PROPERTY_REPORT_KEY = "report.key"; //$NON-NLS-1$
	
	@Override
	protected void configure() {
		bind(ReportDocumentationService.class).to(ReportDocumentationServiceImpl.class).in(Singleton.class);
	}

}
