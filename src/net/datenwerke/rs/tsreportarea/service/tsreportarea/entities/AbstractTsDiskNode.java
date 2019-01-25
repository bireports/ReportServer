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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.AdditionalField;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post.AbstractTsDiskNode2DtoPost;
import net.datenwerke.treedb.service.treedb.AbstractNode;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBTree;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */

@Entity
@Table(name="TS_DISK_NODE")
@Audited
@Inheritance(strategy=InheritanceType.JOINED)
@TreeDBTree(
	rootTypes=TsDiskRoot.class,
	manager=TsDiskService.class,
	multipleRoots = true
)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.tsreportarea.client.tsreportarea.dto",
	abstractDto=true,
	createDecorator=true,
	poso2DtoPostProcessors=AbstractTsDiskNode2DtoPost.class,
	whitelist=RsDto.class,
	additionalFields = {
		@AdditionalField(name="teamSpaceId", type=Long.class)
	}
)
abstract public class AbstractTsDiskNode extends AbstractNode<AbstractTsDiskNode>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 889807740883444464L;

	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract void setName(String name);
	
	public abstract void setDescription(String name);
	
	@Override
	public String getNodeName() {
		return isRoot() ? getRootNodeName() : null == getName() ? "undefined" : getName();
	}

	@Override
	public String getRootNodeName() {
		return "tsreport";
	}
}
