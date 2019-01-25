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
 
 
package net.datenwerke.security.service.security.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ace.class)
public abstract class Ace_ {

	public static volatile SetAttribute<Ace, AceAccessMap> accessMaps;
	public static volatile SingularAttribute<Ace, AccessType> accesstype;
	public static volatile SingularAttribute<Ace, AbstractUserManagerNode> folk;
	public static volatile SingularAttribute<Ace, Long> id;
	public static volatile SingularAttribute<Ace, Acl> acl;
	public static volatile SingularAttribute<Ace, Integer> version;
	public static volatile SingularAttribute<Ace, Integer> n;

}
