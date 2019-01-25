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
 
 
/**
 * JavaDoc copy/pastle from the Apache Lucene project
 * Available under the ASL 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package net.datenwerke.gf.base.service.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for marking a property as indexable.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Documented
public @interface Field {

	/**
	 * Default value for the {@link #indexNullAs} parameter. Indicates that {@code null} values should not be indexed.
	 */
	String DO_NOT_INDEX_NULL = "__DO_NOT_INDEX_NULL__";

	/**
	 * Value for the {@link #indexNullAs} parameter indicating that {@code null} values should be indexed using the null
	 * token given through the {@link org.hibernate.search.cfg.Environment#DEFAULT_NULL_TOKEN} configuration property.
	 * If no value is given for that property, the token {@code _null_} will be used.
	 */
	String DEFAULT_NULL_TOKEN = "__DEFAULT_NULL_TOKEN__";

	/**
	 * @return Returns the field name. Defaults to the JavaBean property name.
	 */
	String name() default "";
	
	/**
	 * @return Returns the value to be used for indexing {@code null}. Per default {@code Field.NO_NULL_INDEXING} is returned indicating that
	 *         null values are not indexed.
	 */
	String indexNullAs() default DO_NOT_INDEX_NULL;
}
