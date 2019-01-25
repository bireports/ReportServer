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
 
 
package net.datenwerke.security.service.security.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Table(name="HIERARCHICAL_ACL")
@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@Audited
public class HierarchicalAcl extends Acl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2545028130153819825L;

	@Override
	public void addAce(Ace ace) {
		if(! (ace instanceof HierarchicalAce))
			throw new IllegalArgumentException("Expected hierarchical ACE");
		super.addAce(ace);
	}
	
	@Override
	public void addAce(Ace ace, int position) {
		if(! (ace instanceof HierarchicalAce))
			throw new IllegalArgumentException("Expected hierarchical ACE");
		super.addAce(ace, position);
	}
	
}
