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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.dashboard.service.dashboard.DashboardManagerService;
import net.datenwerke.security.service.treedb.entities.SecuredAbstractNode;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBTree;

import org.hibernate.envers.Audited;


/**
 * Provides the base class for all user nodes
 * 
 *
 */
@Entity
@Table(name="DASHBOARD_MNGR_NODE")
@Audited
@Inheritance(strategy=InheritanceType.JOINED)
@TreeDBTree(
	rootTypes=DashboardFolder.class,
	manager=DashboardManagerService.class
)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.dashboard.client.dashboard.dto",
	abstractDto=true
)
abstract public class AbstractDashboardManagerNode extends SecuredAbstractNode<AbstractDashboardManagerNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042613328051548683L;

	abstract public String getName();
	abstract public String getDescription();

	@Override
	public String getNodeName() {
		return isRoot() ? getRootNodeName() : null == getName() ? "undefined" : getName();
	}

	@Override
	public String getRootNodeName() {
		return "dadgetlib";
	}
}
