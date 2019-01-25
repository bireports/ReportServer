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
 
 
package net.datenwerke.security.service.usermanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.security.client.usermanager.dto.GroupDto;
import net.datenwerke.security.client.usermanager.dto.OrganisationalUnitDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.service.treedb.entities.SecuredAbstractNode;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserManagerServiceImpl;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBTree;

import org.hibernate.envers.Audited;

import net.datenwerke.gf.base.service.annotations.Field;

/**
 * Provides the base class for all user nodes.
 * 
 * 
 * @see UserManagerServiceImpl
 *
 */
@Entity
@Table(name="USERMANAGER_NODE")
@Audited
@Inheritance(strategy=InheritanceType.JOINED)
@TreeDBTree(
	rootTypes=OrganisationalUnit.class,
	manager=UserManagerService.class
)
@GenerateDto(
	dtoPackage="net.datenwerke.security.client.usermanager.dto",
	abstractDto=true,
	whitelist={
		UserDto.class, OrganisationalUnitDto.class, GroupDto.class
	}
)
abstract public class AbstractUserManagerNode extends SecuredAbstractNode<AbstractUserManagerNode>  {

	private static final long serialVersionUID = -4729776009606610343L;
	
	@ExposeToClient(view=DtoView.MINIMAL)
	@Column(length = 128)
	@Field
    private String guid;
	
	@ExposeToClient(view=DtoView.MINIMAL)
	@Column(length = 1024)
	@Field
    private String origin;
	

	public abstract String getName();

	@Override
	public String getNodeName() {
		return isRoot() ? getRootNodeName() : null == getName() ? "undefined" : getName();
	}

	@Override
	public String getRootNodeName() {
		return "usermanager";
	}
	
	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getGuid() {
		return guid;
	}


	public void setGuid(String guid) {
		this.guid = guid;
	}
}
