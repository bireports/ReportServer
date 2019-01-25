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
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.Filter;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormat;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Column.class)
public abstract class Column_ {

	public static volatile SingularAttribute<Column, Boolean> subtotalGroup;
	public static volatile SingularAttribute<Column, NullHandling> nullHandling;
	public static volatile SingularAttribute<Column, Boolean> hidden;
	public static volatile SingularAttribute<Column, ColumnFormat> format;
	public static volatile SingularAttribute<Column, AggregateFunction> aggregateFunction;
	public static volatile SingularAttribute<Column, Integer> type;
	public static volatile SingularAttribute<Column, Long> version;
	public static volatile SingularAttribute<Column, Filter> filter;
	public static volatile SingularAttribute<Column, String> nullReplacementFormat;
	public static volatile SingularAttribute<Column, String> name;
	public static volatile SingularAttribute<Column, String> alias;
	public static volatile SingularAttribute<Column, Long> id;
	public static volatile SingularAttribute<Column, Integer> position;
	public static volatile SingularAttribute<Column, String> dimension;
	public static volatile SingularAttribute<Column, Order> order;

}

