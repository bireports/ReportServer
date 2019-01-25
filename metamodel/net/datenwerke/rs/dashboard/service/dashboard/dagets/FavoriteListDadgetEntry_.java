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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.dagets;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FavoriteListEntry.class)
public abstract class FavoriteListDadgetEntry_ {

	public static volatile SingularAttribute<FavoriteListEntry, Long> id;
	public static volatile SingularAttribute<FavoriteListEntry, Integer> position;
	public static volatile SingularAttribute<FavoriteListEntry, AbstractTsDiskNode> referenceEntry;
	public static volatile SingularAttribute<FavoriteListEntry, Long> version;

}

