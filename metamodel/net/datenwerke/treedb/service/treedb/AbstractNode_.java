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
 
 
package net.datenwerke.treedb.service.treedb;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractNode.class)
public abstract class AbstractNode_ {

	public static volatile SingularAttribute<AbstractNode, Date> lastUpdated;
	public static volatile SingularAttribute<AbstractNode, AbstractNode> parent;
	public static volatile ListAttribute<AbstractNode, AbstractNode> children;
	public static volatile SingularAttribute<AbstractNode, Long> flags;
	public static volatile SingularAttribute<AbstractNode, Integer> position;
	public static volatile SingularAttribute<AbstractNode, Long> id;
	public static volatile SingularAttribute<AbstractNode, Date> createdOn;
	public static volatile SingularAttribute<AbstractNode, Long> version;

}

