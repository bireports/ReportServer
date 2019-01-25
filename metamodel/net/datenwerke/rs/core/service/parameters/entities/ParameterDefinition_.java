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
 
 
package net.datenwerke.rs.core.service.parameters.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ParameterDefinition.class)
public abstract class ParameterDefinition_ {

	public static volatile ListAttribute<ParameterDefinition, ParameterDefinition> dependsOn;
	public static volatile SingularAttribute<ParameterDefinition, Boolean> hidden;
	public static volatile SingularAttribute<ParameterDefinition, Boolean> displayInline;
	public static volatile SingularAttribute<ParameterDefinition, Boolean> editable;
	public static volatile SingularAttribute<ParameterDefinition, String> name;
	public static volatile SingularAttribute<ParameterDefinition, String> description;
	public static volatile SingularAttribute<ParameterDefinition, Integer> labelWidth;
	public static volatile SingularAttribute<ParameterDefinition, Long> id;
	public static volatile SingularAttribute<ParameterDefinition, Boolean> mandatory;
	public static volatile SingularAttribute<ParameterDefinition, Long> version;
	public static volatile SingularAttribute<ParameterDefinition, String> key;
	public static volatile SingularAttribute<ParameterDefinition, Integer> n;

}

