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
 
 
package net.datenwerke.rs.utils.simplequery.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to perform lookups by the entities id.
 * 
 * <p>
 * <pre>
 * 	@QueryById
 *	public AbstractReportManagerNode getNodeById(long id) {
 *		return null; // by magic
 *	}
 * </pre>
 * This code snipped will perform a query 
 * <pre>
 *   FROM AbstractReportManagerNode where idField = :id
 * </pre>
 * </p>
 * 
 * <p>
 *  Note that if from is not specified it, the return type is examined and
 *  taken as from.
 * </p>
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryById {

	Class<?> from() default Void.class;
	
}
