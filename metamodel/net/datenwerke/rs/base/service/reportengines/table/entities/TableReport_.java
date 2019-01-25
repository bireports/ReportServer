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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.PreFilter;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TableReport.class)
public abstract class TableReport_ extends net.datenwerke.rs.core.service.reportmanager.entities.reports.Report_ {

	public static volatile SingularAttribute<TableReport, PreFilter> preFilter;
	public static volatile ListAttribute<TableReport, Column> columns;
	public static volatile SingularAttribute<TableReport, Boolean> enableSubtotals;
	public static volatile SingularAttribute<TableReport, Boolean> distinctFlag;
	public static volatile SingularAttribute<TableReport, String> cubeXml;
	public static volatile SingularAttribute<TableReport, Boolean> cubeFlag;
	public static volatile SingularAttribute<TableReport, Boolean> allowCubification;
	public static volatile SingularAttribute<TableReport, DatasourceContainer> metadataDatasourceContainer;
	public static volatile ListAttribute<TableReport, AdditionalColumnSpec> additionalColumns;
	public static volatile SingularAttribute<TableReport, Boolean> hideParents;

}

