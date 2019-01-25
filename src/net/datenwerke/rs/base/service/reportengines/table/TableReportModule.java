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
 
 
package net.datenwerke.rs.base.service.reportengines.table;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.Filter;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatDate;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatTemplate;
import net.datenwerke.rs.base.service.reportengines.table.hooks.TableOutputGeneratorProviderHook;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.CSVOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.CompactJSONOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.DataCountOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.HTMLOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.JSONOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.MetaDataOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.RSTableOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.RSTableSimpleBeanOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.TableOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.TableOutputGeneratorImpl;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.LegacyTablePDFOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.XLSOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.XLSStreamOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.table.output.metadata.TableMetadataExporter;
import net.datenwerke.rs.base.service.reportengines.table.output.metadata.TablePlainExporter;
import net.datenwerke.rs.base.service.reportengines.table.utils.RSTableToXLS;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableReportColumnMetadataService;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableReportColumnMetadataServiceImpl;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.utils.config.ConfigService;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

public class TableReportModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		/* bind metadata exporter */
		Multibinder<TableMetadataExporter> metadataExporterBinder = Multibinder.newSetBinder(binder(), TableMetadataExporter.class);
		metadataExporterBinder.addBinding().to(TablePlainExporter.class);
		
		/* bind utilities */
		bind(TableReportUtils.class).to(TableReportUtilsImpl.class).in(Singleton.class);
		bind(TableReportColumnMetadataService.class).to(TableReportColumnMetadataServiceImpl.class).in(Singleton.class);
		
		/* bind startup */
		bind(TableReportStartup.class).asEagerSingleton();
		
		requestStaticInjection(
			ColumnFormatTemplate.class,
			ColumnFormatDate.class,
			TableOutputGeneratorImpl.class,
			RSTableToXLS.class,
			Filter.class,
			Column.class
		);
	}

}
