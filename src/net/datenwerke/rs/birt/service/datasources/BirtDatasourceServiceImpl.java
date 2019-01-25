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
 
 
package net.datenwerke.rs.birt.service.datasources;

import java.util.Collection;

import javax.persistence.EntityManager;

import net.datenwerke.rs.birt.service.datasources.birtreport.entities.BirtReportDatasourceConfig;
import net.datenwerke.rs.birt.service.datasources.birtreport.entities.BirtReportDatasourceConfig__;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BirtDatasourceServiceImpl implements BirtDatasourceService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public BirtDatasourceServiceImpl(
			Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	@QueryByAttribute(where=BirtReportDatasourceConfig__.report)
	public Collection<BirtReportDatasourceConfig> getDatasourceConfigsWith(Report report) {
		return null; // magic
	}
	
	@Override
	@FireMergeEntityEvents
	public BirtReportDatasourceConfig merge(BirtReportDatasourceConfig config) {
		return entityManagerProvider.get().merge(config);
	}
	
}
