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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.filters;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

@Entity
@Table(name="FILTER_BLOCK")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator=true
)
public class FilterBlock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2009925371672029341L;

	@JoinTable(name="FILTER_BLOCK_2_FILTERS")
	@EnclosedEntity
	@ExposeToClient(allowForeignPosoForEnclosed=true)
	@OneToMany(cascade=CascadeType.ALL)
	private Set<FilterSpec> filters = new HashSet<FilterSpec>();
	
	@JoinTable(name="FILTER_BLOCK_2_CHILD_BL")
	@EnclosedEntity
	@ExposeToClient(allowForeignPosoForEnclosed=true)
	@OneToMany(cascade=CascadeType.ALL)
	private Set<FilterBlock> childBlocks = new HashSet<FilterBlock>();
	
	@ExposeToClient(
		id=true
	)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ExposeToClient(
		view=DtoView.MINIMAL
	)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String description;
	
	@Version
	private Long version;

	@Transient
	private BlockType blockType;
	
	public Set<FilterSpec> getFilters() {
		return filters;
	}

	public void setFilters(Set<FilterSpec> filters) {
		this.filters = filters;
	}

	public Set<FilterBlock> getChildBlocks() {
		return childBlocks;
	}

	public void setChildBlocks(Set<FilterBlock> childBlocks) {
		this.childBlocks = childBlocks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void initBlockTypes(BlockType blockType) {
		this.blockType = blockType;
		
		BlockType cblock = blockType == BlockType.AND ? BlockType.OR : BlockType.AND;
		for(FilterBlock c : childBlocks)
			c.initBlockTypes(cblock);
	}
	
	public BlockType getBlockType() {
		if(null == blockType)
			throw new IllegalStateException("BlockTypes have not been initialized");
		return blockType;
	}
	
}
